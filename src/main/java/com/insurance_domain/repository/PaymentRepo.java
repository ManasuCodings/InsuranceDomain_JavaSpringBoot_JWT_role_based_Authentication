package com.insurance_domain.repository;

import com.insurance_domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

    @Query(value = "select * from payment where amount= :amount1",nativeQuery = true)
    Payment findPaymentByAount(@Param("amount1") long amount1);

    @Query(value = "select * from payment where customer_id= :cust_id",nativeQuery = true)
    List<Payment> findAllTransactionByCustomer(@Param("cust_id") long custId);

    @Query(value = "select customer_id from payment where payment_id= :cust_id",nativeQuery = true)
    Optional<Long> findUserIdUsingPaymentId(@Param("cust_id") Long paymentId);

    @Query(value = "select * from payment where customer_id= :customerId",nativeQuery = true)
    List<Payment> findPaymentByCustomerId(@Param("customerId") long customerId);
}
