package com.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.common.PictureCommon;
import com.model.Project;
import com.service.UserService;
import com.type.PictureType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.AdProjectRepository;
import com.dao.ProjectDao;
import com.dao.ProjectRepository;
import com.dao.UserRepository;
import com.service.ProjectService;
import com.utils.UuidUtils;

@RestController
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectDao projectDao;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AdProjectRepository adpr;
    @Autowired
    ProjectRepository projectRepository;

    @Resource
    private UserService userService;

    @PostMapping("project_info")
    public String info(HttpServletRequest request, @RequestParam("id") String solr_id) {
        HttpSession session = request.getSession();
        Integer account_id = (Integer) session.getAttribute("id");
        // Integer account_id =4;
        return projectService.get_info(solr_id, account_id);
    }

    @PostMapping("register_prj")
    public String register(HttpServletRequest request, HttpServletResponse response,
                           @RequestParam("prjname") String name, @RequestParam("tag") Integer tag,
                           @RequestParam("subtag") Integer sub_tag, @RequestParam("file") MultipartFile file,
                           @RequestParam("info") String info, @RequestParam("deadline") Date deadline,
                           @RequestParam("price") float price, @RequestParam("pia") Integer pia) throws IOException {
        String entity = "project";
        String solr_id = UuidUtils.generateShortUuid();
        Date releaseTime = new Date(System.currentTimeMillis());
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (file.isEmpty())
            return "null";
        else {
            if (id != null) {
                File dest = PictureCommon.saveImage(file, PictureType.PROJECT);
                String url = "/prjimg/" + dest.getName();
                String companyName = userService.getCompanyName(id);
                projectService.insertPrj(companyName, name, tag, sub_tag, url,
                        releaseTime, info, deadline, price, id,
                        solr_id, entity, pia);
                return "success";
            }
        }
        return "false";

    }

    @PostMapping("my_prj")
    public JSONArray myPrj(@RequestParam("state") Integer state, HttpServletRequest request,
                           @RequestParam("first") Integer first) {
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("id");
        if (id != null) {
            if (state != 0)
                return projectService.myPrj(id, first, state);
            else
                return projectService.myPrjWithoutState(id, first);
        } else
            return null;
    }

}