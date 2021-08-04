package com.magenta.crud.option.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DelRelativeFromOption {

    private int targetOptionId;
    private int addedOptionId;
    private String optionRelative;
}
