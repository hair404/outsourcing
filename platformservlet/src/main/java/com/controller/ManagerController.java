package com.controller;

import com.common.Environment;
import com.dp.AdDataProcessor;
import com.dp.VerificationDataProcessor;
import com.service.ComplainService;
import com.service.UserService;
import com.type.AdState;
import com.type.UserType;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.dao.AccountRepository;
import com.dao.ActivityRepository;
import com.dao.AdvertisementRepository;
import com.dao.CtgRepository;
import com.dao.FundRepository;
import com.dao.ComplainRepository;
import com.dao.UserRepository;
import com.service.AdService;
import com.utils.JsonUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController

public class ManagerController {
    @Resource
    UserRepository ur;
    @Resource
    AccountRepository ar;
    @Resource
    AdService adService;
    @Resource
    CtgRepository cr;
    @Resource
    private ActivityRepository activityRepository;
    @Resource
    FundRepository fr;

    @Resource
    private AdDataProcessor adDataProcessor;

    @Resource
    private VerificationDataProcessor verificationDataProcessor;

    @Resource
    private ComplainService complainService;

    @Resource
    private UserService userService;


    @PostMapping("manager")
    public Object manager(HttpServletRequest request,
                          @RequestParam("state") Integer state,
                          @RequestParam(value = "status",required = false,defaultValue = "0") int status,
                          @RequestParam(name = "type", required = false) Integer type,
                          @RequestParam(name = "text", required = false) String text,
                          @RequestParam(name = "isVerified", required = false) Integer isVerified) {

        HttpSession session = request.getSession();

        if (!Environment.DEBUG) {
            if (UserType.ADMIN.getId() != (Integer) session.getAttribute("type") && UserType.EXPERT.getId() != (Integer) session.getAttribute("type")) {
                return "fail";
            }
        }

        int id = (int) session.getAttribute("id");

        if (state == 0) {
            return verificationDataProcessor.getVerifications(type, status, text);
        } else if (state == 1) {
            return adDataProcessor.getAdvertisement(type, text);
        } else if (state == 2) {
            if (text != null)
                return JSONArray.parseArray(JSON.toJSONString(fr.findByPrjnameLike(text)));
            return JSONArray.parseArray(JSON.toJSONString(fr.findAll()));
        } else if (state == 3) {
            return JSONArray.parseArray(JSON.toJSONString(activityRepository.findAll()));
        } else if (state == 4) {
            //TODO
        } else if (state == 5) {
            return complainService.getComplainByExpert(id);
        }
        return null;
    }

    @PostMapping("getCtg")
    public String getCtg() {
        return cr.getOne((long) 1).getCtg();
    }

    @PostMapping("manager_action")
    public String managerAction(HttpServletRequest request,
                                @RequestParam("state") Integer state,
                                @RequestParam(name = "id", required = false) Integer id,
                                @RequestParam(name = "solr_id", required = false) String solr_id,
                                @RequestParam(name = "do", required = false) Integer action,
                                @RequestParam(name = "ctg", required = false) String ctg) {
        HttpSession session = request.getSession();
        if (UserType.ADMIN.getId() != (int) session.getAttribute("type")) {
            return "fail";
        }
        switch (state) {
            case 0:
                //删除用户
                return userService.deleteUser(id);
            case 1:
                //广告审核
                if (action == 0) {
                    return adService.judge(id, AdState.REJECT);
                } else if (action == 1) {
                    return adService.judge(id, AdState.PASS);
                }
                return "WrongParam";
            case 2:

                break;
            case 3:
                activityRepository.deleteById(id);
                break;
            case 4:
                cr.updateCtg(ctg);
                break;
            case 5:

                break;
            case 6:
                //直接计算评分
                break;
            case 7: //审核
                //判断 action 是否合法
                if (action != 0 && action != 1) {
                    return "UnsupportedAction";
                }
                return userService.judgeVerification(id, action == 1);
        }
        return "success";
    }

}
