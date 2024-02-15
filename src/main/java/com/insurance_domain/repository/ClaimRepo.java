package com.insurance_domain.repository;

import com.insurance_domain.entity.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClaimRepo extends JpaRepository<Claims,Long> {
    @Query(value ="select * from claims where customer_id= :id", nativeQuery = true)
    List<Claims> findAllClaimsOfOneCustomer(@Param("id") Long id);

    @Query(value = "select * from claims where claim_type = :claimType1", nativeQuery = true)
    Claims findClaimByType(@Param("claimType1") String  claimType1);

    @Query(value = "select customer_id from claims where claim_id= :claim_id1",nativeQuery = true)
    Optional<Long> findCustormerId(@Param("claim_id1") Long claimId);
}
