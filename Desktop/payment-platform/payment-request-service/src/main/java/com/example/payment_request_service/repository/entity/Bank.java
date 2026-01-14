package com.example.payment_request_service.repository.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity(name="bank")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Bank {

    @Id
    Integer bankId;

    String bankName;

    @OneToMany(mappedBy = "bank",cascade = CascadeType.ALL)
    List<UserBank>userBanks;

    public Bank(Integer bankId, String bankName) {
        this.bankId = bankId;
        this.bankName = bankName;
    }
}
