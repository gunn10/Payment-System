package com.example.payment_request_service.repository.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="user_bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBank {

    @Id
    Integer id;

    @ManyToOne
    User user;

    @ManyToOne
    Bank bank;

    Integer balance;

}
