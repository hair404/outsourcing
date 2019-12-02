package com.controller;

import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TokenController {

    @Resource
    private UserService userService;

    @PostMapping("token")
    public String token(
            HttpServletRequest request,
            @RequestParam("token") String token) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return "NotLogin";
        }
        int id = (int) session.getAttribute("id");
        userService.setToken(id, token);
        return "success";
    }
}
