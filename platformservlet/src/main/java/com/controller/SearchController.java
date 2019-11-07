package com.controller;

import java.io.IOException;
import java.util.Iterator;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dao.TagDao;
import com.service.ProjectService;


@RestController
public class SearchController {
	@Autowired
	ProjectService projectService;
	@Autowired
	SolrClient client;
	@Autowired
	TagDao tagDao;

	@PostMapping("search")
	public String recommend(
			// type=0/1&ctg=1&subctg=[0,1,2]&keyword=空或者XXX&number=X&first=
			@RequestParam("type") Integer type, @RequestParam("ctg") Integer ctg,
			@RequestParam("subctg") Integer subctg, @RequestParam("keyword") String keyword,
			@RequestParam("number") Integer number, @RequestParam("first") Integer first)
			throws IOException, SolrServerException {
		final String solrUrl = "http://localhost:8983/solr/new_core";
		HttpSolrClient solrServer = new HttpSolrClient.Builder(solrUrl).withConnectionTimeout(10000)
				.withSocketTimeout(60000).build();

		SolrQuery query = new SolrQuery();
		if ("".equals(keyword))
			query.set("q", "text:*");
		else
			query.set("q", "text:" + keyword);		
		query.setStart(first);
		query.setRows(number);
		query.set("df", "text");
		if (type == 1) {
			if (ctg == 0 & subctg == 0)
				query.setFilterQueries("entity:project");
			else if (ctg == 0 & subctg != 0) {
				query.addFilterQuery("entity:project");
				query.addFilterQuery("subtag:" + subctg);
			} else {
				query.addFilterQuery("entity:project");
				query.addFilterQuery("tag:" + ctg);
				query.addFilterQuery("subtag:" + subctg);
			}
		} else {
			query.setFilterQueries("entity:studio");
			if (ctg == 0)
				query.setFilterQueries("entity:studio");
			else {
				JSONArray json = tagDao.queryUserId(ctg);
				Iterator<Object> it = json.iterator();
				while (it.hasNext()) {
					int l = (int) it.next();
					query.addFilterQuery("user_id:"+l);  
					System.out.println(l);
				}
				query.addFilterQuery("entity:studio");
			}
		}
		JSONArray json = new JSONArray();

		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();

		long cnt = results.getNumFound();
		System.out.println("查询结果总数:" + cnt);
		json.put(cnt);
		for (SolrDocument solrDocument : results) {
			json.put(solrDocument);
		}
		System.out.print(json);
		return json.toString();
	}
}
