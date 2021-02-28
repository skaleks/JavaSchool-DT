package com.magenta.crud.contract;

import com.magenta.crud.option.Option;
import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CONTRACTS", schema = "public", catalog = "Magenta_Operator")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID")
    private Integer id;

    @Column(name = "NUMBER", nullable = false, unique = true)
    private String number;

    @Column(name = "PRICE", nullable = false)
    private Double price;

    @Column(name = "STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tariff tariff;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CONTRACT_OPTIONS",
            joinColumns = {@JoinColumn(name = "CONTRACT_ID", nullable = false, referencedColumnName = "CONTRACT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<Option> options;
}
