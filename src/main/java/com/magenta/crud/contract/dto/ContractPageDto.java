package com.magenta.crud.contract.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContractPageDto {

    private ContractDto contract;

    public ContractPageDto(ContractDto contract) {
        this.contract = contract;
    }
}
