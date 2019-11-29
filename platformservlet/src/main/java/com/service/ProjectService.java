package com.service;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

import com.common.FileCommon;
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
import com.model.FileProject;
import com.model.Project;
import com.model.User;
import com.utils.JsonUtils;

import javax.annotation.Resource;

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

    @Resource
    private FileCommon fileCommon;

    @Value("${url}")
    private static String url;

    // a project information(the bid system)
    public String get_info(String solr_id, Integer user_id) {

        Project project = projectRepository.getInfoBySolrId(solr_id);
        Integer project_id = project.getId();
        JSONObject projectInfo = new JSONObject(project);

        if (project.getCompanyID().equals(user_id)) {
            projectInfo.put("companyID", "self");
        }

        if (project.getIsform() == 1) {
            List<ChildForm> child_form = childFormRepository.getChildForm(project_id);
            if (project.getCurrent() != null) {
                ChildForm child = child_form.get(project.getCurrent());
                if (child.getState() == 4 || child.getState() == 7)
                    projectInfo.put("payDeadline", project.getPayDeadline());
                if (child.getState() == 2) {
                    projectInfo.put("path", fpr.get_file(project_id, project.getCurrent()));
                }
            }
            projectInfo.put("table", JSONArray.parseArray(JsonUtils.objectToJson(child_form)));
        }

        switch (project.getState()) {
            case 1:
                JSONArray enroll = new JSONArray();
                List<Bid> rs = bidRepository.findAllByProjectId(project_id);
                if (!rs.isEmpty()) {
                    for (int i = 0; i < rs.size(); i++) {
                        Bid bid = rs.get(i);
                        User userInfo = userRepository.getInfoById(bid.getStudioId());
                        JSONObject user_info = new JSONObject(userInfo);
                        user_info.put("tag", tagDao.QueryTag(userInfo.getId()));
                        user_info.put("quote", bid.getQuote());
                        enroll.add(user_info);
                        projectInfo.put("enroll", enroll);
                    }
                } else
                    projectInfo.put("enroll", new JSONArray());
                break;
            case 3:
                Calendar calendar = new GregorianCalendar();
                Date date = project.getStartTime();
                Format f = new SimpleDateFormat("yyyy-MM-dd");
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                date = calendar.getTime();
                projectInfo.put("payDeadline", f.format(date));
                break;
            default:
                return projectInfo.toString();
        }
        return projectInfo.toString();
    }

    public void upload(MultipartFile file, int prj_id, int step_id) throws IOException {
        File dest = fileCommon.saveFile(file, prj_id);
        FileProject prj = new FileProject();
        prj.setUrl(prj_id + "/" + dest.getName());
        prj.setPrj_id(prj_id);
        prj.setIspassed(0);
        prj.setStep_id(step_id);
        fpj.save(prj);
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
            return project.getState() == 1;
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
