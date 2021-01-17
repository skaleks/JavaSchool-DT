package com.magenta.crud.option;

import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.myexception.MyException;

import java.util.List;

public interface OptionService {

    void createNewOption(NewOptionDto newOptionDto);

    void deleteExistOption(OptionDto optionDto);

    List<OptionDto> findAllOptions();

    OptionDto findOptionById(int id) throws MyException;

    void updateOption(Option option);
}
