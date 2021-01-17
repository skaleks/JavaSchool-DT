package com.magenta.crud.contract;

import com.magenta.crud.AbstractDao;
import com.magenta.myexception.MyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("contractDao")
public class ContractDaoImpl extends AbstractDao<Contract> implements ContractDao {

    @Override
    public void saveContract(Contract contract) {
        create(contract);
    }

    @Override
    public void deleteContract(Contract contract) throws Exception {
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
    public Contract findById(int id) throws MyException {
        return super.findById(id);
    }

    @Override
    public Contract findByNumber(String number) throws MyException {
        List resultList = getSession()
                .createQuery("SELECT c FROM Contract c WHERE c.number = :number")
                .setParameter("number",number)
                .getResultList();
        if (resultList.isEmpty()) throw new MyException("Number isn't exist");
        Contract contract = (Contract) resultList.get(0);
        return contract;
    }
}
