package com.service;

import com.dao.PaymentRepository;
import com.model.Payment;
import com.type.PayState;
import com.type.PayType;
import com.utils.alipay.AlipayTools;
import com.utils.alipay.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.UUID;

@Service
public class PayService {

    @Value("${application.url}")
    private String url;

    @Resource
    private PaymentRepository paymentRepository;

    /**
     * 支付定金
     *
     * @param projectId 项目ID
     * @param amount    金额数量
     * @return 支付宝付款界面
     */
    public String payDeposit(int projectId, double amount) {
        Payment payment = new Payment();
        OrderInfo orderInfo = new OrderInfo();

        payment.setOrderId(UUID.randomUUID().toString().replace("-", ""));
        payment.setState(PayState.WAIT);
        payment.setType(PayType.DEPOSIT);
        payment.setTypeId(projectId);
        payment.setAmount(amount);
        paymentRepository.save(payment);

        orderInfo.setOutTradeNo(payment.getOrderId());
        orderInfo.setSubject("项目定金");
        orderInfo.setTotalAmount(amount);

        return AlipayTools.pay(orderInfo, url + "/alipay/callback");
    }

    /**
     * 更新支付状态
     * @param orderId 订单号
     * @param state 状态
     */
    public void updatePayState(String orderId, PayState state) {
        Optional<Payment> op = paymentRepository.findByOrOrderId(orderId);
        if (op.isPresent()) {
            Payment payment = op.get();
            payment.setState(state);
            paymentRepository.save(payment);
        }
    }

}


