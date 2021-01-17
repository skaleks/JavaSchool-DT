package com.magenta.crud.contract;

import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CONTRACT", schema = "public", catalog = "Magenta_Operator")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID")
    private Integer id;

    @Column(name = "NUMBER", nullable = false, unique = true)
    private String number;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "CONTRACT_STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tariff tariff;
}