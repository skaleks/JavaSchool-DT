package com.magenta.crud.contract.dto;


import com.magenta.crud.tariff.dto.TariffDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ContractPageDto {

    private ContractDto contract;
    private List<TariffDto> tariffDtoList;

    public ContractPageDto(ContractDto contract, List<TariffDto> tariffDtoList) {
        this.contract = contract;
        this.tariffDtoList = tariffDtoList;
    }
}
