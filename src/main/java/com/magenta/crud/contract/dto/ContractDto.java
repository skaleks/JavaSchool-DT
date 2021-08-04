package com.magenta.crud.contract.dto;

import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class ContractDto implements Serializable {

    private int id;
    private String number;
    private double price;
    private Status status;
    private TariffDto tariff;
    private UserDto user;
    private Set<OptionDto> options;
}
