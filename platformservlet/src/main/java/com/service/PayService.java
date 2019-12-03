package com.service;

import com.dao.ChildFormRepository;
import com.dao.PaymentRepository;
import com.dao.ProjectRepository;
import com.model.ChildForm;
import com.model.Payment;
import com.model.Project;
import com.type.ActionType;
import com.type.PayState;
import com.type.PayType;
import com.utils.alipay.AlipayTools;
import com.utils.alipay.OrderInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
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
    private NotificationService notificationService;

    @Resource
    private ProjectService projectService;

    /**
     * 支付定金
     *
     * @param projectId 项目ID
     * @param amount    金额数量
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
        OrderInfo orderInfo = new OrderInfo();

        payment.setOrderId(UUID.randomUUID().toString().replace("-", ""));
        payment.setState(PayState.WAIT);
        payment.setType(type);
        payment.setTypeId(typeId);
        payment.setAmount(amount);
        paymentRepository.save(payment);

        orderInfo.setOutTradeNo(payment.getOrderId());
        orderInfo.setSubject(description);
        orderInfo.setTotalAmount(amount);

        return AlipayTools.pay(orderInfo, url + "/alipay/callback", url + "/alipay/closeSelf");
    }

    public String payInAdvanced(int projectId, double amount) {
        return pay(projectId, amount, PayType.PAY_IN_ADVANCED, "项目预付款");
    }

    public String payPart(int partId, double amount) {
        return pay(partId, amount, PayType.STEP, "步骤款");
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
                        if (childForm.getPayPrice() - childForm.getPrice() < 0.01) {
                            childForm.setState(9);
                        } else {
                            childForm.setState(4);
                        }
                        childFormRepository.save(childForm);
                        projectService.nextStep(childForm.getProjectId());
                    }
                    break;
                case PAY_IN_ADVANCED:
                    Optional<Project> opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setIspia(1);
                        projectRepository.save(project);
                        checkPayState(project);
                        notificationService.notify(project.getStudioID(), "系统通知", "一个工作室完成了进度表的确认工作", ActionType.JUMP_PROJECT, "{solrId:{0}}".replace("{0}", project.getSolr_id()));
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
                    }
                    break;
                case DEPOSIT_TO_COMPANY:
                    opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setHasPaid(1);
                        projectRepository.save(project);
                        checkPayState(project);
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


