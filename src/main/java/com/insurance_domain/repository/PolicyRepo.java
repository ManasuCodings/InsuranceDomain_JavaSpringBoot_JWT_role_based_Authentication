package com.insurance_domain.repository;

import com.insurance_domain.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PolicyRepo extends JpaRepository<Policy, Long> {
    @Query(value="select * from policy where customer_id= :id", nativeQuery = true)
    List<Policy> findAllPolicyOfOneCustomer(@Param("id") Long id);

    @Query(value = "select * from policy where policy_type= :policy_type1", nativeQuery = true)
    Policy findPolicyByPolicyType(@Param("policy_type1") String policy_type1);

    @Query(value = "select customer_id from policy where policy_id= :customerId",nativeQuery = true)
    Optional<Long> findCustomerIdUsingPolicyId(@Param("customerId") long id);
}
