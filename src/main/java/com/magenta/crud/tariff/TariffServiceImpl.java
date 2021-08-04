package com.magenta.crud.tariff;

import com.magenta.checker.RuleCheckerForOptions;
import com.magenta.crud.contract.Contract;
import com.magenta.crud.contract.ContractDao;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.option.dto.EditTariffOptionDto;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.mapper.MyModelMapper;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {

    private static final Logger LOGGER = LogManager.getLogger(TariffServiceImpl.class);

    private final TariffDao tariffDao;
    private final OptionDao optionDao;
    private final ContractDao contractDao;
    private final MyModelMapper modelMapper;
    private final RuleCheckerForOptions checker;

    @Autowired
    public TariffServiceImpl(TariffDao tariffDao, OptionDao optionDao, ContractDao contractDao, MyModelMapper modelMapper, RuleCheckerForOptions checker) {
        this.tariffDao = tariffDao;
        this.optionDao = optionDao;
        this.contractDao = contractDao;
        this.modelMapper = modelMapper;
        this.checker = checker;
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
        tariff.setOptions(result);
        tariffDao.createNewTariff(tariff);
    }

    @Override
    public void deleteExistTariff(int id) throws DatabaseException, MyException {
        Tariff tariff = tariffDao.findTariffById(id);
        for (Contract contract : contractDao.findAllContracts()) {
            if (contract.getTariff().equals(tariff)) {
                throw new MyException("One or more contracts are still using this tariff");
            }
        }
        tariffDao.deleteExistTariff(tariff);
    }

    @Override
    public List<TariffDto> findAllTariff(){
        List<Tariff> baseList = tariffDao.findAllTariff();
        return modelMapper.mapToList(baseList,TariffDto.class);
    }

    @Override
    public TariffDto findTariffById(int id) throws DatabaseException {
        Tariff tariff = tariffDao.findTariffById(id);
        return modelMapper.map(tariff, TariffDto.class);
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

    @Override
    public void addOption(EditTariffOptionDto optionToTariff) throws DatabaseException, MyException {
        Option option = optionDao.findOptionById(optionToTariff.getOptionId());
        Tariff tariff = tariffDao.findTariffById(optionToTariff.getTariffId());

        if (tariff.getOptions().contains(option)){
            throw new MyException("Tariff already has this option");
        }
        tariff.getOptions().add(option);
    }

    @Override
    public void delOption(EditTariffOptionDto optionFromTariff) throws DatabaseException, MyException {

        Option option = optionDao.findOptionById(optionFromTariff.getOptionId());
        Tariff tariff = tariffDao.findTariffById(optionFromTariff.getTariffId());

        checker.checkIfStillUsed(option);

        tariff.getOptions().remove(option);
    }

}
