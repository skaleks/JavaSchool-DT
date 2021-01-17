package com.magenta.crud.contract.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class NewContractDto implements Serializable {

    private String number;
    private int tariffId;
    private int userId;
}
