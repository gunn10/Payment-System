package com.example.payment_request_service.repository;

import com.example.payment_request_service.repository.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankRepository extends JpaRepository<UserBank,Integer> {

    @Query("""
    SELECT ub.balance
    FROM user_bank ub
    WHERE ub.user.userId = :userId
    AND ub.bank.bankId = :bankId
""")
    Integer findBalanceByUserIdAndBankId(Integer userId, Integer bankId);

    @Query("""
    SELECT ub
    FROM user_bank ub
    WHERE ub.user.userId = :userId
    AND ub.bank.bankId = :bankId
""")
    Optional<UserBank> findByUserIdAndBankId(Integer userId, Integer bankId);
}
