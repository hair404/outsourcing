package com.controller;


import com.dp.AdDataProcessor;
import com.model.User;
import com.service.PayService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.AdService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdController {
    @Resource
    private AdService adService;

    @Resource
    private PayService payService;

    @Resource
    private AdDataProcessor adDataProcessor;

    @PostMapping("ad_register_prj")
    public String establish_ad1(@RequestParam("ad_price") float ad_price, @RequestParam("id") Integer id) {
        adService.insert_ad_project(ad_price, id);
        return "success";
    }

    @PostMapping("ad_register_stu")
    public String adRegisterStudio(HttpServletRequest request, @RequestParam("ad_price") float adPrice) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            return "NotLogin";
        }
        int id = (int) session.getAttribute("id");
        return payService.payStudioAd(id, adPrice);
    }

    @RequestMapping("recommend_project")
    public String recommend_pro(@RequestParam("first") Integer first) {
        return adService.recommend_ad_project(first);
    }

    @RequestMapping("recommend_studio")
    public List<User> recommend_stu(@RequestParam("first") Integer first, @RequestParam("end") int end) {
        return adDataProcessor.getAdStudio(first, end);
    }

}
