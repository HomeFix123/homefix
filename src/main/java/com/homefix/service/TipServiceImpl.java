package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.homefix.domain.Tip;
import com.homefix.domain.Tip_prefer;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.TipPreferRepository;
import com.homefix.persistence.TipRepository;

@Service
public class TipServiceImpl implements TipService {

	@Autowired
	TipRepository tipRepo;

	@Autowired
	MemberRepository memberRepo;

	@Autowired
	TipPreferRepository tipPreferRepo;

	// 팁 전체 목록
	@Override
	public List<Tip> getTipList(Tip tip, int page, String id) {
		// return (List<Tip>)tipRepo.findAll();
		int showCntPerPage = 5;
		Pageable pageable = PageRequest.of(page - 1, showCntPerPage, Sort.by("tid").descending());

		//리스트 팁으로 받음
		List<Tip> result = tipRepo.findAll(pageable);
		for(Tip temp : result) {
			List<Tip_prefer> list = tipPreferRepo.findByTipAndMember(temp, memberRepo.findById(id).get());
			
			if (list.size() > 0) {
				temp.setPreferck(true);
			} else {
				temp.setPreferck(false);
			}
			System.out.println(temp.getPreferck());
		}
		
		return result;
	}

	// 팁 전체 개수 (페이징 용)
	@Override
	public long countEstList() {
		int showCntPerPage = 5;
		return (long) (tipRepo.count() + 1) / showCntPerPage + 1;
	}

	// 팁 작성글 입력
	@Override
	public void saveTip(Tip tip, String id) {
		tip.setMember(memberRepo.findById(id).get());
		tipRepo.save(tip);
		System.out.println("입력값 확인 " + tipRepo.save(tip));

	}

	// 좋아요 입력
	@Override
	public void savePrefer(Tip tip, String id) {
		Tip_prefer result = new Tip_prefer();
		result.setTip(tip);
		result.setMember(memberRepo.findById(id).get());
		tipPreferRepo.save(result);
		System.out.println("좋아요 입력 결과는!!!!" + result);

	}

	// 좋아요 취소
	@Override
	public void deletePrefer(Tip tip, String id) {
		Tip_prefer result = new Tip_prefer();
		result.setTip(tip);
		result.setMember(memberRepo.findById(id).get());
		tipPreferRepo.deleteAll(tipPreferRepo.findByTipAndMember(tip, result.getMember()));
		System.out.println("좋아요 취소 결과는" + result);

	}

}
