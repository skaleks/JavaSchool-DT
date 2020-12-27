package com.magenta.service;

import com.magenta.dao.ContractDao;
import com.magenta.model.Contract;
import com.magenta.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("contractService")
@Transactional
public class ContractServiceImpl implements ContractService {

    ContractDao contractDao;

    @Autowired
    public ContractServiceImpl(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Override
    public void saveContract(Contract contract) {
        contractDao.saveContract(contract);
    }

    @Override
    public void deleteContract(Contract contract) throws Exception {
        contractDao.deleteContract(contract);
    }

    @Override
    public List<Contract> findAllContracts() {
        return contractDao.findAllContracts();
    }

    @Override
    public Contract findById(int id) throws Exception {
        return contractDao.findById(id);
    }

    @Override
    public Contract findByNumber(String number) throws MyException {
        return contractDao.findByNumber(number);
    }
}
