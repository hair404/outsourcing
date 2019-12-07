package com.dp;

import com.dp.dto.ServerAdvertisement;
import com.model.Advertisement;
import com.model.Project;
import com.model.User;
import com.service.AdService;
import com.service.ProjectService;
import com.service.UserService;
import com.type.AdType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AdDataProcessor {

    @Resource
    private AdService adService;

    @Resource
    private UserService userService;

    @Resource
    private ProjectService projectService;

    public List<User> getAdStudio(int start, int end) {
        List<Integer> ids = adService.recommendAdStudio(start, end);
        List<User> userList = new ArrayList<>();
        ids.forEach(it -> {
            Optional<User> op = userService.getUser(it);
            op.ifPresent(userList::add);
        });
        return userList;
    }

    public List<ServerAdvertisement> getAdvertisement(int typeId, String keyword) {
        if (typeId == 0) {
            List<Advertisement> advertisementList = adService.getAll();
            List<ServerAdvertisement> serverAdvertisementList = new ArrayList<>();
            advertisementList.stream()
                    .map(this::toDTO)
                    .filter(Objects::nonNull)
                    .filter(it -> keyword.isEmpty() || it.getBelong().contains(keyword))
                    .forEach(serverAdvertisementList::add);
            return serverAdvertisementList;
        } else {
            List<Advertisement> advertisementList;
            if (typeId == 1) {
                advertisementList = adService.getByType(AdType.PROJECT);
            } else if (typeId == 2) {
                advertisementList = adService.getByType(AdType.STUDIO);
            } else {
                return new ArrayList<>();
            }
            return advertisementList.stream()
                    .map(this::toDTO)
                    .filter(Objects::nonNull)
                    .filter(it -> keyword.isEmpty() || it.getBelong().contains(keyword))
                    .collect(Collectors.toList());
        }
    }

    public ServerAdvertisement toDTO(Advertisement advertisement) {
        if (advertisement.getType() == AdType.STUDIO) {
            Optional<User> op = userService.getUser(advertisement.getTypeId());
            if (!op.isPresent()) {
                return null;
            }
            User studio = op.get();
            return new ServerAdvertisement(advertisement.getId(),
                    studio.getUsername(),
                    advertisement.getType().getId(),
                    advertisement.getPrice(),
                    studio.getId() + "",
                    advertisement.getState().getId());
        } else if (advertisement.getType() == AdType.PROJECT) {
            Optional<Project> op = projectService.getProject(advertisement.getTypeId());
            if (!op.isPresent()) {
                return null;
            }
            Project project = op.get();
            return new ServerAdvertisement(advertisement.getId(),
                    project.getPrjname(),
                    advertisement.getType().getId(),
                    advertisement.getPrice(),
                    project.getSolr_id(),
                    advertisement.getState().getId());
        }
        return null;
    }
}
