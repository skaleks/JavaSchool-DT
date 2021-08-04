package com.magenta.crud.tariff.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllTariffDto implements Serializable {

    List<TariffDto> tariffDtoList;
}
