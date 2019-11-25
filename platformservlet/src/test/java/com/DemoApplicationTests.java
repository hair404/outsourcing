package com;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.aliyuncs.exceptions.ClientException;
import com.utils.MessageTools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.Jdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dao.ProjectDao;
import com.dao.TagDao;
import com.dao.UserDao;
import com.dao.UserRepository;
import com.model.Account;
import com.model.Child_form;
import com.model.Project;
import com.model.Tag;
import com.model.User;
import com.utils.JsonUtils;
import com.utils.RedisUtils;


public class DemoApplicationTests {

    public static void main(String[] args) throws ClientException, InterruptedException {
        for (int i = 0;i < 10;i++) {
            MessageTools.sendVerifyCode("13375810790", "123456");
            Thread.sleep(10000);
        }
    }
}

//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class DemoApplicationTests {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

//	 * public void contextLoads() {
//	 
//		String sql = "select * from user";
//		List<UserInfo> userList = (List<UserInfo>) jdbcTemplate.query(sql,new RowMapper<UserInfo>() {
//			@Override
//			/*
//			 * （非 Javadoc）
//			 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
//			 * rs the ResultSet to map (pre-initialized for the current row)rowNum the number of the current row
//			 */
//			public UserInfo mapRow(ResultSet rs,int rowNum) throws SQLException {
//				UserInfo user = new UserInfo();
//				user.setId(rs.getInt("id"));
//				user.setName(rs.getString("name"));
//				user.setPassword(rs.getString("password"));
//				user.setUsername(rs.getString("username"));
//				user.setAlipay(rs.getString("alipay"));
//				user.setTel(rs.getString("tel"));
//				user.setEmail(rs.getString("email"));
//				user.setInfo(rs.getString("Info"));
//				return user;
//			}
//		});
//		System.out.println("查询成功");
//		String mes = JSONArray.toJSONString(userList);
//		System.out.println(mes);
//		
//
//	public void getjiami() throws JSONException {  
//	        String soap = "1111";
//	        List<String> list = new ArrayList<>();
//	        JSONObject param1 = new JSONObject();
//	        Map<String, String> param0 = new HashMap<String, String>();
//	        list.add(soap);
//	        list.add(soap);
//	        param1.put("soap",soap);try {
//				param1.put("soap","2222");
//			} catch (JSONException e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			}
//	        param0.put("soap",soap);
//	        param0.put("soap","3333");
//	        System.out.println("这是list的输出：" + list);
//	        System.out.println("这是json的输出：" + param1);
//	        System.out.println("这是map的输出：" + param0);	    
//	}

//	@Autowired
//	JdbcTemplate jdbcTemplate;


//	public void queryUserId() {
//		String sql = String.format("select user_id from tag where tag = '%d'", 2);
//		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
//		JSONArray json = new JSONArray();
//		while (rs.next()) {
//			json.put(rs.getInt("user_id"));
//		}
//		Iterator it = json.iterator();
//		while (it.hasNext()) {
//		    System.out.println(it.next());
//		}
//		System.out.print(json);
//	}

//    public void center() {	
//		UserInfo user = userRepository.getInfoById(1);
//		JSONObject json = new JSONObject();
//		 JSONArray tag = tagDao.QueryTag(1);	
//		json.put("img", user.getImg());
//	json.put("name", user.getName());
//	json.put("tag", tag);
//		json.put("email", user.getEmail());
//		json.put("phone", user.getTel());
//		json.put("info", user.getInfo());
//		System.out.println(JsonUtils.objectToJson(user));
//		System.out.println(json);
//	
//	public void ccc() {
//		Account account = userRepository.getAccountById(1);
//		System.out.println(account.getTel());
//	@Autowired
//	UserRepository userRepository;
//	@Autowired
//	TagDao tagDao;
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	@Autowired
//	ProjectDao projectDao;
//	@Autowired
//	RedisUtils redis;
//	@Autowired
//    StringRedisTemplate t;
////	
//	@Test
////	public void getCompletedProject() {
//		String sql ="select * from project where state = 1 and (companyId= ? or studioId=?)";
//		List<Project> projectList = (List<Project>) jdbcTemplate.query(sql, new RowMapper<Project>() {
//			@Override
//			public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Project project = new Project();
//				project.setUuid(rs.getInt("uuid"));
//				project.setTag(rs.getInt("tag"));
//				project.setPrjname(rs.getString("prjname"));
//				project.setSub_tag(rs.getInt("sub_tag"));
//				project.setImg(rs.getString("img"));
//				project.setPrice(rs.getFloat("price"));
//				return project;
//			}
//		},1,1);
//		JSONArray array = new JSONArray();
//		array.put(0);
//		array.put(JsonUtils.objectToJson(projectList));
//		System.out.println(array);
//	}
//}
//	public void display() {
//		//HttpSession session = request.getSession();
//		//Integer id = (Integer) session.getAttribute("id");
//		Integer id=1;
//		JSONObject json = new JSONObject();
//		if (id != null) {
//			UserInfo user = userRepository.getInfoById(id);
//			JSONArray array = new JSONArray();
//			array.put(0);
//			array.put(projectDao.getCompletedProject(id));
//			json.put("id", user.getUuid());
//			json.put("type", user.getType());
//			json.put("img", user.getImg());
//			json.put("username", user.getUsername());
//			json.put("email", user.getEmail());
//			json.put("phone", user.getTel());
//			json.put("info", user.getInfo());
//			json.put("complete", array);
//		}
//		System.out.println(json);
//	}
//	public void myPrj() {
//		  // HttpSession session =request.getSession();
//		   //Integer id = (Integer) session.getAttribute("id");
//		   Integer id =1;
//		   Integer state = 1;
//		   JSONArray array = new JSONArray();
//		   if(id!=null) {
//			   array.put(0);
//			   array.put(projectDao.getProjectById(state, id));
//		   }
//		   System.out.println(array);
//		  
//	   }
//	public void uuid() {
////		JSONObject j  = new JSONObject();
////		j.put("f", "f");
////		System.out.println(j.toString());
////		j.put("f", "d");
////		System.out.println(j.toString());
////
////		JSONArray a = new JSONArray();
////		Form f = new Form();
////    	f.setPrice(122);
////    	 t.opsForHash().put("1", "1", f);
////		UUID i = UUID.randomUUID();
////		System.out.println(i);
//////		
////	   	 String b = "[{\"name\":\"dawd\"},{\"name\":\"dwaghw\"}]";
////			JSONArray j = new JSONArray(b);
////			System.out.println(j.getJSONObject(1).get("name"));
//	}
//}

//	}



