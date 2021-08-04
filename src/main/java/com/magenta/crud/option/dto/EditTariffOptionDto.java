package com.magenta.crud.option.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class EditTariffOptionDto implements Serializable {

    private int tariffId;
    private int optionId;
}
