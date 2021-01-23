package com.magenta.crud.user;

import com.magenta.crud.contract.Contract;
import com.magenta.crud.type.Role;
import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER", schema = "public", catalog = "Magenta_Operator")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID",nullable = false)
    private Integer id;

    @Column(name = "FIRST_NAME",nullable = false)
    private String firstname;

    @Column(name = "LAST_NAME",nullable = false)
    private String lastname;

    @Column(name = "BIRTH_DATE",nullable = false)
    private Date birthdate;

    @Column(name = "PASSPORT",nullable = false)
    private String passport;

    @Column(name = "ADDRESS",nullable = false)
    private String address;

    @Column(name = "EMAIL",nullable = false, unique = true)
    private String email;

    @Column(name = "LOGIN", unique = true, nullable = false)
    private String login;

    @Column(name = "PASSWORD",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_STATUS", nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_ROLE", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Contract> numbers;
}
