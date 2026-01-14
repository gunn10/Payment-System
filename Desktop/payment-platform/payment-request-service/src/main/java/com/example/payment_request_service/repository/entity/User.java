package com.example.payment_request_service.repository.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    Integer userId;

    String userName;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<UserBank>userBanks;

    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
