package com.magenta.crud.option.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class OptionPageDto {

    private OptionDto option;
    private Set<OptionDto> availableOptionsToChoose;

    public OptionPageDto(OptionDto option, Set<OptionDto> availableOptionsToChoose) {
        this.option = option;
        this.availableOptionsToChoose = availableOptionsToChoose;
    }
}
