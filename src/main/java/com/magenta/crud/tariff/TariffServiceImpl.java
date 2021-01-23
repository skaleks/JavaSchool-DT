package com.magenta.crud.tariff;

import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.myexception.DatabaseException;
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
            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        });
        LOGGER.warning("Size: " + result.size());
        tariff.setOptions(result);
        tariffDao.createNewTariff(tariff);
    }

    @Override
    public void deleteExistTariff(int id) throws DatabaseException {
        Tariff tariff = tariffDao.findTariffById(id);
        tariffDao.deleteExistTariff(tariff);
    }

    @Override
    public List<TariffDto> findAllTariff(){
        List<TariffDto> resultList = new ArrayList<>();
        List<Tariff> tariffList = tariffDao.findAllTariff();
        tariffList.forEach(tariff -> resultList.add(mapToTariffDto(tariff)));
        return resultList;
    }

    @Override
    public TariffDto findTariffById(int id) throws DatabaseException {
        Tariff tariff = tariffDao.findTariffById(id);
        return mapToTariffDto(tariff);
    }

    @Override
    public void updateTariff(Tariff tariff) {
        tariffDao.updateTariff(tariff);
    }

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws DatabaseException {

        Tariff tariff = tariffDao.findTariffById(statusDto.getEntityId());
        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        tariff.setStatus(newStatus);
    }

    private Set<OptionDto> toOptionDtoSet(Set<Option> baseList){
        Set<OptionDto> resultList = new HashSet<>();
        baseList.forEach(option -> resultList.add(modelMapper.map(option,OptionDto.class)));
        return resultList;
    }

    private TariffDto mapToTariffDto(Tariff tariff){
        TariffDto tariffDto = modelMapper.map(tariff, TariffDto.class);
        tariffDto.setAvailableOptions(toOptionDtoSet(tariff.getOptions()));
        return tariffDto;
    }
}
