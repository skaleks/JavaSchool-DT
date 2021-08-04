package com.magenta.crud.global.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ResponseDto implements Serializable {

    private String response;

    public ResponseDto(String response){
        this.response = response;
    }
}