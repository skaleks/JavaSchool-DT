package com.magenta.crud.tariff.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class NewTariffDto implements Serializable {

    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String name;

    @NotNull
    @NotEmpty(message = "Should not be empty")
    private String tariffDescription;

    @NotNull
    @Min(value = 1, message = "Set price, please")
    private double price;

    private List<Integer> availableOptionsForThisTariff;
}
