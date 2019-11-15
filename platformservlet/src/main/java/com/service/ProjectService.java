package com.service;

import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.dao.AdProjectRepository;
import com.dao.BidRepository;
import com.dao.File_project_repository;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.CancelReasonRepository;
import com.dao.Child_formRepository;
import com.dao.UserRepository;
import com.model.Bid;
import com.model.Child_form;
import com.model.File_project;
import com.model.Project;
import com.model.UserInfo;
import com.utils.JsonUtils;

@Service
public class ProjectService {
	@Autowired
	File_project_repository fpj;
	@Autowired
	BidRepository bidRepository;
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CancelReasonRepository reasonRepository;
	@Autowired
	Child_formRepository child_formRepository;
	@Autowired
	TagDao tagDao;
	@Autowired
	File_project_repository fpr;

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
			for (int i = 0; i < rs.size(); i++) {
				Bid bid = rs.get(i);
				UserInfo userInfo = userRepository.getInfoById(bid.getStudio_id());
				JSONObject user_info = new JSONObject(userInfo);
				user_info.put("tag", tagDao.QueryTag(userInfo.getId()));
				enroll.add(user_info);
				project_info.put("enroll", enroll);
			}
		}

		else if (project.getState() == 2) {
			if (user_id.equals(project.getCompanyID())) {
				if (project.getIsform() == 1 && project.getIssetprice() == 0) {
					List<Child_form> child = child_formRepository.getChildForm(project_id);
					JSONArray j = JSONArray.parseArray(JsonUtils.objectToJson(child));
					project_info.put("table", j);
				}
			}
				if (user_id.equals(project.getStudioID())) {
					if (project.getIsform()==1 && project.getIssetprice()==1) {
						List<Child_form> child = child_formRepository.getChildForm(project_id);
						JSONArray j = JSONArray.parseArray(JsonUtils.objectToJson(child));
						project_info.put("table", j);
					}
				}
			
		} else if (project.getState() == 3) {
			Calendar calendar = new GregorianCalendar();
			Date date = project.getStartTime();
			Format f = new SimpleDateFormat("yyyy-MM-dd");
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			date = calendar.getTime();
			project_info.put("payDeadline", f.format(date));
		} else if (project.getState() == 4) {

			List<Child_form> child_form = child_formRepository.getChildForm(project_id);
			Child_form child = child_form.get(project.getCurrent());
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
		String filePath = "F://file//" + prj_id + "/" + step_id + "/";
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

}
