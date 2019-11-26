package com.controller;

import java.io.File;

import com.utils.FileTools;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;
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

    public static final String root_user = "img/user_img/img";
    public static final String root_avatar = "img/user_img/avatar";
    public static final String root_prj = "img/prj_img";
    private static String url = "/usr/local/tomcat/work/Catalina/localhost/Platform/";

    @PostMapping("uploadimg")
    public String upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (file.isEmpty()) {
            return "null";
        }
        if (id != null) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = url + "img/user_img/img/";
            fileName = UuidUtils.generateShortUuid() + suffixName;
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                String url = "/userimg/" + fileName;
                file.transferTo(dest);
                userRepository.updateImg(url, id);
                return url;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "false";
    }

    @PostMapping("upload_avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (file.isEmpty()) {
            return "null";
        }
        if (id != null) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String filePath = url + "img/user_img/avatar/";
            fileName = UuidUtils.generateShortUuid() + suffixName;
            File dest = new File(filePath + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                String url = "/avatar/" + fileName;
                file.transferTo(dest);
                userRepository.updateAvatar(url, id);
                return url;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "false";
    }

    @GetMapping(value = "/userimg/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_user_img(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(url + root_user + "/" + filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/avatar/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_user_avatar(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(url + root_avatar + "/" + filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/prjimg/{filename:.+}", produces = "application/octet-stream;charset = utf-8")
    public ResponseEntity<?> get_prj_img(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(FileTools.getBytes(new File(url + root_prj + "/" + filename)));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
