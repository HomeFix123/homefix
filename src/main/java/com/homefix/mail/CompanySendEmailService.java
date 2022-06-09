package com.homefix.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.homefix.domain.Company;
import com.homefix.persistence.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanySendEmailService{

    @Autowired
    CompanyRepository companyRepo;

    @Autowired
    private JavaMailSender mailSender;
    
    private static final String FROM_ADDRESS = "wpaa91@gmail.com";

 

    public MailDto createMailAndChangePassword(String email, String name){
        String str = getTempPassword();
        MailDto dto = new MailDto();
        dto.setAddress(email);
        dto.setTitle("[ HOME - FIX ] 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. HOME - FIX 임시비밀번호 안내 관련 이메일 입니다." + "[" + name + "]" +"님의 임시 비밀번호는 "
        + str + " 입니다.");
        updatePassword(str,email);
        return dto;
    }

    public void updatePassword(String str,String email){
    	String password = str;
    	//아래꺼 사용할경우 비밀번호 암호화되서 들어감!!
//        String password = EncryptionUtils.encryptMD5(str);
        Company temp = companyRepo.findCompanyByEmail(email);
        temp.setPass(password);
        companyRepo.save(temp);
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
        message.setFrom(CompanySendEmailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }


}

