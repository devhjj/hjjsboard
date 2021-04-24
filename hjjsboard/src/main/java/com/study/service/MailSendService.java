package com.study.service;

import java.util.Random;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service("mss")
public class MailSendService {
    
	@Resource(name="mailSender")
    private JavaMailSenderImpl mailSender;
	private int size = 6;

    //인증키 생성
    private String getKey(int size) {
        this.size  = size;
        return getAuthCode();
    }

    //인증코드 난수 발생
    private String getAuthCode() {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        while(buffer.length() < size) {
			if (random.nextBoolean()) {
				buffer.append((char) ((int) (random.nextInt(26)) + 97));
			} else {
				buffer.append((random.nextInt(10)));
			}
        }

        return buffer.toString();
    }

    //인증메일 보내기
    public String sendAuthMail(String email) {
        //6자리 난수 인증번호 생성
        String authKey = getKey(8);

        //인증메일 보내기
        MimeMessage mail = mailSender.createMimeMessage();
        String mailContent = "<h1>[HJJ's Board 가입 인증]</h1><br><p>정상적인 회원가입을 위해 아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
                            + "<a href='http://localhost:8080/member/signupConfirm.do?email=" 
                            + email + "&auth=" + authKey + "' target='_blenk'>이메일 인증 확인</a>";

        try {
            mail.setSubject("HJJ's Board 회원가입 이메일 인증 ", "utf-8");
            mail.setText(mailContent, "utf-8", "html");
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return authKey;
    }
}