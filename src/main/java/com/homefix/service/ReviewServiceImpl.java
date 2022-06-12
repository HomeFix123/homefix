package com.homefix.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homefix.domain.Company;
import com.homefix.domain.CompanyReport;
import com.homefix.domain.ElasticCompany;
import com.homefix.domain.ElasticReview;
import com.homefix.domain.Member;
import com.homefix.domain.Review;
import com.homefix.persistence.CompanyReportRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {
	
	@Autowired
	ReviewRepository reviewRepo;
	
	
	@Autowired 
	MemberRepository memberRepo;
	 
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	CompanyReportRepository companyRRepo;
	
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);
	@Autowired
	private RestHighLevelClient client;
	
	
	// 시공후기 작성하기
	@Override
	public void saveReview(Review rev, String cid) {
		
		rev.setRcnt(0);
		rev.setCompany(companyRepo.findById(cid).get());
		rev.setRdate(new Date());
		reviewRepo.save(rev);
		
	}
	
	
	// 시공후기 상세보기
	@Override
	public Review getReview(Review rev ) {
		Review result = reviewRepo.findById(rev.getRid()).get();
		result.setRcnt(result.getRcnt() + 1);
		reviewRepo.save(result);
		return result;
		
	}
	
	// 시공후기 삭제하기
	@Override
	public void deleteReview(Review rev, String cid) {
		
		Company company = companyRepo.findById(cid).get();
		
		Review review = reviewRepo.findByRidAndCompany(rev.getRid(), company);
		
		reviewRepo.delete(review);
	}
	
	// 시공후기 신고하기
	@Override
	public String saveCReport(Company cid, String id, String reason) {
		Member repo = memberRepo.findById(id).get();
		List<CompanyReport> list = companyRRepo.findByCompanyAndMember(cid, repo);
		if(list.isEmpty()) {
			CompanyReport result = new CompanyReport();
			result.setCompany(cid);
			result.setMember(repo);
			result.setReason(reason);
			result.setRday(new Date());
			companyRRepo.save(result);
			return "true";
			
		} else {
			
			return "false";
		
		}
	}
	
	// 시공후기 목록
	@Override
	public List<ElasticReview> getReviewList(Integer page, String hometype, String job, String family) {


		// 페이지당 보여줄 개수
		int viewsPerPage = 12;
		// 시작 번호 0부터 연산 (0, 12, 24, ...) 
		int startNum = viewsPerPage * (page-1);

		try {
			SearchRequest request = new SearchRequest("review"); // review 인덱스에 요청
			SearchSourceBuilder ssb = new SearchSourceBuilder(); // Elasticsearch 기준에서 요청할 json 생성

			ssb.from(startNum); // 초기 순서
			ssb.size(viewsPerPage); // 표시될 개수
			// 가져올 필드명(null: 기본값 (전부)) / 안가져올 필드명(String 배열)
			// content는 데이터 처리량 줄이기 위해 제외함
			ssb.fetchSource(null, new String[] {"@timestamp", "@version", "content", "company"}); 

			// 정렬
			ssb.sort("date", SortOrder.DESC); // 정렬 기준(필드명, 내림차순)
			

			// 필터링을 위해 bool 형식으로 설정
			// 검색어 => should
			// 키워드 => must
			BoolQueryBuilder query = QueryBuilders.boolQuery(); 



			// 주거형태 검색
			if(hometype != null) {
				query = query
						.must(QueryBuilders
								.matchQuery("hometype", hometype)); // 지역명으로 용어검색
			}
			// 작업분야
			if(job != null) {
				query = query
						.must(QueryBuilders
								.matchQuery("job", job)); 
			}
			// 가족형태
			if(family != null) {
				query = query
						.must(QueryBuilders
								.matchQuery("family", family)); 
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

			List<ElasticReview> resultList = new ArrayList<>();
			for(SearchHit h : searchHits) {
				// json 형식을 클래스로 받을 수 있게 변환
				ElasticReview review = MAPPER.readValue(h.getSourceAsString(), ElasticReview.class);
				
				resultList.add(review);

			}

			return resultList;

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}	
	}
		
	

}
