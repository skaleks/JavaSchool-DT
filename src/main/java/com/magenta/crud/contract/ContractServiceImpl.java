package com.magenta.crud.contract;

import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.tariff.TariffDao;
import com.magenta.crud.type.Status;
import com.magenta.crud.user.UserDao;
import com.magenta.myexception.MyException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    ContractDao contractDao;
    UserDao userDao;
    TariffDao tariffDao;
    ModelMapper modelMapper;

    @Autowired
    public ContractServiceImpl(ContractDao contractDao, UserDao userDao, TariffDao tariffDao, ModelMapper modelMapper) {
        this.contractDao = contractDao;
        this.userDao = userDao;
        this.tariffDao = tariffDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveContract(NewContractDto newContractDto) throws MyException {
        Contract contract = new Contract();
        contract.setUser(userDao.findById(newContractDto.getUserId()));
        contract.setTariff(tariffDao.findTariffById(newContractDto.getTariffId()));
        contract.setStatus(Status.ACTIVE);
        contract.setPrice(contract.getTariff().getPrice());
        contract.setNumber(newContractDto.getNumber());
        contractDao.saveContract(contract);
    }

    @Override
    public void deleteContract(ContractDto contractDto) throws Exception {
        contractDao.deleteContract(modelMapper.map(contractDto,Contract.class));
    }

    @Override
    public List<ContractDto> findAllContracts() {
        List<ContractDto> resultList = new ArrayList<>();
        contractDao.findAllContracts().forEach(contract->resultList.add(modelMapper.map(contract,ContractDto.class)));
        return resultList;
    }

    @Override
    public ContractDto findById(int id) throws Exception {
        return modelMapper.map(contractDao.findById(id),ContractDto.class);
    }

    @Override
    public ContractDto findByNumber(String number) throws MyException {
        return modelMapper.map(contractDao.findByNumber(number),ContractDto.class);
    }
}
