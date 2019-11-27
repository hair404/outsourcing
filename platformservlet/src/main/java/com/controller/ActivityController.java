package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import com.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ActivityRepository;
import com.service.ActivityService;
import com.utils.JsonUtils;

@RestController
public class ActivityController {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    ActivityService as;
    @Autowired
    ActivityRepository ar;

    @PostMapping("activity_register")
    public void register_activity(@RequestParam("img") MultipartFile file, @RequestParam("url") String url) {
        as.register_activity(file, url);
    }

    @PostMapping("activity")
    public String activity() {
        return JsonUtils.objectToJson(ar.findAll());
    }

    private static String url ="/usr/local/tomcat/work/Catalina/localhost/Platform/";;
    private static final String root_ac = url + "img/activity_img";

    @GetMapping(value = "/activity_img/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_activity_img(@PathVariable String filename) {
    	try {
			return ResponseEntity.ok(FileTools.getBytes(new File(root_ac + "/" + filename)));
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
