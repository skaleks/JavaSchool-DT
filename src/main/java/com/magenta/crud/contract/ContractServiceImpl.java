package com.magenta.crud.contract;

import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.EditContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.tariff.TariffDao;
import com.magenta.crud.tariff.dto.SwitchTariffDto;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.User;
import com.magenta.crud.user.UserDao;
import com.magenta.mapper.MyModelMapper;
import com.magenta.myexception.AuthorizationException;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import com.magenta.security.SecurityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    private static final Logger LOGGER = LogManager.getLogger(ContractServiceImpl.class);

    private final ContractDao contractDao;
    private final UserDao userDao;
    private final TariffDao tariffDao;
    private final OptionDao optionDao;
    private final MyModelMapper modelMapper;
    private final SecurityService securityService;

    @Autowired
    public ContractServiceImpl(ContractDao contractDao, UserDao userDao, TariffDao tariffDao, OptionDao optionDao, MyModelMapper modelMapper, SecurityService securityService) {
        this.contractDao = contractDao;
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.optionDao = optionDao;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
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
        List<Contract> baseList = contractDao.findAllContracts();
        return modelMapper.mapToList(baseList ,ContractDto.class);
    }

    @Override
    public ContractDto findById(int id) throws DatabaseException {
        Contract contract = contractDao.findById(id);
        return modelMapper.map(contract,ContractDto.class);
    }

    @Override
    public ContractDto findByNumber(String number) throws DatabaseException {
        return modelMapper.map(contractDao.findByNumber(number),ContractDto.class);
    }

    @Override
    public void setStatus(ChangeStatusDto statusDto) throws DatabaseException, AuthorizationException {
        Contract contract = contractDao.findById(statusDto.getEntityId());

        if (!securityService.isAdmin() && contract.getStatus().equals(Status.BLOCKED)){
            throw new AuthorizationException("Only administrator can do it");
        }

        Status newStatus = modelMapper.map(statusDto.getStatus(),Status.class);
        contract.setStatus(newStatus);
    }

    @Override
    public void switchTariff(SwitchTariffDto newTariff) throws DatabaseException, MyException {

        Contract contract = contractDao.findById(newTariff.getContractId());
        User user = userDao.findById(newTariff.getUserId());

        if (!user.getStatus().equals(Status.ACTIVE)){
            throw new MyException("User isn't active!");
        }

        if (contract.getStatus().equals(Status.BLOCKED)){
            throw new MyException("You can't do it. Contract blocked!");
        }
        Tariff tariff = tariffDao.findTariffById(newTariff.getTariffId());

        contract.getOptions().retainAll(tariff.getOptions());
        contract.setTariff(tariff);
    }

    @Override
    public void addOptionToContract(EditContractDto editContractDto) throws DatabaseException, MyException {
        Contract contract = contractDao.findById(editContractDto.getContractId());

        if (contract.getStatus().equals(Status.BLOCKED)){
            throw new MyException("Contract is blocked!");
        }

        Option option = optionDao.findOptionById(editContractDto.getOptionId());
        contract.getOptions().add(option);
    }

    @Override
    public void deleteOptionFromContract(EditContractDto editContractDto) throws DatabaseException, MyException {
        Contract contract = contractDao.findById(editContractDto.getContractId());

        if (contract.getStatus().equals(Status.BLOCKED)){
            throw new MyException("Contract is blocked!");
        }

        Option option = optionDao.findOptionById(editContractDto.getOptionId());
        contract.getOptions().remove(option);
    }

    private boolean isNumberExist(String number) throws DatabaseException {
        return (contractDao.findByNumber(number) != null);
    }
}
