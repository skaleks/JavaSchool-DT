package com.magenta.crud.showcase;


import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SHOWCASE_ITEMS",schema = "public", catalog = "Magenta_Operator")
public class ShowcaseItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    private int id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TARIFF_ID", referencedColumnName = "TARIFF_ID", nullable = false)
    private Tariff tariff;

    public ShowcaseItem(Tariff tariff){
        this.tariff = tariff;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "ITEM_STATUS", nullable = false)
    private Status status;

    @Column(name = "ITEM_DESCRIPTION")
    private String description;
}
