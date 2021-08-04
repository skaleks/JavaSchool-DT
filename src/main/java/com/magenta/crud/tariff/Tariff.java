package com.magenta.crud.tariff;

import com.magenta.crud.contract.Contract;
import com.magenta.crud.option.Option;
import com.magenta.crud.showcase.ShowcaseItem;
import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TARIFFS", schema = "public", catalog = "Magenta_Operator")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARIFF_ID")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PRICE",nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @OneToMany(mappedBy = "tariff", fetch = FetchType.LAZY)
    private Set<Contract> contracts;

    @OneToOne(mappedBy = "tariff", fetch = FetchType.LAZY)
    private ShowcaseItem showcaseItem;

    @Column(name = "DESCRIPTION")
    private String tariffDescription;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TARIFF_OPTION",
            joinColumns = {@JoinColumn(name = "TARIFF_ID", referencedColumnName = "TARIFF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "OPTION_ID",referencedColumnName = "OPTION_ID")})
    private Set<Option> options;

}
