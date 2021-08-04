package com.magenta.crud.global.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeStatusDto {

    private int entityId;
    private String status;
}
