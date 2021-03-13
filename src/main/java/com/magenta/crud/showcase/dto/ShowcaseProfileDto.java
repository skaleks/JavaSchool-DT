package com.magenta.crud.showcase.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ShowcaseProfileDto implements Serializable {

    private List<ShowcaseItemDto> tariffList;

    public ShowcaseProfileDto(List<ShowcaseItemDto> tariffList) {
        this.tariffList = tariffList;
    }
}
