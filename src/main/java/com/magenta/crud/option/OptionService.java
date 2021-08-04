package com.magenta.crud.option;

import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.dto.AddRelativeToOption;
import com.magenta.crud.option.dto.DelRelativeFromOption;
import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;

import java.util.List;
import java.util.Set;

public interface OptionService {

    void createNewOption(NewOptionDto newOptionDto);

    void deleteExistOption(int id) throws DatabaseException, MyException;

    List<OptionDto> findAllOptions();

    OptionDto findOptionById(int id) throws DatabaseException;

    Set<Option> findSetOptionsById(List<Integer> optionsList);

    void updateOption(Option option);

    void setStatus(ChangeStatusDto statusDto) throws DatabaseException;

    List<OptionDto> findAvailableOptions(int id) throws DatabaseException;

    void addRelativeToOption(AddRelativeToOption addRelativeToOption) throws DatabaseException, MyException;

    void delRelativeToOption(DelRelativeFromOption delRelativeFromOption) throws DatabaseException, MyException;
}
