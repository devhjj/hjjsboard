package com.study.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.constant.Method;
import com.study.domain.UserDto;
import com.study.service.MailSendService;
import com.study.service.UserService;
import com.study.util.UiUtils;

@Controller
public class UserController extends UiUtils{
	
	@Autowired
    private UserService userService;

    @Autowired
    private MailSendService mss;
    
 	@GetMapping(value = "/member/signin.do")
 	public String login() {
 		return "user/sign-in";
 	}
 	
 	@GetMapping(value = "/member/signup.do")
 	public String signUp() {
 		return "user/sign_up";
 	}
 	
 	
	// 회원가입
    @ResponseBody
    @RequestMapping(value = "/member/signup.do", method=RequestMethod.POST)
	public Map<String, Object> signUp(@RequestBody UserDto userDto) {
		Map<String, Object> response = new HashMap<>();
		
		// 유효성 검사 시작
		// 이메일 정규 표현식 "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		// naver.com 메일만 가능하도록 처리
		String emailRegex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@naver.com$";
		Matcher emailMatcher = Pattern.compile(emailRegex).matcher(userDto.getEmail());
		
		// 영문, 숫자, 특수문자 포함 8 ~ 15 자리
		String passwordRegex = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*?&`~'\"+=])[A-Za-z[0-9]$@$!%*?&`~'\"+=]{8,15}$";
		Matcher passwordMatcher = Pattern.compile(passwordRegex).matcher(userDto.getPassword());		
		
		if(!emailMatcher.find()) {
			response.put("email", true);
			return response;
		}else if(!passwordMatcher.find()) {
			response.put("password", true);
			return response;
		}else if(userService.findUserByEmail(userDto.getEmail()).isPresent()) {
			response.put("duplicate", true);
			return response;
		}
		// 유효성 검사 끝
		
		// DB에 기본정보 insert
		boolean result = userService.createUser(userDto);
		response.put("success", result);

		if(result) {
			//임의의 authKey 생성 후 이메일 발송
	        String authKey = mss.sendAuthMail(userDto.getEmail());
	        userDto.setAuth(authKey);

	        Map<String, String> map = new HashMap<String, String>();
	        map.put("email", userDto.getEmail());
	        map.put("auth", userDto.getAuth());

	        //DB에 authKey 업데이트
	        userService.updateAuth(map);
		}
        
		return response;
	}
    
    @GetMapping("/member/signupConfirm.do")
    public String signUpConfirm(@RequestParam Map<String, String> map, Model model){
    	//email, authKey 가 일치할경우 authStatus 업데이트
    	if(userService.updateUserStatus(map)) {
    		return showMessageWithRedirect("가입 완료. 로그인해주세요.", "/board/list.do", Method.GET, null, model);
    	}else {
    		return showMessageWithRedirect("가입 실패. 관리자에게 문의해주세요. (문의: hjjdev@gmail.com)", "/board/list.do", Method.GET, null, model);
    	}
    	
    }
 	
}
