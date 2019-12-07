package com.service;

import com.dao.AdvertisementRepository;
import com.dao.ChildFormRepository;
import com.dao.PaymentRepository;
import com.dao.ProjectRepository;
import com.model.*;
import com.type.*;
import com.utils.alipay.AlipayTools;
import com.utils.alipay.PayInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayService {

    @Value("${application.alipay.callback.url}")
    private String url;

    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private ChildFormRepository childFormRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private AdvertisementRepository advertisementRepository;

    @Resource
    private NotificationService notificationService;

    @Resource
    private ProjectService projectService;

    @Resource
    private UserService userService;

    /**
     * 支付定金
     *
     * @param projectId 项目ID
     * @return 支付宝付款界面
     */
    public String payDepositToStudio(int projectId) {
        Optional<Project> op = projectService.getProject(projectId);
        if (!op.isPresent()) {
            return "NotFound";
        }
        return pay(projectId, op.get().getPrice() * 0.1, PayType.DEPOSIT_TO_STUDIO, "项目押金");
    }

    public String payDepositToCompany(int projectId, double amount) {
        return pay(projectId, amount, PayType.DEPOSIT_TO_COMPANY, "项目押金");
    }

    private String pay(int typeId, double amount, PayType type, String description) {
        Payment payment = new Payment();
        PayInfo payInfo = new PayInfo();

        payment.setOrderId(UUID.randomUUID().toString().replace("-", ""));
        payment.setState(PayState.WAIT);
        payment.setType(type);
        payment.setTypeId(typeId);
        payment.setAmount(amount);
        paymentRepository.save(payment);

        payInfo.setOutTradeNo(payment.getOrderId());
        payInfo.setSubject(description);
        payInfo.setTotalAmount(amount);

        return AlipayTools.pay(payInfo, url + "/alipay/callback", url + "/alipay/closeSelf");
    }

    public String payInAdvanced(int projectId, double amount) {
        return pay(projectId, amount, PayType.PAY_IN_ADVANCED, "项目预付款");
    }

    public String payPart(int partId, double amount) {
        return pay(partId, amount, PayType.STEP, "步骤款");
    }

    public String payStudioAd(int studioId, double amount) {
        return pay(studioId, amount, PayType.AD_STUDIO, "推广费");
    }

    /**
     * 更新支付状态
     *
     * @param orderId 订单号
     * @param state   状态
     */
    public void updatePayState(String orderId, PayState state) {
        Optional<Payment> op = paymentRepository.findByOrOrderId(orderId);
        if (op.isPresent()) {
            Payment payment = op.get();
            payment.setState(state);
            paymentRepository.save(payment);

            switch (payment.getType()) {
                case STEP:
                    Optional<ChildForm> opChildForm = childFormRepository.findById(payment.getTypeId());
                    if (opChildForm.isPresent()) {
                        ChildForm childForm = opChildForm.get();
                        if (Math.abs(childForm.getPayPrice() - childForm.getPrice()) < 0.01) {
                            childForm.setState(9);
                        } else {
                            childForm.setState(4);
                        }
                        childFormRepository.save(childForm);
                        projectService.nextStep(childForm.getProjectId());

                        Optional<Project> optionalProject = projectService.getProject(childForm.getProjectId());
                        optionalProject.ifPresent(project -> notificationService.notify(project.getStudioID(), "公司支付进度款", "有一个公司支付了你项目的进度款，点击查看详情", ActionType.JUMP_PROJECT, ActionType.generateJumpProjectParams(project.getSolr_id())));
                    }
                    break;
                case PAY_IN_ADVANCED:
                    Optional<Project> opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setIspia(1);
                        projectRepository.save(project);
                        checkPayState(project);
                        notificationService.notify(project.getStudioID(), "系统通知", "一个工作室完成了进度表的确认工作", ActionType.JUMP_PROJECT, ActionType.generateJumpProjectParams(project.getSolr_id()));
                    }
                    break;
                case DEPOSIT_TO_STUDIO:
                    opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setIsdeposit(1);
                        project.setRestDeposit((float) payment.getAmount());
                        projectRepository.save(project);
                        checkPayState(project);
                        notificationService.notify(project.getStudioID(), "公司已经支付定价", "一个公司支付了定价，点击查看详情", ActionType.JUMP_PROJECT, ActionType.generateJumpProjectParams(project.getSolr_id()));
                    }
                    break;
                case DEPOSIT_TO_COMPANY:
                    opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setHasPaid(1);
                        projectRepository.save(project);
                        checkPayState(project);
                        notificationService.notify(project.getStudioID(), "工作室已经支付押金", "一个工作室支付了押金，点击查看详情", ActionType.JUMP_PROJECT, ActionType.generateJumpProjectParams(project.getSolr_id()));
                    }
                    break;
                case AD_STUDIO:
                    Optional<User> studio = userService.getUser(payment.getTypeId());
                    if (studio.isPresent()) {
                        Advertisement advertisement = new Advertisement();
                        advertisement.setTypeId(payment.getTypeId());
                        advertisement.setPrice((int) payment.getAmount());
                        advertisement.setState(AdState.PADDING);
                        advertisement.setType(AdType.STUDIO);
                        advertisementRepository.save(advertisement);
                        notificationService.notify(advertisement.getTypeId(), "推广开始审核", "我们将以最快的速度通知您", ActionType.CENTER,"");
                    }
                    break;
            }
        }
    }

    private void checkPayState(Project project) {
        if (project.getIspia() == 1 && project.getHasPaid() == 1 && project.getIsdeposit() == 1) {
            project.setState(4);
            projectRepository.save(project);
        }
    }

}


