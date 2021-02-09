package com.magenta.crud.option;


import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "OPTIONS", schema = "public", catalog = "Magenta_Operator")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "COST")
    private Double addCost;

    @Column(name = "STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @Column(name = "DESCRIPTION")
    private String optionDescription;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "TARIFF_OPTION",
//            joinColumns = {@JoinColumn(name = "OPTION_ID", referencedColumnName = "OPTION_ID")},
//            inverseJoinColumns = {@JoinColumn(name = "TARIFF_ID",referencedColumnName = "TARIFF_ID")})
//    private Set<Tariff> tariffs;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "RELATED_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RELATED_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<Option> relatedOptions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "EXCLUDING_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EXCLUDING_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<Option> excludedOptions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "LEAD_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RELATED_FOR", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<Option> leadOptions;
}
