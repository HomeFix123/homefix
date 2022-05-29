package com.homefix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.homefix.config.EncryptionUtils;
import com.homefix.domain.MailDto;
import com.homefix.domain.Member;
import com.homefix.persistence.MemberRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SendEmailService{

	@Autowired
	private MemberService memberService;
	
	@Autowired
	MemberRepository memberRepo;

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "tlqls3169@gmail.com";



    public MailDto createMailAndChangePassword(Member mem,String email, String id){
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle(id+"님의 Home-Fix 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. Home-Fix 임시비밀번호 안내 관련 이메일 입니다." + "[" + id + "]" +"님의 임시 비밀번호는 "
        + str + " 입니다.");
        updatePassword(mem,str,email);
        return dto;
    }

    public void updatePassword(Member mem, String str,String email){
        String password = EncryptionUtils.encryptMD5(str);
		String id = memberRepo.findUserById(email).getId();
		memberService.updatePassword(mem, id,password);
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }
    
    public void mailSend(MailDto mailDto){
        System.out.println("이멜 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(SendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}
