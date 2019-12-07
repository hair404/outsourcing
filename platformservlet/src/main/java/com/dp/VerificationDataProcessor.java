package com.dp;

import com.dp.dto.ServerAdvertisement;
import com.dp.dto.ServerVerification;
import com.model.Advertisement;
import com.model.User;
import com.model.Verification;
import com.service.UserService;
import com.type.VerificationType;
import com.utils.OSSTools;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class VerificationDataProcessor {

    @Resource
    private UserService userService;

    public List<ServerVerification> getVerifications(int typeId, int status, String text) {
        List<Verification> verificationList = userService.getAllVerification().stream()
                .filter(it -> it.getState().getId() == status || status == 0)
                .filter(it -> it.getType().getId() == typeId || typeId == 0)
                .collect(Collectors.toList());

        //转化为DTO
        return verificationList.stream()
                .map(this::toDTO)
                .filter(Objects::nonNull)
                .filter(it -> it.getUsername().contains(text))
                .collect(Collectors.toList());
    }

    private ServerVerification toDTO(Verification verification) {
        Optional<User> optionalUser = userService.getUser(verification.getUserId());
        if (!optionalUser.isPresent()) {
            return null;
        }
        User user = optionalUser.get();
        return new ServerVerification(
                verification.getId(),
                verification.getType().getId(),
                verification.getState().getId(),
                user.getUsername(),
                OSSTools.url + verification.getImage(),
                user.getId());
    }
}
