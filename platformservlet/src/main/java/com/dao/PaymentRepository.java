package com.dao;

import com.model.Payment;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PaymentRepository extends CrudRepository<Payment, Integer> {

    public Optional<Payment> findByOrOrderId(String orderId);
}
