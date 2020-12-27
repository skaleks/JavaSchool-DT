package com.magenta.model;


import com.magenta.model.check.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "OPTION", schema = "public", catalog = "Magenta_Operator")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Integer id;

    @Column(name = "OPTION_NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "OPTION_PRICE")
    private Double price;

    @Column(name = "OPTION_COST")
    private Double addCost;

    @Column(name = "OPTION_STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TARIFF_OPTION",
            joinColumns = {@JoinColumn(name = "OPTION_ID", referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "TARIFF_ID",referencedColumnName = "TARIFF_ID")})
    private Set<Tariff> tariffs;
}
