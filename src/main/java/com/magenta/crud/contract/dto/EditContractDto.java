package com.magenta.crud.contract.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class EditContractDto {

    @NotNull
    private int optionId;
    @NotNull
    private int contractId;
}
