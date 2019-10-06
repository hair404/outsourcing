package com.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
public class ManagerController {

	@PostMapping("manager")
     public String manager(@RequestParam("state") Integer state) {
		return null;
	}
}
