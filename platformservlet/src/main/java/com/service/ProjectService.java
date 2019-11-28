package com.service;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.dao.BidRepository;
import com.dao.File_projectRepository;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.CancelReasonRepository;
import com.dao.ChildFormRepository;
import com.dao.UserRepository;
import com.model.Bid;
import com.model.ChildForm;
import com.model.File_project;
import com.model.Project;
import com.model.User;
import com.utils.JsonUtils;

@Service
public class ProjectService {
    @Autowired
    File_projectRepository fpj;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CancelReasonRepository reasonRepository;
    @Autowired
    ChildFormRepository childFormRepository;
    @Autowired
    TagDao tagDao;
    @Autowired
    File_projectRepository fpr;

    @Value("${url}")
    private static String url;

    // a project information(the bid system)
    public String get_info(String solr_id, Integer user_id) {

        Project project = projectRepository.getInfoBySolrId(solr_id);
        Integer project_id = project.getId();
        JSONObject project_info = new JSONObject(project);

        if (project.getCompanyID().equals(user_id)) {
            project_info.put("companyID", "self");
        }
        if (project.getState() == 1) {
            JSONArray enroll = new JSONArray();
            List<Bid> rs = bidRepository.get_info(project_id);
            if (!rs.isEmpty()) {
                System.out.println(rs.toString());
                for (int i = 0; i < rs.size(); i++) {
                    Bid bid = rs.get(i);
                    User userInfo = userRepository.getInfoById(bid.getStudioId());
                    JSONObject user_info = new JSONObject(userInfo);
                    user_info.put("tag", tagDao.QueryTag(userInfo.getId()));
                    user_info.put("quote", bid.getQuote());
                    enroll.add(user_info);
                    project_info.put("enroll", enroll);
                }
            } else
                project_info.put("enroll", new JSONArray());
        } else if (project.getState() == 3) {
            Calendar calendar = new GregorianCalendar();
            Date date = project.getStartTime();
            Format f = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
            project_info.put("payDeadline", f.format(date));
        } else if (project.getState() == 4) {

            List<ChildForm> child_form = childFormRepository.getChildForm(project_id);
            ChildForm child = child_form.get(project.getCurrent());
            if (child.getState() == 4 || child.getState() == 7)
                project_info.put("payDeadline", project.getPayDeadline());
            if (child.getState() == 2) {
                project_info.put("path", fpr.get_file(project_id, project.getCurrent()));
            }
            project_info.put("table", JSONArray.parseArray(JsonUtils.objectToJson(child_form)));
        } else
            return project_info.toString();
        return project_info.toString();
    }

    public void upload(MultipartFile file, int prj_id, int step_id) {
        String fileName = file.getOriginalFilename();
        String filePath = url + "//file//" + prj_id + "/" + step_id + "/";
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            File_project prj = new File_project();
            prj.setUrl("/file/" + prj_id + "/" + step_id + "/" + fileName);
            prj.setPrj_id(prj_id);
            prj.setIspassed(0);
            prj.setStep_id(step_id);
            fpj.save(prj);
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray myPrjWithoutState(Integer id, Integer first) {
        JSONArray array = new JSONArray();
        Integer size = projectRepository.findByCompanyIDOrStudioID(id).size();
        Integer page = (first - 1) / 16;
        for (int i = 0; i <= page; i++) {
            final Pageable pageable = PageRequest.of(page, 16);
            List<Project> pro = projectRepository.findByCompanyIDOrStudioID(id, pageable);
            array.add(size);
            array.addAll(pro);
        }
        return array;
    }

    public JSONArray displayPrj(Integer id, Integer first) {
        JSONArray array = new JSONArray();
        Integer size = projectRepository.findByCompanyIDOrStudioID(id).size();
        Integer page = (first - 1) / 12;
        for (int i = 0; i <= page; i++) {
            final Pageable pageable = PageRequest.of(page, 12);
            List<Project> pro = projectRepository.findByCompanyIDOrStudioID(id, pageable);
            array.add(size);
            array.addAll(pro);
        }
        return array;
    }


    public void insertPrj(String company_name
            , String name, Integer tag, Integer subtag, String img,
                          java.sql.Date releaseTime, String info, java.sql.Date deadline, float price, Integer companyId,
                          String solr_id, String entity, Integer pia) {
        Project project = new Project();
        project.setCompanyID(companyId);
        project.setCompanyName(company_name);
        project.setReleaseTime(releaseTime);
        project.setPrjname(name);
        project.setDeadline(deadline);
        project.setSolr_id(solr_id);
        project.setEntity(entity);
        project.setPayinadvance(pia);
        project.setTag(tag);
        project.setSubtag(subtag);
        project.setImg(img);
        project.setStudioID(0);
        project.setInfo(info);
        project.setState(1);
        project.setPrice(price);
        project.setHasPaid(0);
        project.setIfAd(0);
        project.setIspia(0);
        project.setIssetprice(0);
        project.setIsconfirm(0);
        project.setIsdeposit(0);
        project.setIsform(0);
        project.setCompanyPrice(price);
        projectRepository.save(project);
    }

    public JSONArray myPrj(Integer id, Integer first, Integer state) {
        JSONArray array = new JSONArray();
        Integer size = projectRepository.getProjectById(state, id).size();
        Integer page = (first - 1) / 16;
        for (int i = 0; i <= page; i++) {
            final Pageable pageable = PageRequest.of(page, 16);
            List<Project> pro = projectRepository.getProjectById(state, id, pageable);
            array.add(size);
            array.addAll(pro);
        }
        return array;
    }

    /**
     * 返回步骤
     *
     * @param projectId 项目ID
     * @param part      步骤
     * @return 步骤
     */
    public ChildForm getPart(int projectId, int part) {
        List<ChildForm> forms = childFormRepository.getChildForm(projectId);
        for (ChildForm form : forms) {
            if (form.getPart() == part) {
                return form;
            }
        }
        return null;
    }

    /**
     * 是否可投标
     *
     * @param projectId 项目ID
     * @return
     */
    public boolean canBid(int projectId) {
        Optional<Project> op = projectRepository.findById(projectId);
        if (op.isPresent()) {
            Project project = op.get();
            return project.getStudioRate() == 1;
        }
        return false;
    }

    /**
     * 获取项目
     *
     * @param projectId 项目ID
     * @return
     */
    public Optional<Project> getProject(int projectId) {
        return projectRepository.findById(projectId);
    }
}
