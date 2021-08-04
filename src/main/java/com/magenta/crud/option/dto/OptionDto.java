package com.magenta.crud.option.dto;

import com.magenta.crud.option.Option;
import com.magenta.crud.type.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class OptionDto implements Serializable {

    private int id;
    private String name;
    private String optionDescription;
    private double price;
    private double addCost;
    private Status status;
    private Set<Option> relatedOptions;
    private Set<Option> excludedOptions;
    private Set<Option> leadOptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionDto optionDto = (OptionDto) o;
        return id == optionDto.id && Double.compare(optionDto.price, price) == 0 && Double.compare(optionDto.addCost, addCost) == 0 && Objects.equals(name, optionDto.name) && Objects.equals(optionDescription, optionDto.optionDescription) && status == optionDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, optionDescription, price, addCost, status);
    }
}
