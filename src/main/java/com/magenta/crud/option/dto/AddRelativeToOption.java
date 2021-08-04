package com.magenta.crud.option.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class AddRelativeToOption {

    private int targetOptionId;
    private int addedOptionId;
    private String optionRelative;
}
