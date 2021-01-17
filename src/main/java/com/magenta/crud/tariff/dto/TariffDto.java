package com.magenta.crud.tariff.dto;


import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
public class TariffDto implements Serializable {

    private int id;
    private String name;
    private String tariffDescription;
    private double price;
    private Status status;
    private Set<OptionDto> options;
}
