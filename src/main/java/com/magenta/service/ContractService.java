package com.magenta.service;

import com.magenta.model.Contract;
import com.magenta.myexception.MyException;

import java.util.List;

public interface ContractService {

    void saveContract(Contract contract);
    void deleteContract(Contract contract) throws Exception;
    List<Contract> findAllContracts();
    Contract findById(int id) throws Exception;
    Contract findByNumber(String number) throws MyException;
}
