package com.service;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.FileCommon;
import com.common.result.ProjectPage;
import com.utils.TimeTool;
import org.apache.solr.client.solrj.SolrServerException;
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

@SuppressWarnings("ALL")
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
    File_projectRepository fpr;

    @Resource
    private SearchService searchService;

    @Resource
    private FileCommon fileCommon;

    @Resource
    UserService userService;

    @Value("${url}")
    private static String url;

    // a project information(the bid system)
    public String getInfo(String solr_id, Integer user_id) {

        Project project = projectRepository.getInfoBySolrId(solr_id);
        checkDelay(project);
        Integer project_id = project.getId();
        JSONObject projectInfo = (JSONObject) JSON.toJSON(project);

        if (project.getCompanyID().equals(user_id)) {
            projectInfo.put("companyID", "self");
        }

        if (project.getIsform() == 1) {
            List<ChildForm> childForms = childFormRepository.getChildForm(project_id);
            JSONArray arr = new JSONArray();
            int day = 0;
            for (ChildForm it : childForms) {
                JSONObject json = (JSONObject) JSON.toJSON(it);
                if (project.getIsconfirm() != 1){
                    arr.add(json);
                    continue;
                }
                day += it.getTime();
                Date deadline = new Date();
                deadline.setTime(project.getStartTime().getTime() + day * 3600 * 24 * 1000);
                json.put("deadline", TimeTool.formatTime(deadline, "yyyy-MM-dd"));
                arr.add(json);
            }
            if (project.getCurrent() != null) {
                ChildForm child = childForms.get(project.getCurrent());
                if (child.getState() == 5)
                    projectInfo.put("payDeadline", project.getPayDeadline());
                if (child.getState() == 2) {
                    projectInfo.put("path", fpr.get_file(project_id, project.getCurrent()));
                }
            }
            projectInfo.put("table", arr);
        }

        switch (project.getState()) {
            case 1:
                JSONArray enroll = new JSONArray();
                List<Bid> rs = bidRepository.findAllByProjectId(project_id);
                if (!rs.isEmpty()) {
                    for (int i = 0; i < rs.size(); i++) {
                        Bid bid = rs.get(i);
                        User userInfo = userRepository.getInfoById(bid.getStudioId());
                        JSONObject user_info = (JSONObject) JSON.toJSON(userInfo);
                        user_info.put("tag", userService.getTagIntList(user_id));
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

    private void checkDelay(Project project) {
        if (project.getPayDeadline() != null && project.getPayDeadline().getTime() < System.currentTimeMillis()) {
            project.setState(6);
            projectRepository.save(project);
        }
        if (project.getCurrent() == null) {
            return;
        }
        Optional<ChildForm> optionalChildForm = childFormRepository.findById(project.getCurrent());
        if (!optionalChildForm.isPresent()) {
            return;
        }
        ChildForm childForm = optionalChildForm.get();
        if (childForm.getState() == 1) {
            int day = getChildTotalDay(project.getId(), childForm.getPart());
            if (project.getStartTime().getTime() + day * 3600 * 24 * 1000 < System.currentTimeMillis()) {
                childForm.setState(3);
                childFormRepository.save(childForm);
            }
        }
    }

    public void finish(MultipartFile file, int prj_id, int step_id) throws IOException {
        File dest = fileCommon.saveFile(file, prj_id, step_id);
        FileProject prj = new FileProject();
        prj.setUrl("file/" + prj_id + "/" + step_id + "/" + dest.getName());
        prj.setPrj_id(prj_id);
        prj.setIspassed(0);
        prj.setStep_id(step_id);
        fpj.save(prj);
        childFormRepository.updateState(2, prj_id, step_id);
    }

    /**
     * 扣除进度款
     *
     * @param projectId 项目id
     * @param money     扣款金额
     */
    public String punishStepMoney(int projectId, int stepId, Float rate) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Optional<ChildForm> optionalChildForm = childFormRepository.findByProjectIdAndPart(projectId, stepId);
        if (!optionalChildForm.isPresent()) {
            return "fail";
        }
        if (!optionalProject.isPresent()) {
            return "fail";
        }

        Project project = optionalProject.get();
        ChildForm childForm = optionalChildForm.get();
        float money = rate / 100 * childForm.getPrice();

        if (childForm.getState() != 2 && childForm.getState() != 3) {
            return "fail";
        }
        if (project.getCurrent() != stepId) {
            return "fail";
        }

        if (rate > 30) {
            return "tooMuch";
        }

        childForm.setPayPrice(childForm.getPrice() - money);
        childForm.setState(5);
        project.setPayDeadline(new java.sql.Date(System.currentTimeMillis() + 259200000));

        projectRepository.save(project);
        childFormRepository.save(childForm);
        nextStep(projectId);
        return "success";
    }

    /**
     * 惩罚定金
     *
     * @param projectId 项目ID
     * @param stepId    步骤ID
     * @param money     罚金
     * @return
     */
    public String punishDeposit(int projectId, int stepId, Float rate) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Optional<ChildForm> optionalChildForm = childFormRepository.findByProjectIdAndPart(projectId, stepId);
        if (!optionalChildForm.isPresent()) {
            return "fail";
        }
        if (!optionalProject.isPresent()) {
            return "fail";
        }

        Project project = optionalProject.get();
        ChildForm childForm = optionalChildForm.get();
        float money = rate / 100 * childForm.getPrice();

        if (project.getState() != 6) {
            return "fail";
        }
        if (project.getCurrent() != stepId) {
            return "fail";
        }

        if (money / project.getPrice() * 0.1 > 0.3 || money > project.getRestDeposit()) {
            return "tooMuch";
        }

        project.setRestDeposit(project.getRestDeposit() - money);
        childForm.setState(5);
        project.setPayDeadline(new java.sql.Date(System.currentTimeMillis() + 259200000));

        projectRepository.save(project);
        childFormRepository.save(childForm);
        return "success";
    }

    /**
     * 重置项目
     *
     * @param projectId 项目id
     * @param stepId    步骤id
     * @return
     */
    public String restart(int projectId, int stepId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Optional<ChildForm> optionalChildForm = childFormRepository.findByProjectIdAndPart(projectId, stepId);
        if (!optionalChildForm.isPresent()) {
            return "fail";
        }
        if (!optionalProject.isPresent()) {
            return "fail";
        }

        Project project = optionalProject.get();
        ChildForm childForm = optionalChildForm.get();

        if (project.getState() != 3) {
            return "fail";
        }
        if (project.getCurrent() != stepId) {
            return "fail";
        }

        childForm.setState(1);
        childFormRepository.save(childForm);
        return "success";
    }

    /**
     * 推迟付款
     *
     * @param projectId 项目ID
     * @param stepId    步骤ID
     * @return
     */
    public String putOffPay(int projectId, int stepId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        Optional<ChildForm> optionalChildForm = childFormRepository.findByProjectIdAndPart(projectId, stepId);
        if (!optionalProject.isPresent()) {
            return "fail";
        }
        if (!optionalChildForm.isPresent()) {
            return "fail";
        }

        Project project = optionalProject.get();
        ChildForm childForm = optionalChildForm.get();

        if (project.getState() != 6) {
            return "fail";
        }
        if (project.getCurrent() != stepId) {
            return "fail";
        }

        childForm.setState(5);

        projectRepository.save(project);
        childFormRepository.save(childForm);
        return "success";
    }

    public String cacelProject(int projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (!optionalProject.isPresent()) {
            return "fail";
        }

        Project project = optionalProject.get();
        project.setState(7);
        projectRepository.save(project);
        return "success";
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
                          String solr_id, String entity, Integer pia) throws IOException, SolrServerException {
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
        searchService.insertProject(project);
    }

    public ProjectPage getUserProject(int userId, int page, int size, int state) {
        List<Project> projects;
        if (state == 0) {
            projects = projectRepository.getByUserId(userId);
        } else {
            projects = projectRepository.getByStateAndUserId(state, userId);
        }
        return new ProjectPage(projects.size(), projects.subList((page - 1) * size, Math.min(page * size, projects.size())));
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

    /**
     * 设置Form
     *
     * @param projectId 项目ID
     * @param forms     进度表
     */
    public void setForm(int projectId, List<ChildForm> forms) {
        Optional<Project> op = projectRepository.findById(projectId);
        if (!op.isPresent()) {
            return;
        }
        Project project = op.get();
        project.setTotalPart(forms.size());
        project.setCurrent(0);
        project.setIsform(1);
        projectRepository.save(project);
        childFormRepository.saveAll(forms);
    }

    /**
     * 通过小进度
     *
     * @param projectId 项目ID
     * @param stepId    步骤ID
     */
    public void passStep(int projectId, int stepId) {
        Optional<ChildForm> op = childFormRepository.findByProjectIdAndPart(projectId, stepId);
        if (!op.isPresent()) {
            return;
        }
        ChildForm childForm = op.get();
        childForm.setState(5);
        childForm.setPayPrice(childForm.getPrice());
        childFormRepository.save(childForm);
    }

    public void nextStep(int projectId) {
        Optional<Project> op = projectRepository.findById(projectId);
        if (!op.isPresent()) {
            return;
        }
        Project project = op.get();
        int current = 0;
        if (project.getCurrent() != null) {
            current = project.getCurrent();
        }
        if ((project.getTotalPart() - 1) != current) {
            childFormRepository.updateState(1, project.getId(), project.getCurrent() + 1);
            project.setCurrent(current + 1);
        } else {
            project.setState(5);
        }

        projectRepository.save(project);
    }

    /**
     * 获取到指定步骤一共需要多少步
     *
     * @param projectId 项目ID
     * @param stepId    步骤ID
     * @return
     */
    public int getChildTotalDay(int projectId, int stepId) {
        List<ChildForm> childForms = childFormRepository.getChildForm(projectId);
        Collections.sort(childForms, new Comparator<ChildForm>() {
            @Override
            public int compare(ChildForm o1, ChildForm o2) {
                if (o1.getPart() < o2.getPart()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        int day = 0;
        for (ChildForm childForm : childForms) {
            day += childForm.getTime();
            if (childForm.getPart() == stepId) {
                return day;
            }
        }
        return day;
    }

}
