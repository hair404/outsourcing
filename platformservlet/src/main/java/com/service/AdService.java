package com.service;


import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.AdDao;
@Service
public class AdService {

	@Autowired
	AdDao adDao;

	public JSONArray recommend_ad(int first, int end) {
		JSONArray array = new JSONArray();
		array.put(0);
		JSONArray ad = adDao.queryAd();
		for (int i = first; i <= end; i++) {
			array.put(ad.get(i));
		}
		return array;
	}
}
