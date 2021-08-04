package com.magenta.crud.tariff.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddTariffToShowcase {

    private int tariffId;
    private String description;
}
