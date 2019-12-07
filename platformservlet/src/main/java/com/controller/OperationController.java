package com.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.model.*;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ProjectRepository;
import com.dao.ComplainRepository;
import com.dao.ChildFormRepository;
import com.utils.Notification;

@RestController
@Transactional
public class OperationController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ChildFormRepository child;
    @Autowired
    ComplainRepository rr;
    @Autowired
    TokenController tc;

    @Resource
    private PayService payService;

    @Resource
    private BidService bidService;

    @Resource
    private ProjectService projectService;

    @Resource
    private UserService userService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private ComplainService complainService;

    @PostMapping("company_action")
    public String company_action(@RequestParam("id") Integer projectId, @RequestParam("action") Integer action,
                                 @RequestParam(value = "studioid", required = false) Integer studioid,
                                 @RequestParam(value = "stepid", required = false) Integer stepid,
                                 @RequestParam(value = "reason", required = false) String reason,
                                 @RequestParam(value = "table", required = false) String table,
                                 @RequestParam(value = "measure", required = false) Integer measure,
                                 @RequestParam(value = "money", required = false) Float rate,
                                 @RequestParam(value = "companyRate", required = false) Float companyRate,
                                 @RequestParam(value = "headings", required = false) String headings,
                                 @RequestParam(value = "contents", required = false) String contents,
                                 @RequestParam(value = "isfirst", required = false) Integer isfirst, HttpServletRequest request) {

        Integer companyId = (Integer) request.getSession().getAttribute("id");
        if (companyId == null) {
            return "NoPermission";
        }
        if (!permissionService.checkCompanyProject(companyId, projectId)) return "NoPermission";

        Optional<Project> op = projectService.getProject(projectId);
        if (!op.isPresent()) {
            return "notFound";
        }
        Project project = op.get();

        switch (action) {
            case 0:
                return bidService.pick(project.getId(), studioid);
            case 1:
                JSONArray array = JSON.parseArray(table);
                Map<Integer, Float> priceMap = new HashMap<>();
                array.forEach(it -> {
                    JSONObject json = (JSONObject) it;
                    priceMap.put(json.getInteger("part"), json.getFloat("price"));
                });
                return projectService.setPrice(projectId, priceMap);
            case 2:
                return projectService.urgeStudio(projectId);
            case 3:
                return projectService.cacelProject(projectId);
            case 4:
                return payService.payDepositToStudio(projectId);
            case 5:
                return payService.payInAdvanced(projectId, project.getPayinadvance());
            case 6:
                ChildForm cf = projectService.getPart(project.getId(), stepid);
                return payService.payPart(cf.getId(), cf.getPayPrice());
            case 7:
            case 11: {
                return complainService.add(
                        project.getCompanyID(),
                        project.getStudioID(),
                        reason,
                        projectId,
                        rate
                );
            }
            case 8:
                return projectService.passStep(project.getId(), stepid);
            case 9:
                if (measure == 0) {
                    return projectService.punishStepMoney(projectId, stepid, rate);
                }
                if (measure == 1) {
                    return projectService.restart(projectId, stepid);
                }
                if (measure == 2) {
                    return projectService.cacelProject(projectId);
                }
                break;
            case 10:
                projectRepository.updateCompanyRate(companyRate, companyId);
                break;
        }
        return "false";
    }

    @PostMapping(value = "studio_action", produces = "application/json;charset=UTF-8")
    public String studio_action(@RequestParam("id") Integer projectId, @RequestParam("action") Integer action,
                                @RequestParam(value = "reason", required = false) String reason,
                                @RequestParam(value = "studioRate", required = false) Double studioRate,
                                @RequestParam(value = "stepid", required = false) Integer stepId,
                                @RequestParam(value = "measure", required = false) Integer measure,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "money", required = false) Float money,
                                @RequestParam(value = "headings", required = false) String headings,
                                @RequestParam(value = "contents", required = false) String contents,
                                @RequestParam(value = "table", required = false) String table,
                                @RequestParam(value = "quote", required = false) Integer quote,
                                HttpServletRequest request) throws IOException {

        Integer studioId = (Integer) request.getSession().getAttribute("id");
        if (studioId == null) {
            return "NoPermission";
        }
        if (!permissionService.checkStudioProject(studioId, projectId) && action != 0) return "NoPermission";

        Optional<Project> op = projectService.getProject(projectId);
        if (!op.isPresent()) {
            return "notFound";
        }
        Project project = op.get();

        switch (action) {
            case 0:
                return bidService.bid(projectId, studioId, quote);
            case 1:
                JSONArray array = JSON.parseArray(table);
                AtomicInteger count = new AtomicInteger();
                List<ChildForm> formList = new ArrayList<>();
                array.forEach(it -> {
                    JSONObject json = (JSONObject) it;
                    ChildForm childForm = new ChildForm();
                    childForm.setName(json.getString("name"));
                    childForm.setTime(json.getInteger("time"));
                    childForm.setPart(count.get());
                    childForm.setInfo(json.getString("info"));
                    childForm.setPrice((float) 0);
                    childForm.setState(0);
                    childForm.setProjectId(projectId);
                    count.getAndIncrement();
                    formList.add(childForm);
                });
                projectService.setForm(project.getId(), formList);
                return "success";
            case 2:
                projectService.urgeCompany(projectId);
                return "success";
            case 3:
                return projectService.cacelProject(projectId);
            case 4:
                return projectService.startProject(projectId);
            case 5:
                return payService.payDepositToCompany(project.getId(), project.getPrice() * 0.1);
            case 6:
                projectService.finish(file, projectId, stepId);
                return "success";
            case 7:
                child.updateState(3, projectId, project.getCurrent());
                return "success";
            case 8: {
                Complain r = new Complain();
                r.setToid(project.getCompanyID());
                r.setPrjid(projectId);
                r.setReason(reason);
                r.setFromid(studioId);
                r.setType(0);
                r.setMoney(money);
                r.setState(0);
                rr.save(r);
                child.updateState(9, studioId, stepId);
                return "success";
            }
            case 9:
                if (measure == 0) {
                    return projectService.punishDeposit(project.getId(), stepId, money);
                }
                if (measure == 1) {
                    return projectService.putOffPay(projectId,stepId);
                }
                if (measure == 2) {
                    projectRepository.update_state(7, projectId);
                    Format f = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendar = new GregorianCalendar();
                    Date date = new Date();
                    try {
                        calendar.setTime(ff.parse(f.format(date.getTime())));
                    } catch (ParseException e1) {

                        e1.printStackTrace();
                    }
                    calendar.add(Calendar.DATE, 1);
                    date = calendar.getTime();
                    projectRepository.updateCountdown(date, projectId);
                }
                return "success";
            case 10:
                Notification.send(userService.getToken(studioId), headings, contents);
                return "success";
            case 11:
                projectRepository.updateStudioRate(studioRate, project.getCompanyID());
                return "success";
            case 12: {
                Complain r = new Complain();
                r.setToid(project.getCompanyID());
                r.setPrjid(projectId);
                r.setReason(reason);
                r.setFromid(studioId);
                r.setType(1);
                r.setMoney(money);
                r.setState(0);
                rr.save(r);
                projectRepository.update_state(8, projectId);
                return "success";
            }
        }
        return "fail";
    }
}
