package com.homefix.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homefix.domain.CompanyPrefer;
import com.homefix.domain.ElasticCompany;
import com.homefix.domain.Member;
import com.homefix.persistence.CompanyPreferRepository;
import com.homefix.persistence.MemberRepository;

@Service
public class ElasticCompanyServiceImpl implements ElasticCompanyService{
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger LOG = LoggerFactory.getLogger(ElasticCompanyServiceImpl.class);
	
	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private CompanyPreferRepository companyPreferRepo;
	
	/*
	 * 파라미터:
	 * 		userId 	: 북마크 여부 판단용
	 * 		keyword : 검색어
	 * 		area	: 지역 조건
	 * 		sort	: 정렬 조건
	 * 		page	: 요청 페이지(기본 1, ajax 요청시 2이상)
	 * 
	 * 리턴값:
	 * 		List<ElasticCompany> (엘라스틱서치용 company 객체 리스트)
	 */
	@Override
	public List<ElasticCompany> getCompanyByKeyword(String userId, String keyword, String area, String sort, Integer page) {
		
		// 북마크한 업체의 아이디 목록 저장
		Set<String> companyIdList = new HashSet<>();
		if(userId != null && !userId.equals("")) {
			Member member = memberRepo.findById(userId).get();
			List<CompanyPrefer> preferList = companyPreferRepo.findByMember(member);
			for(CompanyPrefer prefer : preferList) {
				companyIdList.add(prefer.getCompany().getId());
			}
		}
		
		// 페이지당 보여줄 개수
		int viewsPerPage = 12;
		// 시작 번호 0부터 연산 (0, 12, 24, ...) 
		int startNum = viewsPerPage * (page-1);
		
		try {
			SearchRequest request = new SearchRequest("company"); // company 인덱스에 요청
			SearchSourceBuilder ssb = new SearchSourceBuilder(); // Elasticsearch 기준에서 요청할 json 생성
			
			ssb.from(startNum); // 초기 순서
			ssb.size(viewsPerPage); // 표시될 개수
			// 가져올 필드명(null: 기본값 (전부)) / 안가져올 필드명(String 배열)
			// content는 데이터 처리량 줄이기 위해 제외함
			ssb.fetchSource(null, new String[] {"@timestamp", "@version", "content"}); 
			
			// 정렬
			if(sort != null) {
				ssb.sort(sort, SortOrder.DESC); // 정렬 기준(필드명, 내림차순)
			}
			
			// 필터링을 위해 bool 형식으로 설정
			// 검색어 => should
			// 지역 => must
			BoolQueryBuilder query = QueryBuilders.boolQuery(); 
			
			// 검색어 처리
			if(keyword != null && !keyword.equals("")) {
				query = query
							.should(QueryBuilders
									.multiMatchQuery(keyword, "name", "content") // 검색될 필드명 설정 (검색어, 필드명, 필드명, ...)
									.field("name", 2)); // 특정 필드(name)에 가중치 2배
			}
			
			// 지역 검색
			if(area != null) {
				query = query
						.must(QueryBuilders
								.termQuery("spaces", area)); // 지역명으로 용어검색
			}
			
			// 요청할 데이터 처리
			ssb.query(query);
			request.source(ssb);
			
			// 엘라스틱서치 = client
			// client에 요청 후 결과 반환
			final SearchResponse documentFields = client.search(
					request, 
					RequestOptions.DEFAULT // 기본값으로 처리
			);
			
			// 결과가 없는 경우 null 반환
			if(documentFields == null) {
				return null;
			}
			
			// 결과 데이터 추출
			SearchHits hits = documentFields.getHits();
			SearchHit[] searchHits = hits.getHits();
			
			List<ElasticCompany> resultList = new ArrayList<>();
			for(SearchHit h : searchHits) {
				
				// json 형식을 클래스로 받을 수 있게 변환
				ElasticCompany company = MAPPER.readValue(h.getSourceAsString(), ElasticCompany.class);
				
				// 검색한 결과가 북마크한 업체에 있는 지 체크
				if(companyIdList.contains(company.getId())) {
					company.setIsPrefer(true);
				}
				resultList.add(company);
				
			}
			
			return resultList;
			
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}	
	}
}
