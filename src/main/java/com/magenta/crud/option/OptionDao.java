package com.magenta.crud.option;

import com.magenta.myexception.MyException;

import java.util.List;

public interface OptionDao {

    void createNewOption(Option option);

    void deleteExistOption(Option option);

    List<Option> findAllOptions();

    Option findOptionById(int id) throws MyException;

    void updateOption(Option option);
}
