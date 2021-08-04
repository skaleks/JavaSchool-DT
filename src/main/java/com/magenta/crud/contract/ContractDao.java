package com.magenta.crud.contract;


import com.magenta.myexception.DatabaseException;

import java.util.List;

public interface ContractDao {

    void saveContract(Contract contract);
    void deleteContract(Contract contract) throws DatabaseException;
    List<Contract> findAllContracts();
    Contract findById(int id) throws DatabaseException;
    Contract findByNumber(String number) throws DatabaseException;
}
