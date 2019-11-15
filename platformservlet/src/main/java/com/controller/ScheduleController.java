package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;

import com.utils.RedisUtils;

@Controller
public class ScheduleController {
	
	@Autowired
	RedisUtils redis;
	@Autowired
	StringRedisTemplate re;
     public void schedule() {  

     }
}
