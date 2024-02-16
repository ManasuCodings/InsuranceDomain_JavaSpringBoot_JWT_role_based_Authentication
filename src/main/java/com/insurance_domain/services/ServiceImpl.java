package com.insurance_domain.services;

import com.insurance_domain.dtos.*;
import com.insurance_domain.entity.*;
import com.insurance_domain.exceptation.NoRecordFoundException;
import com.insurance_domain.repository.*;
import com.insurance_domain.util.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    private ClaimRepo claimRepo;

    @Autowired
    private PolicyRepo policyRepo;
    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PaymentRepo paymentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public CustomerDto registerNewUser(CustomerDto customerDto) {

        //encoded password

        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        //setting role
        Role role = this.roleRepository.findById(AppConstants.ROLE_CUSTOMER).get();
        customer.getRoles().add(role);
        System.out.println(customer.getCustId());
        System.out.println(customer.getEmail());
        System.out.println(customer.getCustName());
        System.out.println(customer.getPassword());
        Customer save = customerRepository.save(customer);
        CustomerDto customerDto1 = mapToCustomerDto(save);

        return customerDto1;
    }
    @Override
    public CustomerDto registerAdmin(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustName(customerDto.getCustName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        Role role = roleRepository.findById(AppConstants.ROLE_ADMINISTRATOR).get();
        customer.getRoles().add(role);
        Customer save = customerRepository.save(customer);
        CustomerDto customerDto1 = mapToCustomerDto(save);
        return customerDto1;
    }

    @Override
    public CustomerDto registerAgent(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setCustName(customerDto.getCustName());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        Role role = roleRepository.findById(AppConstants.ROLE_AGENT).get();
        customer.getRoles().add(role);
        Customer save = customerRepository.save(customer);
        CustomerDto customerDto1 = mapToCustomerDto(save);
        return customerDto1;
    }


    @Override
    public List<CustomerDto> getAllCustomer() {

        List<Customer> all = customerRepository.findAll();
        List<CustomerDto> collect = all.stream().map(x->mapToCustomerDto(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CustomerDto getOneCustomer(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoRecordFoundException("no such record"));


//        CustomerDto map = modelMapper.map(customer, CustomerDto.class);

        CustomerDto customerDto=mapToCustomerDto(customer);
        return customerDto;
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustName(customer.getCustName());
        customerDto.setCustId(customer.getCustId());
        customerDto.setEmail(customer.getEmail());

//        String password = bCryptPasswordEncoder.encode(customer.getPassword());
        customerDto.setPassword(customer.getPassword());
        List<Claims> allClaimsOfOneCustomer = claimRepo.findAllClaimsOfOneCustomer(customer.getCustId());
        List<ClaimsDto> customerDtolist = allClaimsOfOneCustomer.stream().map(x -> mapToClaimDto(x)).collect(Collectors.toList());
        
        List<Policy> allPolicyOfOneCustomer = policyRepo.findAllPolicyOfOneCustomer(customer.getCustId());
        List<PolicyDto> policyDtoList = allPolicyOfOneCustomer.stream().map(x -> mapToPolicyDto(x)).collect(Collectors.toList());
        
        List<Payment> allTransactionByCustomer = paymentRepo.findAllTransactionByCustomer(customer.getCustId());
        List<PaymentDto> paymentDtoList = allTransactionByCustomer.stream().map(x -> mapToPaymentDto(x)).collect(Collectors.toList());

        customerDto.setClaims(customerDtolist);
        customerDto.setPolicy(policyDtoList);
        customerDto.setPayment(paymentDtoList);
        return customerDto;
    }


    @Override
    public List<PolicyDto> getAllPoliciesOfCustomer(long id) {
        List<Policy> policyOfOneCustomer = policyRepo.findAllPolicyOfOneCustomer(id);
        List<PolicyDto> collect = policyOfOneCustomer.stream().map(x ->mapToPolicyDto(x)).collect(Collectors.toList());
        return collect;
    }

    private PolicyDto mapToPolicyDto(Policy x) {
        PolicyDto policyDto = new PolicyDto();
        policyDto.setPolicyId(x.getPolicyId());
        policyDto.setPolicyType(x.getPolicyType());

        Optional<Long> customerIdUsingPolicyId = policyRepo.findCustomerIdUsingPolicyId(x.getPolicyId());
        if(customerIdUsingPolicyId.isPresent()){
            Long cusId = customerIdUsingPolicyId.get();
            System.out.println(cusId);
            Customer customer = customerRepository.findById(cusId).orElseThrow(() -> new NoRecordFoundException("no such record"));
            CustomerDto2 customerDto2 = new CustomerDto2();
            customerDto2.setCustName(customer.getCustName());
            customerDto2.setCustId(customer.getCustId());
            customerDto2.setEmail(customer.getEmail());
            policyDto.setCustomerDto2(customerDto2);
        }else {

            return policyDto;

//            throw new NoRecordFoundException("no customer id found for policy id "+x.getPolicyId());
        }
        return policyDto;
    }

    @Override
    public List<ClaimsDto> getAllClaimsOfCustomer(long id) {
        List<Claims> allClaimsOfOneCustomer =claimRepo.findAllClaimsOfOneCustomer(id);
        List<ClaimsDto> collect = allClaimsOfOneCustomer.stream().map(x ->mapToClaimDto(x)).collect(Collectors.toList());
        return collect;
    }


    @Override
    public List<PolicyDto> getAllPolicies() {
        List<Policy> all = policyRepo.findAll();
        List<PolicyDto> collect = all.stream().map(x ->mapToPolicyDto(x)).collect(Collectors.toList());
        return collect;
    }

    public PolicyDto getPolicyBY_Id(long id) {
        Policy policy = policyRepo.findById(id).get();
        PolicyDto map = modelMapper.map(policy, PolicyDto.class);

        Optional<Long> customerIdUsingPolicyId = policyRepo.findCustomerIdUsingPolicyId(id);
        if(customerIdUsingPolicyId.isPresent()){
            Long customerId = customerIdUsingPolicyId.get();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoRecordFoundException("no such record"));
            CustomerDto2 customerDto2 = new CustomerDto2();
            customerDto2.setCustId(customer.getCustId());
            customerDto2.setCustName(customer.getCustName());
            customerDto2.setEmail(customer.getEmail());
            map.setCustomerDto2(customerDto2);
        }else {
            return map;
        }

        return map;
    }

    @Override
    public Only_Policy createOnePolicy(Only_Policy onlyPolicy) {

        Policy policy = modelMapper.map(onlyPolicy, Policy.class);
        Policy save = policyRepo.save(policy);
        Only_Policy map = modelMapper.map(save, Only_Policy.class);
        System.out.println(map.toString());
        return map;
    }

    @Override
    public List<ClaimsDto> AllClaimsList() {
        List<Claims> all = claimRepo.findAll();
        List<ClaimsDto> collect = all.stream().map(x ->mapToClaimDto(x)).collect(Collectors.toList());

        return collect;
    }

    private ClaimsDto mapToClaimDto(Claims x) {
        ClaimsDto claimsDto = new ClaimsDto();
        claimsDto.setClaimId(x.getClaimId());
        claimsDto.setClaimType(x.getClaimType());

        CustomerDto2 customerDto2 = new CustomerDto2();

        Optional<Long> customerId = claimRepo.findCustormerId(x.getClaimId());
        if(customerId.isPresent()){
            Long customer_Id = customerId.get();
            Customer customer = customerRepository.findById(customer_Id).orElseThrow(() -> new NoRecordFoundException("no such record"));
            customerDto2.setCustName(customer.getCustName());
            customerDto2.setCustId(customer.getCustId());
            customerDto2.setEmail(customer.getEmail());
            claimsDto.setCustomerDto2(customerDto2);

        }else {
            return claimsDto;
        }

        return  claimsDto;
    }

    public CustomerDto2 setCustomer2_data(String custName,long customerId){
        CustomerDto2 customerDto2 = new CustomerDto2();
        customerDto2.setCustId(customerId);
        customerDto2.setCustName(custName);

        return customerDto2;
    }

    @Override
    public ClaimsDto getOneClaimById(long id) {
        Claims claims = claimRepo.findById(id).orElseThrow(() -> new NoRecordFoundException("no such record"));
        ClaimsDto claimsDto = modelMapper.map(claims, ClaimsDto.class);
        if(claims.getCustomer()!=null){
            Customer customer=claims.getCustomer();
            CustomerDto2 customerDto2 = new CustomerDto2();
            customerDto2.setCustName(customer.getCustName());
            customerDto2.setCustId(customer.getCustId());
            customerDto2.setEmail(customer.getEmail());
            claimsDto.setCustomerDto2(customerDto2);
        }

        return claimsDto;
    }

    @Override
    public ClaimsDto createNewClaim(ClaimsDto claimsDto) {
        Claims map = modelMapper.map(claimsDto, Claims.class);
        Claims save = claimRepo.save(map);
        ClaimsDto map1 = modelMapper.map(save, ClaimsDto.class);
        return map1;
    }

    @Override
    public CustomerDto saveOneCustomer(CustomerDto customerDto) {

        Customer customer = modelMapper.map(customerDto, Customer.class);
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        Customer save = customerRepository.save(customer);
        CustomerDto customerDto1 = modelMapper.map(save, CustomerDto.class);
        return customerDto1;
    }

    @Override
    public PaymentDto createOnePayment(PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        Payment save = paymentRepo.save(payment);
        PaymentDto paymentDto1 = modelMapper.map(save, PaymentDto.class);
        return paymentDto1;
    }

    @Override
    public Claims findClaimByClaimType(String claimType) {
        Claims claimByType = claimRepo.findClaimByType(claimType);
        return claimByType;
    }

    @Override
    public Policy findPolicyByPolicyType(String policyType) {
        Policy policyByPolicyType = policyRepo.findPolicyByPolicyType(policyType);
        return policyByPolicyType;
    }

    @Override
    public Payment findAountbyAmount(long amount) {
        Payment paymentByAount = paymentRepo.findPaymentByAount(amount);
        return paymentByAount;
    }

    @Override
    public ClaimsDto createClaim_Customer_According(ClaimsDto claimsDto, long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoRecordFoundException("no such record"));
        Claims claims = modelMapper.map(claimsDto, Claims.class);
        claims.setCustomer(customer);

        Claims save = claimRepo.save(claims);


        ClaimsDto map = modelMapper.map(save, ClaimsDto.class);

        CustomerDto2 customerDto_2 = new CustomerDto2();
        customerDto_2.setCustId(customer.getCustId());
        customerDto_2.setCustName(customer.getCustName());
        customerDto_2.setEmail(customer.getEmail());

        map.setCustomerDto2(customerDto_2);

//      CustomerDto2 customerDto2=convertToCustomerDto2(save);

        return map;
    }

    @Override
    public PaymentDto createPaymentCustomerAccording(PaymentDto paymentDto, long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoRecordFoundException("no such record"));
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setCustomer(customer);
        Payment save = paymentRepo.save(payment);

        PaymentDto payDto_2 = modelMapper.map(save, PaymentDto.class);

        CustomerDto2 customerDto2 = new CustomerDto2();
        customerDto2.setCustName(customer.getCustName());
        customerDto2.setCustId(customer.getCustId());
        customerDto2.setEmail(customer.getEmail());

        payDto_2.setCustomerDto2(customerDto2);
        return payDto_2;
    }

    @Override
    public PolicyDto createNewPolicyCustomerWise(PolicyDto policyDto, long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoRecordFoundException("no such record"));
        Policy policy = modelMapper.map(policyDto, Policy.class);
        policy.setCustomer(customer);
        Policy save = policyRepo.save(policy);

        PolicyDto map = modelMapper.map(save, PolicyDto.class);

        CustomerDto2 customerDto2 = new CustomerDto2();
        customerDto2.setCustId(customer.getCustId());
        customerDto2.setCustName(customer.getCustName());
        customerDto2.setEmail(customer.getEmail());

        map.setCustomerDto2(customerDto2);
        return map;
    }

    @Override
    public void delPaymentByPaymentId(long paymentId) {
        paymentRepo.deleteById(paymentId);
    }

    @Override
    public void delClaimByClaimId(long claimId) {
        claimRepo.deleteById(claimId);
    }

    @Override
    public void delPolicyByPolicyId(long policyId) {
        policyRepo.deleteById(policyId);
    }

    @Override
    public void delCustomerByCustomerId(long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<PaymentDto> findPaymentByCust_Id(long customerId) {
        List<Payment> paymentList=paymentRepo.findPaymentByCustomerId(customerId);
        List<PaymentDto> collect = paymentList.stream().map(x -> mapToPaymentDto(x)).collect(Collectors.toList());
        return collect;
    }



    private PaymentDto mapToPaymentDto(Payment x) {
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setAmount(x.getAmount());
        paymentDto.setPaymentId(x.getPaymentId());

        CustomerDto2 customerDto2 = new CustomerDto2();
        Optional<Long> userIdUsingPaymentId = paymentRepo.findUserIdUsingPaymentId(x.getPaymentId());

        if(userIdUsingPaymentId.isPresent()){
            Long customer_Id = userIdUsingPaymentId.get();

            Customer customer = customerRepository.findById(customer_Id).orElseThrow(() -> new NoRecordFoundException("no such record"));
            customerDto2.setCustName(customer.getCustName());
            customerDto2.setCustId(customer.getCustId());
            customerDto2.setEmail(customer.getEmail());
            paymentDto.setCustomerDto2(customerDto2);
        }else {
            return paymentDto;
        }

        return paymentDto;
    }



}
