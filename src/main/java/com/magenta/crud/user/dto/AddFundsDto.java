package com.magenta.crud.user.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddFundsDto {

    private int userId;
    private double funds;
}
