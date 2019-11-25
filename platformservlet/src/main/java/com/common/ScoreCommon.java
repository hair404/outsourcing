package com.common;

import com.service.UserService;
import com.type.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class ScoreCommon {

    @Resource
    private UserService userService;


    /**
     * 计算所有公司评分
     */
    public void calculateCompany() {
        Map<Integer, Double> scores = userService.getUsersScore(UserType.COMPANY);

        //计算主观评分
        scores.keySet().forEach(it -> scores.put(it, scores.get(it) * 0.25));
    }
}
