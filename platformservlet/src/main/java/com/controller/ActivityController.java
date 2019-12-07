package com.controller;

import java.io.File;
import java.io.IOException;

import com.type.PictureType;
import com.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.Resource;

@RestController
public class ActivityController {

    @Resource
    ActivityService activityService;
    @Resource
    ActivityRepository ar;

    @PostMapping("activity_register")
    public String register_activity(@RequestParam("img") MultipartFile file, @RequestParam("url") String url) throws IOException {
        activityService.registerActivity(file, url);
        return "success";
    }

    @PostMapping("activity")
    public String activity() {
        return JsonUtils.objectToJson(ar.findAll());
    }

    @GetMapping(value = "/activity_img/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_activity_img(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(PictureType.ACTIVITY.getSaveFolder(), filename)));
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
