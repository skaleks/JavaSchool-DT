package com.magenta.crud.user.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
public class AddFundsDto {

    private int userId;

    @Min(value = 1, message = "No, this is our money!")
    private double funds;
}
