package com.magenta.checker;

import com.magenta.crud.contract.Contract;
import com.magenta.crud.contract.ContractDao;
import com.magenta.crud.option.Option;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.tariff.TariffDao;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleCheckerForOptions {

    private final OptionDao optionDao;
    private final TariffDao tariffDao;
    private final ContractDao contractDao;

    @Autowired
    public RuleCheckerForOptions(OptionDao optionDao, TariffDao tariffDao, ContractDao contractDao) {
        this.optionDao = optionDao;
        this.tariffDao = tariffDao;
        this.contractDao = contractDao;
    }

    public List<Option> checkRuleAndGetResult(List<Option> checked, int id) throws DatabaseException {
        Option mainOption = optionDao.findOptionById(id);

        deleteIfExcluded(checked, id);
        deleteIfRelated(checked, id);
        deleteIfLead(checked,id);

        checked.remove(mainOption);
        return checked;
    }

    private void deleteIfRelated(List<Option> checked, int id) throws DatabaseException {
        Option mainOption = optionDao.findOptionById(id);
        checked.removeIf(option -> mainOption.getRelatedOptions().contains(option));
    }

    public void deleteIfExcluded(List<Option> checked, int id) throws DatabaseException {
        Option mainOption = optionDao.findOptionById(id);
        checked.removeIf(option -> mainOption.getExcludedOptions().contains(option));
    }

    public void deleteIfLead(List<Option> checked, int id) throws DatabaseException {
        Option mainOption = optionDao.findOptionById(id);
        checked.removeIf(option -> mainOption.getLeadOptions().contains(option));
    }

    public void checkIfStillUsed(Option deletedOption) throws MyException {

        usedByTariff(deletedOption);
        usedByContract(deletedOption);
    }

    private void usedByContract(Option deletedOption) throws MyException {

        List<Contract> contractList = contractDao.findAllContracts();
        for (Contract contract : contractList) {
            if (contract.getOptions().contains(deletedOption)){
                throw new MyException("One or more contracts still using this option");
            }
        }
    }

    private void usedByTariff(Option deletedOption) throws MyException {

        List<Tariff> tariffList = tariffDao.findAllTariff();
        for (Tariff tariff : tariffList) {
            if (tariff.getOptions().contains(deletedOption)){
                throw new MyException("One or more tariffs still using this option");
            }
        }
    }


}
