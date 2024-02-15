package com.insurance_domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "claims")
public class Claims {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @Column(name = "claim_type")
    private String claimType;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


}
