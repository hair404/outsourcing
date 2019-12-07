package com.service;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.dao.AdvertisementRepository;
import com.dao.ProjectRepository;
import com.dao.TagDao;
import com.dao.UserRepository;
import com.model.Advertisement;
import com.model.User;
import com.type.AdState;
import com.type.AdType;
import com.utils.alipay.AlipayTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdService {

    @Resource
    AdvertisementRepository advertisementRepository;
    @Resource
    UserRepository userRepository;
    @Autowired
    ProjectRepository pr;
    @Autowired
    TagDao tagDao;

    @Resource
    UserService userService;

    public String recommend_ad_project(int first) {
//        Integer page = first / 20;
//        Sort sort = new Sort(Direction.DESC, "weight");
//        final Pageable pageable = PageRequest.of(page, 20, sort);
//        return JsonUtils.objectToJson(apr.findAll(pageable).getContent());
        return "";
    }

    public List<Integer> recommendAdStudio(int first, int end) {
        Map<Integer, Integer> moneyMap = new HashMap<>();
        advertisementRepository.findAllByType(AdType.STUDIO.getId()).forEach(it -> {
            if (userService.isStudent(it.getTypeId())) {
                moneyMap.put(it.getTypeId(), moneyMap.getOrDefault(it.getTypeId(), 0) + it.getPrice() * 2);
            } else {
                moneyMap.put(it.getTypeId(), moneyMap.getOrDefault(it.getTypeId(), 0) + it.getPrice());
            }
        });
        List<Integer> studioIds = new ArrayList<>(moneyMap.keySet());
        studioIds.sort((o1, o2) -> moneyMap.get(o2).compareTo(moneyMap.get(o1)));
        if (end > studioIds.size()) {
            end = studioIds.size();
        }
        return studioIds.subList(first, end);
    }

    public void insert_ad_project(float ad_price, Integer prj_id) {
//        Project prj = pr.get_info(prj_id);
//        AdProject ap = new AdProject();
//        ap.setPrjname(prj.getPrjname());
//        ap.setDueTime(prj.getDeadline());
//        ap.setAd_price(ad_price);
//        ap.setTag(prj.getTag());
//        ap.setSubtag(prj.getSubtag());
//        ap.setPrice(prj.getPrice());
//        ap.setImg(prj.getImg());
//        ap.setWeight(weight(ad_price));
//        ap.setPrjId(prj_id);
//        ap.setSolrid(prj.getSolr_id());
//        ap.setState(0);
//        apr.save(ap);
    }

    public Boolean isProject(String solr_id) {
//        if (apr.findBySolrId(solr_id) != null)
//            return true;
//        else
//            return false;
        return true;
    }

    public JSONArray ad() {

        JSONArray array = new JSONArray();
//        List<AdProject> p = apr.findAll();
//        List<AdStudio> s = adStudioRepository.findAll();
//        for (int i = 0; i < p.size(); i++) {
//            JSONObject ad = new JSONObject();
//            AdProject project = p.get(i);
//            ad.put("id", project.getId());
//            ad.put("name", project.getPrjname());
//            ad.put("belong", pr.findCompanyNameById(project.getPrjId()));
//            ad.put("type", 0);
//            ad.put("money", project.getAd_price());
//            ad.put("solr_id", project.getSolrid());
//            ad.put("state", project.getState());
//            array.add(ad);
//        }
//        for (int i = 0; i < s.size(); i++) {
//            JSONObject ad = new JSONObject();
//            AdStudio studio = s.get(i);
//            ad.put("id", studio.getId());
//            ad.put("name", studio.getUsername());
//            ad.put("belong", userRepository.findUsernameById(studio.getAccount_id()));
//            ad.put("type", 1);
//            ad.put("money", studio.getAd_price());
//            ad.put("solr_id", studio.getSolrid());
//            ad.put("state", studio.getState());
//            array.add(ad);
//        }
        return array;
    }

    public List<Advertisement> getAll() {
        List<Advertisement> advertisementList = new ArrayList<>();
        advertisementRepository.findAll().forEach(advertisementList::add);
        return advertisementList;
    }

    public List<Advertisement> getByType(AdType adType) {
        return new ArrayList<>(advertisementRepository.findAllByType(adType.getId()));
    }

    public String judge(int advertisementId, AdState adState) throws AlipayApiException {
        Optional<Advertisement> optionalAdvertisement = advertisementRepository.findById(advertisementId);
        if (!optionalAdvertisement.isPresent()) {
            return "NotFound";
        }
        Advertisement advertisement = optionalAdvertisement.get();

        Optional<User> optionalUser = userRepository.findById(advertisement.getTypeId());
        if (!optionalUser.isPresent()) {
            return "NotFound";
        }
        User user = optionalUser.get();


        //判断是否被审核过
        if (advertisement.getState() != AdState.PADDING) {
            return "hasJudge";
        }

        //设定广告审核状态
        advertisement.setState(adState);

        //若未通过进行退款
        if (adState == AdState.REJECT) {
            //判断用户是否填写支付宝账号
            if (user.getAlipay().isEmpty()) {
                return "NullAlipayAccount";
            }
            if (!AlipayTools.transfer(user.getAlipay(), advertisement.getPrice())) {
                return "RefundError";
            }
        }

        advertisementRepository.save(advertisement);


        return "success";
    }

}
