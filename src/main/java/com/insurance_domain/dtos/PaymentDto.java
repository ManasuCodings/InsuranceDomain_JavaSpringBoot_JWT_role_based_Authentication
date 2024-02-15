package com.insurance_domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDto {


    private Long paymentId;

    private long amount;

    private CustomerDto2 customerDto2;
}
