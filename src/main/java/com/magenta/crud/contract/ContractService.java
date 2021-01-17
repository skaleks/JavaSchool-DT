package com.magenta.crud.contract;

import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.myexception.MyException;

import java.util.List;

public interface ContractService {

    void saveContract(NewContractDto newContractDto) throws MyException;
    void deleteContract(ContractDto contractDto) throws Exception;
    List<ContractDto> findAllContracts();
    ContractDto findById(int id) throws Exception;
    ContractDto findByNumber(String number) throws MyException;
}
