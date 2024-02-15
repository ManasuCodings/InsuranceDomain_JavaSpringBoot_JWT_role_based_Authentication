package com.insurance_domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyDto {


    private long policyId;

    private String policyType;
//
    private CustomerDto2 customerDto2;

}
