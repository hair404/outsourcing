package com.service;

import com.dao.ChildFormRepository;
import com.dao.PaymentRepository;
import com.dao.ProjectRepository;
import com.model.ChildForm;
import com.model.Payment;
import com.model.Project;
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

    @Value("${application.url}")
    private String url;

    @Resource
    private PaymentRepository paymentRepository;

    @Resource
    private ChildFormRepository childFormRepository;

    @Resource
    private ProjectRepository projectRepository;

    /**
     * 支付定金
     *
     * @param projectId 项目ID
     * @param amount    金额数量
     * @return 支付宝付款界面
     */
    public String payDepositToStudio(int projectId, double amount) {
        return pay(projectId, amount, PayType.DEPOSIT_TO_STUDIO, "项目押金");
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
                        //TODO 小进度付款成功
                    }
                    break;
                case PAY_IN_ADVANCED:
                    Optional<Project> opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setIspia(1);
                        projectRepository.save(project);
                    }
                    break;
                case DEPOSIT_TO_STUDIO:
                    opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setIsdeposit(1);
                        projectRepository.save(project);
                    }
                    break;
                case DEPOSIT_TO_COMPANY:
                    opProject = projectRepository.findById(payment.getTypeId());
                    if (opProject.isPresent()) {
                        Project project = opProject.get();
                        project.setHasPaid(1);
                        projectRepository.save(project);
                    }
                    break;
            }
        }
    }

}


