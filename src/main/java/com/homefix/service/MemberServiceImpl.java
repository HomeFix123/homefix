package com.homefix.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.homefix.domain.Brag;
import com.homefix.domain.Member;
import com.homefix.domain.Prefer;
import com.homefix.domain.Tip;
import com.homefix.persistence.BragRepository;
import com.homefix.persistence.MemberRepository;
import com.homefix.persistence.PreferRepository;
import com.homefix.persistence.TipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepo;

    @Value("${spring.mail.username}")
    private String sender;
	
	//이메일 유효성 체크
	@Override
	public String memEmail(String email) {
		String message = "Y";
		if (memberRepo.findByEmail(email).size() > 0) {
			message= "N";
		}
		System.out.println(memberRepo.findByEmail(email));
		System.out.println(email+"여기까지 와라");
		return message;
	}
	
	//아이디 중복 체크
	@Override
	public String memIdChack(String id) {
		System.out.println(memberRepo.countById(id));
		String message = "Y";
		if (memberRepo.countById(id) > 0) {
			message= "N";
		}
		System.out.println(id+"여기까지 와라");
		System.out.println(memberRepo.countById(id));
		return message;
	}

	//닉네임 중복 체크
	@Override
	public String memNickChack(String nickname) {
		String message = "Y";
		if (memberRepo.findByNickname(nickname).size() > 0) {
			message= "N";
		}
		System.out.println(memberRepo.findByEmail(nickname));
		System.out.println(nickname+"닉네임 중복체크 여기까지옴");
		return message;
	}

	//회원가입
	@Override
	public void memberInsert(Member mem) {
		memberRepo.save(mem);
		
	}
	
	//로그인
	@Override	//String - > Member :: 시큐리티 사용시 이렇게 변경!
//	public String login(Member mem) {
	public Member login(Member mem) {
		System.out.println("멤버레포에서 넘어왔나?" + memberRepo.findByIdAndPassword(mem.getId(), mem.getPassword()));
		List<Member> list = memberRepo.findByIdAndPassword(mem.getId(), mem.getPassword());
		String message = null;
		if (list.size() > 0) {
			mem = list.get(0);
			/* message = list.get(0).getName(); */
//			message = list.toString();
		} else {
			return null;
		}
		return mem;
		/**
		 * test1
		 * test1
		 * Member Optional<Member>
		 */
//		try {
//			Member member = memberRepo.findMemberByEmail(mem.getEmail());
//			System.out.println("확인용!!!!!!!!!이메일!!! :: " + mem.getEmail());
//			if(member !=null) {
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//				if(encoder.matches(mem.getPassword(),member.getPassword())) {
//					mem.setId(member.getId());
//					mem.setEmail(member.getEmail());
//					mem.setName(member.getName());
//					return mem;
//				}else {
//					throw new RuntimeException("passwordError");
//				}
//			}
//			throw new RuntimeException("notExist");
//		}catch(Exception e) {
//			throw new RuntimeException("fail" + e);
//		}
	}

	//회원정보 수정
	@Override
	public void updateMember(Member mem) {
		Member member = memberRepo.findById(mem.getId()).get();
		member.setId(mem.getId());
		member.setName(mem.getName());        
		member.setNickname(mem.getNickname());
		member.setPassword(mem.getPassword());
		member.setEmail(mem.getEmail());      
		member.setZipcode(mem.getZipcode());  
		member.setAddr(mem.getAddr());        
		member.setAddrd(mem.getAddrd());      
		member.setFav(mem.getFav());          
		memberRepo.save(member);
		System.out.println("수정 :::::: " + mem);
	}
		
	
	//임시비밀번호 발급용
	public boolean userEmailCheck(String email, String id){

        Member mem = memberRepo.findById(id).get();
        if(mem!=null && mem.getEmail().equals(email)) {
            return true;
        }
        else {
            return false;
        }
    }

	//화면에 값 출력하려고 만든건데 필요없나..?
	@Override
	public List<Member> myPageList(Member mem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String memberDelete(Member mem) {
		Member memb = memberRepo.findById(mem.getId()).get();
		if (memb.getPassword().equals(mem.getPassword()) ) {
			memberRepo.deleteById(mem.getId());
			return "Y";
		}
		return "N";
	}

	@Autowired
	private BragRepository bragRepo;
	
	//개인이 작성한 후기 목록 , int page
	@Override
	public List<Brag> getMyReviewList(String id) {
//		int showCntPerPage = 3;	//한번에 보여줄 게시물의 수
//		Pageable pageable = PageRequest.of(page-1, showCntPerPage,Sort.by("id").descending());
		Member mem = memberRepo.findById(id).get();
		System.out.println("세션 잘 넘어왔는지 "+id);
	
		return bragRepo.findByMember(mem);
	}
	
	@Autowired
	private TipRepository tipRepo;

	//개인이 작성한 팁 목록
	@Override
	public List<Tip> getMyTip(String id) {
		Member mem = memberRepo.findById(id).get();
		System.out.println("세션 잘 넘어왔는지 "+id);
		List<Tip> tip =  tipRepo.findByMember(mem);
		return tip;
	}

	@Autowired
	private PreferRepository loveRepo;
	
	@Override
	public List<Prefer> getMyLove(String id) {
		Member mem = memberRepo.findById(id).get();
		System.out.println("세션 잘 넘어왔는지 "+id);
		List<Prefer> love =  loveRepo.findByMember(mem);
		return love;
	}
	
	
}
