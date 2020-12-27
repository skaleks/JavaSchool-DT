package com.magenta.service;

import com.magenta.dao.OptionDao;
import com.magenta.model.Option;
import com.magenta.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("optionService")
@Transactional
public class OptionServiceImpl implements OptionService{

    OptionDao optionDao;

    @Autowired
    public void setOptionDao(OptionDao optionDao) {
        this.optionDao = optionDao;
    }

    @Override
    public void createNewOption(Option option) {
        optionDao.createNewOption(option);
    }

    @Override
    public void deleteExistOption(Option option) {
        optionDao.deleteExistOption(option);
    }

    @Override
    public List<Option> findAllOptions() {
        return optionDao.findAllOptions();
    }

    @Override
    public Option findOptionById(int id) throws MyException {
        return optionDao.findOptionById(id);
    }

    @Override
    public void updateOption(Option option) {
        optionDao.updateOption(option);
    }
}
