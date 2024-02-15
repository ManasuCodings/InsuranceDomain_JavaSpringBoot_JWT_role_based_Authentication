package com.insurance_domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "amount",nullable = false)
    private long amount;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;


}
