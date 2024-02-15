package com.insurance_domain.controller;

import com.insurance_domain.dtos.CustomerDto;
import com.insurance_domain.entity.Customer;
import com.insurance_domain.exceptation.NoRecordFoundException;
import com.insurance_domain.security.JwtAuthRequest;
import com.insurance_domain.security.JwtAuthResponse;
import com.insurance_domain.security.JwtTokenHelper;
import com.insurance_domain.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/insurance")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Service serviceImpl;
    @PostMapping("/api/authenticate")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
        this.autheticate(jwtAuthRequest.getEmail(), jwtAuthRequest.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getEmail());

        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();

        jwtAuthResponse.setToken(token);

        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);



    }
    @PostMapping("/api/register")
    public ResponseEntity<CustomerDto> registerUser(@RequestBody CustomerDto customerDto){
        CustomerDto customerDto1 = serviceImpl.registerNewUser(customerDto);
        ResponseEntity<CustomerDto> customerDtoResponseEntity = new ResponseEntity<>(customerDto1, HttpStatus.CREATED);
        return customerDtoResponseEntity;
    }

    private void autheticate(String email, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new  UsernamePasswordAuthenticationToken(email,password);

        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e){
            throw  new NoRecordFoundException("invalid details");
        }

    }
}
