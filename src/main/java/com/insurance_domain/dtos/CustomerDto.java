package com.insurance_domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {


    private long custId;
    private String custName;
    private String email;

    private String password;
    private List<PolicyDto> policy;
    private List<PaymentDto> payment;
    private List<ClaimsDto> claims;

}
