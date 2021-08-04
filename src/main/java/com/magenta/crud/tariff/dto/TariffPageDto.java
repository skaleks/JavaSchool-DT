package com.magenta.crud.tariff.dto;


import com.magenta.crud.option.dto.OptionDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class TariffPageDto {

    private TariffDto tariff;
    private Set<OptionDto> options;

    public TariffPageDto(TariffDto tariff, Set<OptionDto> options) {
        this.tariff = tariff;
        this.options = options;
    }
}
