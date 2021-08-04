package com.magenta.crud.option.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class AllOptionsDto implements Serializable {

    List<OptionDto> optionDtoList;
}
