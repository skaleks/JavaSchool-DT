package com.magenta.crud.option.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OptionPageDto {

    private OptionDto option;

    public OptionPageDto(OptionDto option) {
        this.option = option;
    }
}
