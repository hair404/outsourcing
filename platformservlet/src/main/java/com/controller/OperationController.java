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
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.model.*;
import com.service.BidService;
import com.service.PayService;
import com.service.UserService;
import com.type.ActionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ProjectRepository;
import com.dao.RefundRepository;
import com.dao.BidRepository;
import com.dao.CancelReasonRepository;
import com.dao.ChildFormRepository;
import com.dao.ComplainReasonRepository;
import com.service.ProjectService;
import com.utils.Notification;

@RestController
@Transactional
public class OperationController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    CancelReasonRepository cancelr;
    @Autowired
    ComplainReasonRepository complainr;
    @Autowired
    ChildFormRepository child;
    @Autowired
    BidRepository bidRepository;
    @Autowired
    RefundRepository rr;
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

    @PostMapping("company_action")
    public String company_action(@RequestParam("id") Integer id, @RequestParam("action") Integer action,
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
        HttpSession session = request.getSession();
        Integer company_id = (Integer) session.getAttribute("id");
        Optional<Project> op = projectService.getProject(id);
        if (!op.isPresent()) {
            return "fail";
        }
        Project project = op.get();
        if (action == 0) {
            bidService.pick(project, studioid);
            return "success";
        } else if (action == 1) {
            List<ChildForm> l = com.alibaba.fastjson.JSONArray.parseArray(table, ChildForm.class);
            for (int i = 0; i < l.size(); i++) {
                JSONObject json_form = (JSONObject) JSON.toJSON(l.get(i));
                child.updatePrice((Float) json_form.get("price"), id, i);
            }
            projectRepository.updateIssetprice(1, id);
            return "success";
        } else if (action == 2) {
//            Notification.send(token.getToken(), headings, contents);
            return "success";
        } else if (action == 3) {
            projectRepository.update_state(7, id);
            return "success";
        } else if (action == 4) {
            return payService.payDepositToStudio(id, project.getPrice() * 0.1);
        } else if (action == 5) {
            return payService.payInAdvanced(id, project.getPayinadvance());
        } else if (action == 6) {
            ChildForm cf = projectService.getPart(project.getId(), project.getCurrent());
            return payService.payPart(cf.getId(), cf.getPayPrice());
        } else if (action == 7) {
            Refund r = new Refund();
            r.setFromid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setToid(studioid);
            r.setType(0);
            r.setMoney(rate);
            r.setState(0);
            rr.save(r);
        } else if (action == 8) {
            projectService.passStep(project.getId(), stepid);
            return "success";
        } else if (action == 9) {
            if (measure == 0) {
                return projectService.punishStepMoney(id, stepid, rate);
            }
            if (measure == 1) {
                return projectService.restart(id, stepid);
            }
            if (measure == 2) {
                return projectService.cacelProject(id);
            }
        } else if (action == 10) {
            projectRepository.updateCompanyRate(companyRate, company_id);
        } else if (action == 11) {
            Refund r = new Refund();
            r.setFromid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setToid(project.getStudioID());
            r.setType(1);
            r.setMoney(rate);
            r.setState(0);
            rr.save(r);
            projectRepository.update_state(8, id);
            return "success";
        }
        return "false";
    }

    @PostMapping(value = "studio_action", produces = "application/json;charset=UTF-8")
    public String studio_action(@RequestParam("id") Integer id, @RequestParam("action") Integer action,
                                @RequestParam(value = "reason", required = false) String reason,
                                @RequestParam(value = "studioRate", required = false) Double studioRate,
                                @RequestParam(value = "stepid", required = false) Integer step_id,
                                @RequestParam(value = "measure", required = false) Integer measure,
                                @RequestParam(value = "file", required = false) MultipartFile file,
                                @RequestParam(value = "money", required = false) Float money,
                                @RequestParam(value = "headings", required = false) String headings,
                                @RequestParam(value = "contents", required = false) String contents,
                                @RequestParam(value = "table", required = false) String table,
                                @RequestParam(value = "quote", required = false) Integer quote,
                                HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Optional<Project> op = projectService.getProject(id);
        if (!op.isPresent()) {
            return "fail";
        }
        Project project = op.get();
        Integer studioId = (Integer) session.getAttribute("id");
        Integer company_id = project.getCompanyID();
        if (action == 0) {
            if (!projectService.canBid(project.getId())) {
                return "fail";
            }
            bidService.bid(project, studioId, quote);
            userService.notify(company_id, "系统通知", "有一个新的工作室投标了您的项目", ActionType.JUMP_PROJECT, "{\"solrId\":\"{0}\"}".replace("{0}", project.getSolr_id()));
            return "success";
        } else if (action == 1) {
            JSONArray array = JSON.parseArray(table);
            AtomicInteger count = new AtomicInteger();
            List<ChildForm> formList = new ArrayList<>();
            array.forEach(it -> {
                JSONObject json = (JSONObject) it;
                ChildForm childForm = new ChildForm();
                childForm.setName( json.getString("name"));
                childForm.setTime( json.getInteger("time"));
                childForm.setPart(count.get());
                childForm.setInfo(json.getString("info"));
                childForm.setPrice((float) 0);
                childForm.setState(0);
                childForm.setProjectId(id);
                count.getAndIncrement();
                formList.add(childForm);
            });
            projectService.setForm(project.getId(), formList);
            userService.notify(company_id, "系统通知", "有一个工作室完成了进度表", ActionType.JUMP_PROJECT, "{\"solrId\":\"{0}\"}".replace("{0}", project.getSolr_id()));
            return "success";
        } else if (action == 2) {
            userService.notify(company_id, "催促通知", "有一个工作室正在催促您尽快完成价格设定", ActionType.JUMP_PROJECT, "{\"solrId\":\"{0}\"}".replace("{0}", project.getSolr_id()));
            return "success";
        } else if (action == 3) {
            projectRepository.update_state(7, id);
            return "success";
        } else if (action == 4) {
            projectRepository.updateIsconfirm(1, id);
            projectRepository.update_state(3, id);
            Date date = new Date();
            Format f = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat ff = new SimpleDateFormat("yyyy-MM-dd");
            try {
                projectRepository.updateStartTime(ff.parse(f.format(date.getTime())), id);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            child.updateState(1, id, 0);
            userService.notify(company_id, "系统通知", "一个工作室完成了进度表的确认工作", ActionType.JUMP_PROJECT, "{\"solrId\":\"{0}\"}".replace("{0}", project.getSolr_id()));
            return "success";
        } else if (action == 5) {
            return payService.payDepositToCompany(project.getId(), project.getPrice() * 0.1);
        } else if (action == 6) {
            projectService.finish(file, id, step_id);
            child.updateState(2, id, project.getCurrent());
            return "success";
        } else if (action == 7) {
            child.updateState(3, id, project.getCurrent());
            return "success";
        } else if (action == 8) {
            Refund r = new Refund();
            r.setToid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setFromid(studioId);
            r.setType(0);
            r.setMoney(money);
            r.setState(0);
            rr.save(r);
            child.updateState(9, studioId, step_id);
            return "success";
        } else if (action == 9) {
            if (measure == 0) {
                return projectService.punishDeposit(project.getId(), step_id, money);
            }
            if (measure == 1) {
                child.updateState(5, project.getId(), step_id);
            }
            if (measure == 2) {
                projectRepository.update_state(7, id);
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
                projectRepository.updateCountdown(date, id);
            }
            return "success";
        } else if (action == 10) {
            Notification.send(userService.getToken(studioId), headings, contents);
            return "success";
        } else if (action == 11) {
            projectRepository.updateStudioRate(studioRate, company_id);
            return "success";
        } else if (action == 12) {
            Refund r = new Refund();
            r.setToid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setFromid(studioId);
            r.setType(1);
            r.setMoney(money);
            r.setState(0);
            rr.save(r);
            projectRepository.update_state(8, id);
            return "success";
        }
        return "fail";
    }
}
