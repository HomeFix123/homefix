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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	
	// 인테리어 자랑 작성
	@Override
	public void saveBrag(Brag brag, String cid, String id) {

		brag.setBcnt(0);
		brag.setMember(memberRepo.findById(id).get());
		brag.setCompany(companyRepo.findById(cid).get());
		if(brag.getExtrainfo() == null && brag.getExtrainfo() == "") {
			brag.setExtrainfo("추가사항 없음");
		}
		
		brag.setBdate(new Date());


		bragRepo.save(brag);

	}

	// 인테리어 자랑 목록, 엘라스틱 검색 기능 추가
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
				query = query.must(QueryBuilders.matchQuery("family", family)); 
			}

			// 주거형태 검색
			if (hometype != null) {
				query = query.must(QueryBuilders.matchQuery("hometype", hometype)); 
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
	
	// 인테리어 자랑 상세
	@Override
	public Brag getBrag(Brag brag, String id) {
		
		Brag result = bragRepo.findByBid(brag.getBid());
		
		result.setPrefer(preferRepo.countByBrag(brag));
		// 좋아요 여부 체크
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
		// 조회수 증가
		Integer cnt = result.getBcnt();
		result.setBcnt(cnt+1);
		bragRepo.save(result);
		return result;

	}

	// 인테리어 자랑 삭제
	@Override
	public void deleteBrag(Brag brag, String id) {
		Brag result = bragRepo.findByBid(brag.getBid());
		result.setMember(memberRepo.findById(id).get());
		bragRepo.delete(result);
	}
	
	// 좋아요 입력
	@Override
	public void savePrefer(Brag brag, String id) {

		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		System.out.println("좋아요 입력" + result);
		preferRepo.save(result);

	}
	
	// 좋아요 취소
	@Override
	public void deletePrefer(Brag brag, String id) {
		Prefer result = new Prefer();
		result.setBrag(brag);
		result.setMember(memberRepo.findById(id).get());
		preferRepo.deleteAll(preferRepo.findByBragAndMember(brag, result.getMember()));
	}
	
	// 신고하기
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
	
	// 계약완료 업체 리스트
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

	//개인 마이페이지 =====================================================
	//개인 마이페이지 후기 목록 표시
	@Override
	public List<Brag> bMyPageList(Member mem) {
		// TODO Auto-generated method stub
		return null;
	}

	//개인 마이페이지 후기목록 페이징 (1)
	@Override
	public Page<Brag> getBragList(String id,Integer page) {
		int showCntPerPage = 3;	//한번에 보여줄 게시물의 수
		Member mem = memberRepo.findById(id).get();
		Pageable pageable = PageRequest.of(page, showCntPerPage,Sort.by("bid").descending());
		return bragRepo.findByMember(mem,pageable);
	}

	//개인 마이페이지 후기목록 페이징 (2)
	@Override
	public long countBragList(String id) {
		int showCntPerPage = 10;
		Member member = memberRepo.findById(id).get();
		return (long)(bragRepo.countByMember(member)-1)/showCntPerPage + 1;
	}

	

}
