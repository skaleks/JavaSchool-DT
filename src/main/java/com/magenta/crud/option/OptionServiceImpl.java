package com.magenta.crud.option;

import com.magenta.crud.option.dto.NewOptionDto;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.type.Status;
import com.magenta.myexception.MyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("optionService")
@Transactional
public class OptionServiceImpl implements OptionService {

    OptionDao optionDao;
    ModelMapper modelMapper;

    @Autowired
    public void setOptionDao(OptionDao optionDao, ModelMapper modelMapper) {
        this.optionDao = optionDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createNewOption(NewOptionDto newOptionDto) {
        Option option = modelMapper.map(newOptionDto, Option.class);
        option.setStatus(Status.ACTIVE);
        optionDao.createNewOption(option);
    }

    @Override
    public void deleteExistOption(OptionDto optionDto) {
        optionDao.deleteExistOption(modelMapper.map(optionDto,Option.class));
    }

    @Override
    public List<OptionDto> findAllOptions() {
        List<OptionDto> resultList = new ArrayList<>();
        optionDao.findAllOptions().forEach(option->resultList.add(modelMapper.map(option, OptionDto.class)));
        return resultList;
    }

    @Override
    public OptionDto findOptionById(int id) throws MyException {
        return modelMapper.map(optionDao.findOptionById(id),OptionDto.class);
    }

    @Override
    public void updateOption(Option option) {
        optionDao.updateOption(option);
    }
}
