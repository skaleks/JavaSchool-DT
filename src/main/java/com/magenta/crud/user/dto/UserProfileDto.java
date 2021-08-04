package com.magenta.crud.user.dto;


import com.magenta.crud.contract.dto.ContractDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileDto {

    private UserDto user;
    private List<ContractDto> numbers;
}
