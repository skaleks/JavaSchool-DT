package com.magenta.crud.contract;

import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.option.dto.OptionDto;
import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.tariff.TariffDao;
import com.magenta.crud.tariff.dto.SwitchTariffDto;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.UserDao;
import com.magenta.myexception.DatabaseException;
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

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    private static final Logger LOGGER = Logger.getLogger("ContractService");

    private final ContractDao contractDao;
    private final UserDao userDao;
    private final TariffDao tariffDao;
    private final OptionDao optionDao;
    private final ModelMapper modelMapper;

    @Autowired
    public ContractServiceImpl(ContractDao contractDao, UserDao userDao, TariffDao tariffDao, OptionDao optionDao, ModelMapper modelMapper) {
        this.contractDao = contractDao;
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.optionDao = optionDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveContract(NewContractDto newContractDto) throws MyException, DatabaseException {

        if (isNumberExist(newContractDto.getNumber())) {
            throw new MyException("Contract with that phone number already exists.");
        }

        Contract contract = new Contract();
        contract.setNumber(newContractDto.getNumber());
        contract.setUser(userDao.findById(newContractDto.getUserId()));

        Tariff tariff = tariffDao.findTariffById(newContractDto.getTariffId());
        contract.setTariff(tariff);

        Set<Option> options = new HashSet<>(optionDao.findSetOptionsById(newContractDto.getOptionsId()));
        contract.setOptions(options);
        contract.setStatus(Status.ACTIVE);

        Double price = tariff.getPrice();
        for (Option option: contract.getOptions()) {
            price += option.getPrice();
        }
        contract.setPrice(price);
        contractDao.saveContract(contract);
    }

    @Override
    public void deleteContract(int id) throws DatabaseException {
        Contract contract = contractDao.findById(id);
        contractDao.deleteContract(contract);
    }

    @Override
    public List<ContractDto> findAllContracts() {
        List<ContractDto> resultList = new ArrayList<>();
        contractDao.findAllContracts().forEach(contract->resultList.add(modelMapper.map(contract,ContractDto.class)));
        return resultList;
    }

    @Override
    public ContractDto findById(int id) throws DatabaseException {
        Contract contract = contractDao.findById(id);
        return mapToContractDto(contract);
    }

    @Override
    public ContractDto findByNumber(String number) throws DatabaseException {
        return modelMapper.map(contractDao.findByNumber(number),ContractDto.class);
    }

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws DatabaseException {

        Contract contract = contractDao.findById(statusDto.getEntityId());
        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        contract.setStatus(newStatus);
    }

    @Override
    public void switchTariff(SwitchTariffDto newTariff) throws DatabaseException, MyException {

        Contract contract = contractDao.findById(newTariff.getContractId());
        if (contract.getStatus().equals(Status.BLOCKED)){
            throw new MyException("You can't do it. Contract blocked!");
        }
        Tariff tariff = tariffDao.findTariffById(newTariff.getTariffId());

        contract.setTariff(tariff);
    }

    private boolean isNumberExist(String number) throws DatabaseException {
        return (contractDao.findByNumber(number) != null);
    }

    private Set<OptionDto> toOptionDtoSet(Set<Option> baseList){
        Set<OptionDto> resultList = new HashSet<>();
        baseList.forEach(option -> resultList.add(modelMapper.map(option,OptionDto.class)));
        return resultList;
    }

    private ContractDto mapToContractDto(Contract contract){
        ContractDto contractDto = modelMapper.map(contract, ContractDto.class);
        contractDto.setOptions(toOptionDtoSet(contract.getOptions()));
        return contractDto;
    }
}
