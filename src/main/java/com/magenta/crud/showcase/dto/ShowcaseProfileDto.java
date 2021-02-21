package com.magenta.crud.showcase.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ShowcaseProfileDto {

    private List<ShowcaseItemDto> tariffList;

    public ShowcaseProfileDto(List<ShowcaseItemDto> tariffList) {
        this.tariffList = tariffList;
    }
}
