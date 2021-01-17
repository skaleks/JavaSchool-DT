package com.magenta.crud.tariff;

import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.myexception.MyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {

    private static final Logger LOGGER = Logger.getLogger("Info");

    TariffDao tariffDao;
    OptionDao optionDao;
    ModelMapper modelMapper;


    @Autowired
    public void setTariffDao(TariffDao tariffDao, ModelMapper modelMapper, OptionDao optionDao) {
        this.tariffDao = tariffDao;
        this.modelMapper = modelMapper;
        this.optionDao = optionDao;
    }

    @Override
    public void createNewTariff(NewTariffDto newTariffDto) {
        Tariff tariff = modelMapper.map(newTariffDto,Tariff.class);
        tariff.setStatus(Status.ACTIVE);
        Set<Option> result = new HashSet<>();
        newTariffDto.getAvailableOptionsForThisTariff().forEach(optionId -> {
            try {
                result.add(optionDao.findOptionById(optionId));
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        LOGGER.warning("Size: " + result.size());
        tariff.setOptions(result);
        tariffDao.createNewTariff(tariff);
    }

    @Override
    public void deleteExistTariff(TariffDto tariffDto) {
        tariffDao.deleteExistTariff(modelMapper.map(tariffDto,Tariff.class));
    }

    @Override
    public List<TariffDto> findAllTariff(){
        List<TariffDto> resultList = new ArrayList<>();
        tariffDao.findAllTariff().forEach(tariff->resultList.add(modelMapper.map(tariff,TariffDto.class)));
        return resultList;
    }

    @Override
    public TariffDto findTariffById(int id) throws MyException {
        return modelMapper.map(tariffDao.findTariffById(id),TariffDto.class);
    }

    @Override
    public void updateTariff(Tariff tariff) {
        tariffDao.updateTariff(tariff);
    }
}