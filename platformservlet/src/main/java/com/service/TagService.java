package com.service;

import java.util.Iterator;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.TagDao;

@Service
public class TagService {
	@Autowired
	TagDao tagDao;

	public void tagService(Integer id, String tag) {
		tagDao.DeleteTag(id);
		JSONArray json_tag = new JSONArray(tag);
		Iterator<Object> it = json_tag.iterator();
		while (it.hasNext()) {
			int l = (int) it.next();
			tagDao.InsertTag(id, l);
		}

	}
}
