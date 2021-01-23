package com.magenta.crud.contract;

import com.magenta.crud.contract.dto.ContractDto;
import com.magenta.crud.contract.dto.NewContractDto;
import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;

import java.util.List;

public interface ContractService {

    void saveContract(NewContractDto newContractDto) throws MyException, DatabaseException;

    void deleteContract(int id) throws DatabaseException;

    List<ContractDto> findAllContracts();

    ContractDto findById(int id) throws DatabaseException;

    ContractDto findByNumber(String number) throws DatabaseException;

    void setStatus(ChangeStatusDto statusDto) throws DatabaseException;
}
