package com.controller;

import com.common.result.ResultBean;
import com.common.result.ResultCode;
import com.service.UserService;
import com.type.UserType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("payment")
@RestController
public class PaymentController {

    @Resource
    private UserService userService;

    @PostMapping("bind")
    public ResultBean bind(@SessionAttribute("id") int id,
                           @SessionAttribute("type") int type,
                           @RequestParam("account") String account) {
        if (type != UserType.COMPANY.getId() && type != UserType.STUDIO.getId()) {
            return ResultBean.fromCode(ResultCode.WRONG_ROLE);
        }
        return ResultBean.fromCode(userService.setAlipayAccount(id, account));
    }
}
