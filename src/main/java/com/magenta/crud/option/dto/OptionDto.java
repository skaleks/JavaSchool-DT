package com.magenta.crud.option.dto;
import com.magenta.crud.option.Option;
import com.magenta.crud.type.Status;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
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
}
