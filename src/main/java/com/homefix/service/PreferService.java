package com.homefix.service;

import org.springframework.data.domain.Page;

import com.homefix.domain.Prefer;

public interface PreferService {

	//좋아요 누른 후기글 목록 페이징용
	Page<Prefer> getLoveList(String id, Integer page);

	long countLoveList(String id);

}
