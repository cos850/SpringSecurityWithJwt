package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        /** mustache 기본 폴더: src/main/resources
         *  뷰리졸버 설정 (application.properties 에 설정. 기본값이므로 생략하면 아래와 같이 설정됨)
         *      - prefix: template,
         *      - suffix: .mustache
         *  +) WebMvcConfig의 configureViewResolvers() 함수를 재정의하여 설정할 수 있다.
         *
         *  설정 변경 전 반환값 => src/main/resources/template/index.mustache
         *  설정 변경 후 반환 값 => src/main/resources/template/index.html
         */
        return "index";

    }
    
    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/login")
    public @ResponseBody String login() {
        return "login";
    }

    @GetMapping("/join")
    public @ResponseBody String join() {
        return "join";
    }

    @GetMapping("/joinProc")
    public @ResponseBody String joinProc() {
        return "회원가입 완료됨";
    }
}
