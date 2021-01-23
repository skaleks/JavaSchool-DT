package com.magenta.crud.option;

import com.magenta.myexception.DatabaseException;

import java.util.List;
import java.util.Set;

public interface OptionDao {

    void createNewOption(Option option);

    void deleteExistOption(Option option);

    List<Option> findAllOptions();

    Set<Option> findSetOptionsById(List<Integer> optionsList);

    Option findOptionById(int id) throws DatabaseException;

    void updateOption(Option option);
}
