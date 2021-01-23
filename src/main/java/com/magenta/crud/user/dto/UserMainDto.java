package com.magenta.crud.user.dto;

import com.magenta.crud.contract.Contract;
import com.magenta.crud.tariff.dto.TariffDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserMainDto {

    private int userId;
    private String firstname;
    private String lastname;
    private String email;
    private String login;
    private String status;
    private List<TariffDto> tariffDtoList;
    private List<Contract> contractList;
}
