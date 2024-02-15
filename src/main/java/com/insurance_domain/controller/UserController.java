package com.insurance_domain.controller;


import com.insurance_domain.dtos.*;
import com.insurance_domain.services.Service;
import com.insurance_domain.services.ServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/insurance")
public class UserController {

    @Autowired
    private ServiceImpl serviceImpl;

    @Autowired
    private Service service;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/api/customer")
    public ResponseEntity<CustomerDto> postOneCustomer( @RequestBody CustomerDto customerDto )
    {

        CustomerDto customerDto1=service.saveOneCustomer(customerDto);

        ResponseEntity<CustomerDto> customerDtoResponseEntity = new ResponseEntity<>(customerDto1, HttpStatus.CREATED);
        return customerDtoResponseEntity;
    }

    @GetMapping("/api/customers")
    public ResponseEntity<List<CustomerDto>> getALlCustomer() {
        List<CustomerDto> allCustomer = serviceImpl.getAllCustomer();
        ResponseEntity<List<CustomerDto>> listResponseEntity = new ResponseEntity<>(allCustomer, HttpStatus.OK);
        return listResponseEntity;
    }

    @GetMapping("/api/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") long id) {
        CustomerDto customerDto = serviceImpl.getOneCustomer(id);
        ResponseEntity<CustomerDto> customerDtoResponseEntity = new ResponseEntity<>(customerDto, HttpStatus.OK);
        return customerDtoResponseEntity;
    }

    @GetMapping("/api/customers/{id}/policies")
    public ResponseEntity<List<PolicyDto>> getAllPoliciesCustomerAccording(@PathVariable("id") long id) {
        List<PolicyDto> policyDtoList = serviceImpl.getAllPoliciesOfCustomer(id);
        ResponseEntity<List<PolicyDto>> listResponseEntity = new ResponseEntity<>(policyDtoList, HttpStatus.OK);
        return listResponseEntity;
    }

    @GetMapping("/api/customers/{id}/claims")
    public ResponseEntity<List<ClaimsDto>> getAllClaimsCustomerAccording(@PathVariable("id") long id) {
        List<ClaimsDto> ClaimsDtoList = serviceImpl.getAllClaimsOfCustomer(id);
        ResponseEntity<List<ClaimsDto>> listResponseEntity = new ResponseEntity<>(ClaimsDtoList, HttpStatus.OK);
        return listResponseEntity;
    }

    @GetMapping("/api/policies")
    public ResponseEntity<List<PolicyDto>> getAllPoliciesList() {
        List<PolicyDto> allPolicies = serviceImpl.getAllPolicies();
        ResponseEntity<List<PolicyDto>> listResponseEntity = new ResponseEntity<>(allPolicies, HttpStatus.OK);
        return listResponseEntity;
    }

    @GetMapping("/api/policies/{id}")
    public ResponseEntity<PolicyDto> getPolicyBYId(@PathVariable("id") long id) {
        PolicyDto policyDto = serviceImpl.getPolicyBY_Id(id);
        ResponseEntity<PolicyDto> policyDtoResponseEntity = new ResponseEntity<>(policyDto, HttpStatus.OK);
        return policyDtoResponseEntity;
    }

    @PostMapping("/api/policies")
    public ResponseEntity<Only_Policy> createNewPolicy(@RequestBody Only_Policy onlyPolicy) {

        Only_Policy onePolicy1 = serviceImpl.createOnePolicy(onlyPolicy);

        ResponseEntity<Only_Policy> policyDtoResponseEntity = new ResponseEntity<>(onePolicy1, HttpStatus.CREATED);
        return policyDtoResponseEntity;
    }

    @PostMapping("/api/policies/{customerId}")
    public ResponseEntity<PolicyDto> createNewPolicyCustomerBased(@RequestBody PolicyDto policyDto,@PathVariable("customerId") long customerId) {

        PolicyDto policyDto1 = service.createNewPolicyCustomerWise(policyDto,customerId);

        ResponseEntity<PolicyDto> policyDtoResponseEntity = new ResponseEntity<>(policyDto1, HttpStatus.CREATED);
        return policyDtoResponseEntity;
    }
    @GetMapping("/api/claims")
    public ResponseEntity<List<ClaimsDto>> allClaims() {
        List<ClaimsDto> claimsDtos = serviceImpl.AllClaimsList();
        ResponseEntity<List<ClaimsDto>> listResponseEntity = new ResponseEntity<>(claimsDtos, HttpStatus.OK);
        return listResponseEntity;
    }

    @GetMapping("/api/claims/{id}")
    public ResponseEntity<ClaimsDto> getClaimById(@PathVariable("id") long id) {
        ClaimsDto claimsDto = serviceImpl.getOneClaimById(id);
        ResponseEntity<ClaimsDto> claimsDtoResponseEntity = new ResponseEntity<>(claimsDto, HttpStatus.OK);
        return claimsDtoResponseEntity;
    }
    @PostMapping("/api/claims")
    public ResponseEntity<ClaimsDto> createOneClaim(@RequestBody ClaimsDto claimsDto) {
        ClaimsDto claimsDto1 = serviceImpl.createNewClaim(claimsDto);
        ResponseEntity<ClaimsDto> claimsDtoResponseEntity = new ResponseEntity<>(claimsDto1, HttpStatus.CREATED);
        return claimsDtoResponseEntity;
    }
    @PostMapping("/api/claims/{customerId}")
    public ResponseEntity<ClaimsDto> createClaimCustomerAccording(@RequestBody ClaimsDto claimsDto,@PathVariable("customerId") long customerId) {
        ClaimsDto claimsDto1 = service.createClaim_Customer_According(claimsDto,customerId);

        ResponseEntity<ClaimsDto> claimsDtoResponseEntity = new ResponseEntity<>(claimsDto1, HttpStatus.CREATED);

        return claimsDtoResponseEntity;
    }

    @PostMapping("/api/payment")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto){
        PaymentDto paymentDto1=serviceImpl.createOnePayment(paymentDto);
        ResponseEntity<PaymentDto> paymentDtoResponseEntity = new ResponseEntity<>(paymentDto1, HttpStatus.CREATED);
        return paymentDtoResponseEntity;
    }
    @PostMapping("/api/payment/{customerId}")
    public ResponseEntity<PaymentDto> createPaymentByCustomer(@RequestBody PaymentDto paymentDto,@PathVariable("customerId") long customerId){
        PaymentDto paymentDto1=service.createPaymentCustomerAccording(paymentDto,customerId);
        ResponseEntity<PaymentDto> paymentDtoResponseEntity = new ResponseEntity<>(paymentDto1, HttpStatus.CREATED);
        return paymentDtoResponseEntity;
    }
    @GetMapping("/api/payment/{customerId}")
    public ResponseEntity<List<PaymentDto>> findPaymentByCustomerId(@PathVariable("customerId") long customerId){
        List<PaymentDto> paymentDtoList=service.findPaymentByCust_Id(customerId);
        ResponseEntity<List<PaymentDto>> listResponseEntity = new ResponseEntity<>(paymentDtoList, HttpStatus.CREATED);
        return listResponseEntity;
    }

    @DeleteMapping("/api/payment/delete/{paymentId}")
    public String delPayment(@PathVariable("paymentId") long paymentId){
         service.delPaymentByPaymentId(paymentId);

        return "Payment with id "+paymentId+" has deleted";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/api/claim/delete/{claimId}")
    public String delClaim(@PathVariable("claimId") long claimId){
        service.delClaimByClaimId(claimId);

        return "Claim with id "+claimId+" has deleted";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/api/policy/delete/{policyId}")
    public String delPolicy(@PathVariable("policyId") long policyId){
        service.delPolicyByPolicyId(policyId);

        return "Policy with id "+policyId+" has deleted";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/api/customer/delete/{customerId}")
    public String delCustomer(@PathVariable("customerId") long customerId){
        service.delCustomerByCustomerId(customerId);

        return "Customer with id "+customerId+" has deleted";
    }



}
