package com.magenta.crud.contract;

import com.magenta.crud.AbstractDao;
import com.magenta.myexception.DatabaseException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDao<Contract> implements ContractDao {

    @Override
    public void saveContract(Contract contract) {
        create(contract);
    }

    @Override
    public void deleteContract(Contract contract) throws DatabaseException {
        delete(findById(contract.getId()));
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Contract> findAllContracts() {
        return (List<Contract>) getSession()
                .createQuery("SELECT c FROM Contract c")
                .getResultList();
    }

    @Override
    public Contract findById(int id) throws DatabaseException {
        return super.findById(id);
    }

    @Override
    public Contract findByNumber(String number) throws DatabaseException {
        List resultList = getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.number = :number")
                .setParameter("number",number)
                .getResultList();
        if (resultList.isEmpty()) return null;
        return (Contract) resultList.get(0);
    }
}
