package com.insurance_domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;




@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long policyId;

    @Column(name = "policy_type",nullable = false)
    private String policyType;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


}
