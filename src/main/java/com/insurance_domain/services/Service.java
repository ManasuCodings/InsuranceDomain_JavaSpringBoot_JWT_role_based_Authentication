package com.insurance_domain.services;

import com.insurance_domain.dtos.*;
import com.insurance_domain.entity.Claims;
import com.insurance_domain.entity.Payment;
import com.insurance_domain.entity.Policy;

import java.util.List;

public interface Service {

    CustomerDto registerNewUser(CustomerDto customerDto);
    List<CustomerDto> getAllCustomer();

    CustomerDto getOneCustomer(long id);

    List<PolicyDto> getAllPoliciesOfCustomer(long id);

    List<ClaimsDto> getAllClaimsOfCustomer(long id);

    List<PolicyDto> getAllPolicies();

    PolicyDto getPolicyBY_Id(long id);

    Only_Policy createOnePolicy(Only_Policy policyDto);

    List<ClaimsDto> AllClaimsList();

    ClaimsDto getOneClaimById(long id);

    ClaimsDto createNewClaim(ClaimsDto claimsDto);

    CustomerDto saveOneCustomer(CustomerDto customerDto);

    PaymentDto createOnePayment(PaymentDto paymentDto);

    Claims findClaimByClaimType(String claimType);

    Policy findPolicyByPolicyType(String policyType);

    Payment findAountbyAmount(long amount);

    ClaimsDto createClaim_Customer_According(ClaimsDto claimsDto, long customerId);

    PaymentDto createPaymentCustomerAccording(PaymentDto paymentDto, long customerId);

    PolicyDto createNewPolicyCustomerWise(PolicyDto policyDto, long customerId);

    void delPaymentByPaymentId(long paymentId);

    void delClaimByClaimId(long claimId);

    void delPolicyByPolicyId(long policyId);

    void delCustomerByCustomerId(long customerId);

    List<PaymentDto> findPaymentByCust_Id(long customerId);
}
