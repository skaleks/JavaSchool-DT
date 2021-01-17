package com.magenta.crud.option.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllOptionsDto implements Serializable {

    List<OptionDto> optionDtoList;
}
