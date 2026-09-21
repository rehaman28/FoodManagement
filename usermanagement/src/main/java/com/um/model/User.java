package com.um.model;


import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String userName;
    @Column (unique = true,nullable = false)
    private String userPhone;
    @Column (unique = true,nullable = false)
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL,
        orphanRemoval =  true
    )
    @JoinColumn(name = "user_id")
    private List<UserAddress> userAddresses;
}
