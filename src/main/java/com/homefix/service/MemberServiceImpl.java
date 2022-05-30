package com.homefix.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.domain.MailDto;
import com.homefix.domain.Member;
import com.homefix.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	public JavaMailSender javaMailSender;

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
	@Override
	public String login(Member mem) {
		System.out.println("멤버레포에서 넘어왔나?" + memberRepo.findByIdAndPassword(mem.getId(), mem.getPassword()));
		List<Member> list = memberRepo.findByIdAndPassword(mem.getId(), mem.getPassword());
		String message = null;
		if (list.size() > 0) {
			message = list.get(0).getName();
		}
		return message;
	}
	
	//개인 회원 탈퇴
	

	//임시 비밀번호 발급
//	@Override
//	public String sendForgotPassword(String email) {
//        List<Member> userList = memberRepo.findByEmail(email);
//        Member user = userList.get(0);
//        if(user==null){
//            throw new RuntimeException("User not found with email : " + email);
//        }else{
//            String tempPassword = getTempPassword();
//            //user.setPassword(PasswordEncoder.encode(tempPassword));
//            user.setPassword(tempPassword); //발표 후 시큐리티 적용 예정
//            memberRepo.save(user);
//			
//            //메세지를 생성하고 보낼 메일 설정 저장
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(email);
//            message.setFrom(sender);
//            message.setSubject(user.getName()+" : 임시비밀번호가 발급되었습니다.");
//            message.setText("안녕하세요!" + user.getName() + "님!" + "\n Home-Fix에서 임시비밀번호를 발급해드렸습니다. \n 임시 비밀번호 : " + tempPassword);
//            javaMailSender.send(message);
////            return "Temporary password sent to your email.";
//            return "ok";
//        }
//    }
//	
//	//임시 비밀번호 발급
//    public String getTempPassword(){
//        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
//                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
//
//        String str = "";
//
//        int idx = 0;
//        for (int i = 0; i < 10; i++) {
//            idx = (int) (charSet.length * Math.random());
//            str += charSet[idx];
//        }
//        return str;
//    }
	
	//임시 비밀번호 발급용 test
	public boolean userEmailCheck(String email, String id) {

		Member user = memberRepo.findMemberByEmail(email);
        if(user!=null && user.getId().equals(id)) {
            return true;
        }
        else {
            return false;
        }
    }

	@Override
	public void updatePassword(Member mem, String id, String password) {
		memberRepo.save(mem);
		
	}

	
	
	
	
	
	


}
