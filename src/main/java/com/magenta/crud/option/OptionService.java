package com.magenta.crud.option;

import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.myexception.DatabaseException;

import java.util.List;
import java.util.Set;

public interface OptionService {

    void createNewOption(NewOptionDto newOptionDto);

    void deleteExistOption(int id) throws DatabaseException;

    List<OptionDto> findAllOptions();

    OptionDto findOptionById(int id) throws DatabaseException;

    Set<Option> findSetOptionsById(List<Integer> optionsList);

    void updateOption(Option option);

    void setStatus(ChangeStatusDto statusDto) throws DatabaseException;
}
