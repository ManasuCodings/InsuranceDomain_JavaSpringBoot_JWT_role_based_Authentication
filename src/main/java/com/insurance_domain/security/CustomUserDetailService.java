package com.insurance_domain.security;

import com.insurance_domain.entity.Customer;
import com.insurance_domain.exceptation.NoRecordFoundException;
import com.insurance_domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading gmail from database as username
        Customer customer = customerRepository.findByEmail(username).orElseThrow(() -> new NoRecordFoundException("user not found with email " + username));
        return customer;
    }
}
