package com.controller;

import java.io.File;

import com.common.PictureCommon;
import com.service.UserService;
import com.type.PictureType;
import com.utils.FileTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.UserRepository;
import com.utils.UuidUtils;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ImgController {
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    UserRepository userRepository;

    @Resource
    UserService userService;

    @PostMapping("uploadimg")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (file.isEmpty()) {
            return "null";
        }
        if (id != null) {
            File dest = PictureCommon.saveImage(file, PictureType.USER);
            String url = "/userimg/" + dest.getName();
            userService.updateBackground(id, url);
            return url;
        }
        return "false";
    }

    @PostMapping("upload_avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (file.isEmpty()) {
            return "null";
        }
        if (id != null) {
            File dest = PictureCommon.saveImage(file, PictureType.AVATAR);
            String url = "/avatar/" + dest.getName();
            userService.updateAvatar(id, url);
            return url;
        }
        return "false";
    }

    @GetMapping(value = "/userimg/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_user_img(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(PictureType.USER.getSaveFolder(), filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/avatar/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_user_avatar(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(PictureType.AVATAR.getSaveFolder(), filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/prjimg/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_prj_img(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(PictureType.PROJECT.getSaveFolder(), filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
