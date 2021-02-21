package com.magenta.crud.showcase.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowcaseItemDto {

    private String tariffName;
    private String tariffPrice;
    private String tariffDescription;
    private String showcaseDescription;
}
