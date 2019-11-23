package com.controller;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dao.ProjectRepository;
import com.dao.RefundRepository;
import com.dao.BidRepository;
import com.dao.CancelReasonRepository;
import com.dao.Child_formRepository;
import com.dao.ComplainReasonRepository;
import com.model.Bid;
import com.model.Cancel_reason;
import com.model.Complain_reason;
import com.model.Project;
import com.model.Refund;
import com.service.AliPayService;
import com.service.ProjectService;
import com.utils.Notification;
import com.model.Child_form;

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
	Child_formRepository child;
	@Autowired
	BidRepository bidRepository;
	@Autowired
	ProjectService ser;
	@Autowired
	RefundRepository rr;

	@PostMapping("company_action")
	public String company_action(@RequestParam("id") Integer id, @RequestParam("action") Integer action,
			@RequestParam(value = "studioid", required = false) Integer studioid,
			@RequestParam(value = "stepid", required = false) Integer stepid,
			@RequestParam(value = "reason", required = false) String reason,
			@RequestParam(value = "table", required = false) String table,
			@RequestParam(value = "measure", required = false) Integer measure,
			@RequestParam(value = "money", required = false) Float money,
			@RequestParam(value = "companyRate", required = false) Float companyRate,
			@RequestParam(value = "isfirst", required = false) Integer isfirst, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer company_id = (Integer) session.getAttribute("id");
         Project project = projectRepository.get_info(id);
		if (action == 0) {
			projectRepository.update_studioid(studioid, id);
			bidRepository.updateAllState(0, id);
			bidRepository.updateState(2, id, studioid);
			projectRepository.update_state(2, id);
			return "success";
		} else if (action == 1) {
			List<Child_form> l = com.alibaba.fastjson.JSONArray.parseArray(table, Child_form.class);
			for (int i = 0; i < l.size(); i++) {
				JSONObject json_form = new JSONObject(l.get(i));
				child.updatePrice((Float) json_form.get("price"), id, i);
			}
			projectRepository.updateIssetprice(1, id);
			return "success";
		} else if (action == 2) {
			Notification.send();
			return "success";
		} else if (action == 3) {
			projectRepository.update_state(7, id);
			return "success";
		} else if (action == 4) {
			projectRepository.updateIsdeposit(1, id);
			return AliPayService.Pay("", "",
					"{" + "\"out_trade_no\":\"20150320010101001\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
							+ "\"total_amount\":88.88," + "\"subject\":\"part_remuneration\","
							+ "\"body\":\"Iphone6 16G\","
							+ "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
							+ "\"extend_params\":{" + "\"sys_service_provider_id\":\"2088511833207846\"" + "}" + "}");

		} else if (action == 5) {

			projectRepository.updateIspia(1, id);
			return AliPayService.Pay("", "",
					"{" + "\"out_trade_no\":\"20150320010101001\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
							+ "\"total_amount\":88.88," + "\"subject\":\"首付款\"," + "\"body\":\"Iphone6 16G\","
							+ "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
							+ "\"extend_params\":{" + "\"sys_service_provider_id\":\"2088511833207846\"" + "}" + "}");
		} else if (action == 6) {
			projectRepository.updateIspia(1, id);
			return AliPayService.Pay("", "",
					"{" + "\"out_trade_no\":\"20150320010101001\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
							+ "\"total_amount\":88.88," + "\"subject\":\"进度款\"," + "\"body\":\"Iphone6 16G\","
							+ "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
							+ "\"extend_params\":{" + "\"sys_service_provider_id\":\"2088511833207846\"" + "}" + "}");
		} else if (action == 7) {
              Refund r = new Refund();
              r.setFromid(company_id);
              r.setPrjid(id);
              r.setReason(reason);
              r.setToid(studioid);
              r.setType(0);
              r.setMoney(money);
              r.setState(0);
              rr.save(r);
		} else if (action == 8) {
			child.updateState(9, id, stepid);
			if ((project.getTotalPart()-1)!=stepid) { 
				child.updateState(1, id, stepid+1);
				projectRepository.updateCurrent(stepid+1, id);
			}
			return "success";
		} else if (action == 9) {
			if (measure==0) {
				child.updateState(4, project.getId(), stepid);
			}
			if(measure==1) {
				child.updateState(1, project.getId(), stepid);
			}
			if(measure==2) {
				projectRepository.update_state(7, id);
			}
			return "success";
		} else if (action == 10) {
			projectRepository.updateCompanyRate(companyRate, company_id);
		} else if (action == 11) {
			Refund r = new Refund();
            r.setFromid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setToid(project.getStudioID());
            r.setType(1);
            r.setMoney(money);
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
			@RequestParam(value = "table", required = false) String table, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Project project = projectRepository.get_info(id);
		Integer studio_id = (Integer) session.getAttribute("id");
		Integer company_id = project.getCompanyID();
		if (action == 0) {
			// todo:判断是否可以投标
			Bid bid = new Bid();
			bid.setCompany_id(company_id);
			bid.setProject_id(id);
			bid.setState(0);
			bid.setStudio_id(studio_id);
			bid.setCompany_id(company_id);
		} else if (action == 1) {
			List<Child_form> l = com.alibaba.fastjson.JSONArray.parseArray(table, Child_form.class);
			for (int i = 0; i < l.size(); i++) {
				JSONObject json_form = new JSONObject(l.get(i));
				Child_form c = new Child_form();
				c.setName((String) json_form.get("name"));
				c.setTime((Integer) json_form.get("time"));
				c.setPart(i);
				c.setInfo((String) json_form.get("info"));
				c.setPrice((float) 0);
				c.setState(0);
				c.setProject_id(id);
				child.save(c);
				projectRepository.updateIsform(1, id);
			}
			return "success";
		} else if (action == 2) {
			Notification.send();
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
			child.updateState(1, id, 1);
			return "success";
		} else if (action == 5) {
			projectRepository.updateHasPaid(1, id);
			return AliPayService.Pay("", "",
					"{" + "\"out_trade_no\":\"20150320010101001\"," + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\","
							+ "\"total_amount\":88.88," + "\"subject\":\"押金\"," + "\"body\":\"Iphone6 16G\","
							+ "\"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\","
							+ "\"extend_params\":{" + "\"sys_service_provider_id\":\"2088511833207846\"" + "}" + "}");

			
		} else if (action == 6) {
			//ser.upload(file, id, step_id);
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
            r.setFromid(studio_id);
            r.setType(0);
            r.setMoney(money);
            r.setState(0);
            rr.save(r);
			child.updateState(9, studio_id, step_id);
			return "success";
		} else if (action == 9) {
			if (measure == 0) {
				child.updateState(7, project.getId(), step_id);
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
			Notification.send();
			return "success";
		} else if (action == 11) {
			projectRepository.updateStudioRate(studioRate, company_id);
			return "success";
		}
		else if(action == 12) {
		    Refund r = new Refund();
            r.setToid(company_id);
            r.setPrjid(id);
            r.setReason(reason);
            r.setFromid(studio_id);
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
