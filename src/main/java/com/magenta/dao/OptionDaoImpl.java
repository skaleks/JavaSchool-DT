package com.magenta.dao;

import com.magenta.model.Option;
import com.magenta.myexception.MyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("optionDao")
public class OptionDaoImpl extends AbstractDao<Option> implements OptionDao{

    @Override
    public void createNewOption(Option option) {
        create(option);
    }
    @Override
    public void deleteExistOption(Option option) {
        delete(option);
    }
    @Override
    public List findAllOptions() {
        return getSession().createQuery("SELECT o FROM Option o").getResultList();
    }
    @Override
    public Option findOptionById(int id) throws MyException {
        return findById(id);
    }
    @Override
    public void updateOption(Option option) {
        update(option);
    }
}
