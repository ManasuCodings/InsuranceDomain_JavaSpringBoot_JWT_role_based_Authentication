package com.insurance_domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClaimsDto {


    private Long claimId;


    private String claimType;


    private CustomerDto2 customerDto2;
}
