package com.magenta.crud.tariff.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SwitchTariffDto {

    private int userId;
    private int contractId;
    private int tariffId;
}
