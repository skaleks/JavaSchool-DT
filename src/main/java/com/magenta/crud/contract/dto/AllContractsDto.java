package com.magenta.crud.contract.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllContractsDto implements Serializable {

    List<ContractDto> contractDtoList;
}
