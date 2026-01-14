package com.example.payment_request_service.repository;

import com.example.payment_request_service.repository.entity.User;
import com.example.payment_request_service.repository.entity.UserBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("""
    SELECT u.userBanks
    FROM users u
    WHERE u.userId = :userId
""")
    List<UserBank> findUserBanks(Integer userId);

}
