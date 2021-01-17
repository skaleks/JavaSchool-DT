package com.magenta.crud.option.dto;

import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewOptionDto {

    private String name;
    private String optionDescription;
    private double price;
    private double addCost;
    private Status status;
}
