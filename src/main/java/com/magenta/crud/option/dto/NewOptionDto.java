package com.magenta.crud.option.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class NewOptionDto {

    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String name;

    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String optionDescription;

    @NotNull
    @Min(value = 1, message = "Set price, please")
    private double price;

    @NotNull
    @Min(value = 1, message = "Set cost, please")
    private double addCost;

    private Set<Integer> leadOptions;
    private Set<Integer> excludingOptions;
}
