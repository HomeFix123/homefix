package com.homefix.service;

import java.util.ArrayList;
import java.util.Date;
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
import com.homefix.domain.Brag;
import com.homefix.domain.Company;
import com.homefix.domain.Contract;
import com.homefix.domain.ElasticBrag;
import com.homefix.domain.Estimation;
import com.homefix.domain.Member;
import com.homefix.domain.MemberReport;
import com.homefix.domain.Prefer;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.CompanyRepository;
import com.homefix.persistence.ContractRepository;
import com.homefix.persistence.EstRepository;
import com.homefix.persistence.MemberReportRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PreferRepository;

@Service
public class BragServiceImpl implements BragService {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final Logger LOG = LoggerFactory.getLogger(BragServiceImpl.class);

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	BragRepository bragRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	CompanyRepository companyRepo;

	@Autowired
	PreferRepository preferRepo;

	@Autowired
	MemberReportRepository memberRRepo;

	@Autowired
	EstRepository estRepo;

	@Autowired
	ContractRepository contractRepo;

	@Override
	public void saveBrag(Brag brag, String cid, String id) {

		brag.setBcnt(0);
		brag.setMember(memberRepo.findById(id).get());
		brag.setCompany(companyRepo.findById(cid).get());
		brag.setBdate(new Date());

		System.out.println(brag);
		bragRepo.save(brag);

	}

	@Override
	public List<ElasticBrag> getBragByKeyword(String id, String loc, 
			String family, String hometype, String sort, Integer page) {


		// 페이지당 보여줄 개수
		int viewsPerPage = 12;
		// 시작 번호 0부터 연산 (0, 10, 20, ...)
		int startNum = viewsPerPage * (page - 1);

		try {
			SearchRequest request = new SearchRequest("brag"); // company 인덱스에 요청
			SearchSourceBuilder ssb = new SearchSourceBuilder(); // Elasticsearch 기준에서 요청할 json 생성

			ssb.from(startNum); // 초기 순서
			ssb.size(viewsPerPage); // 표시될 개수
			// 가져올 필드명(null: 기본값 (전부)) / 안가져올 필드명(String 배열)
			// content는 데이터 처리량 줄이기 위해 제외함
			ssb.fetchSource(null, new String[] { "@timestamp", "@version", "content" });

			// 정렬
			if (sort != null) {
				ssb.sort(sort, SortOrder.DESC); // 정렬 기준(필드명, 내림차순)
			}

			// 필터링을 위해 bool 형식으로 설정
			// 검색어 => should
			// 지역 => must
			BoolQueryBuilder query = QueryBuilders.boolQuery();

			// 지역 검색
			if (loc != null) {
				query = query.must(QueryBuilders.matchQuery("loc", loc)); // 지역명으로 용어검색
			}

			// 가족형태 검색
			if (family != null) {
				query = query.must(QueryBuilders.matchQuery("family", family)); // 지역명으로 용어검색
			}

			// 주거형태 검색
			if (hometype != null) {
				query = query.must(QueryBuilders.matchQuery("hometype", hometype)); // 지역명으로 용어검색
			}


			// 요청할 데이터 처리
			ssb.query(query);
			request.source(ssb);

			// 엘라스틱서치 = client
			// client에 요청 후 결과 반환
			final SearchResponse documentFields = client.search(request, RequestOptions.DEFAULT // 기본값으로 처리
			);

			// 결과가 없는 경우 null 반환
			if (documentFields == null) {
				return null;
			}

			// 결과 데이터 추출
			SearchHits hits = documentFields.getHits();
			SearchHit[] searchHits = hits.getHits();

			List<ElasticBrag> resultList = new ArrayList<>();
			for (SearchHit h : searchHits) {

				// json 형식을 클래스로 받을 수 있게 변환
				ElasticBrag brag = MAPPER.readValue(h.getSourceAsString(), ElasticBrag.class);
				Set<String> prefer = brag.getPreferids();
				if(prefer != null && !prefer.isEmpty()) {
					if(prefer.contains(id)) {
						brag.setPreferck(true);
					} else {
						brag.setPreferck(false);
					}
				} else {
					brag.setPreferck(false);
				}
				
				resultList.add(brag);

			}

			return resultList;

		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Brag getBrag(Brag brag, String id) {

		Brag result = bragRepo.findByBid(brag.getBid());
		result.setPrefer(preferRepo.countByBrag(brag));
		if(id != null) {
			List<Prefer> list = preferRepo.findByBragAndMember(brag, memberRepo.findById(id).get());
			if (list.size() > 0) {
				result.setPreferck(true);
			} else {
				result.setPreferck(false);
			}
		} else {
			result.setPreferck(false);
		}
		Integer cnt = result.getBcnt();
		result.setBcnt(cnt+1);
		bragRepo.save(result);
		return result;

	}

	@Override
	public void deleteBrag(Brag brag, String id) {
		Brag result = bragRepo.findByBid(brag.getBid());
		result.setMember(memberRepo.findById(id).get());
		bragRepo.delete(bragRepo.findByBidAndMember(result.getBid(), result.getMember()));
	}

	@Override
	public void savePrefer(Brag brag, String id) {

		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		System.out.println("좋아요 입력" + result);
		preferRepo.save(result);

	}

	@Override
	public void deletePrefer(Brag brag, String id) {
		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		preferRepo.deleteAll(preferRepo.findByBragAndMember(brag, result.getMember()));
	}

	@Override
	public String saveReport(Member id, String reporter, String reason) {
		Member repo = memberRepo.findById(reporter).get();
		List<MemberReport> list = memberRRepo.findByMemberAndReporter(id, repo);
		if (list.isEmpty()) {
			MemberReport result = new MemberReport();
			result.setMember(id);
			result.setReporter(repo);
			result.setReason(reason);
			memberRRepo.save(result);
			return "true";

		} else {

			return "false";

		}
	}

	@Override
	public Set<Company> getContractList(String id) {
		Member mem = memberRepo.findById(id).get();
		List<Estimation> result = estRepo.findByMember(mem);
		Set<Company> con = new HashSet<>();
		for (int i = 0; i < result.size(); i++) {
			Contract temp = contractRepo.findByEstimation(result.get(i));
			if (temp != null) {
				con.add(temp.getCompany());
			}
		}

		System.out.println("con" + con);
		return con;

	}

}
