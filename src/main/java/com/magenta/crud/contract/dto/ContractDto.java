package com.magenta.crud.contract.dto;

import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ContractDto implements Serializable {

    private int id;
    private String number;
    private double price;
    private Status status;
    private TariffDto tariff;
    private User user;
}
