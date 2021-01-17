package com.magenta.crud.tariff;

import com.magenta.crud.AbstractDao;
import com.magenta.myexception.MyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("tariffDao")
public class TariffDaoImpl extends AbstractDao<Tariff> implements TariffDao {


    @Override
    public void createNewTariff(Tariff tariff) {
        create(tariff);
    }

    @Override
    public void deleteExistTariff(Tariff tariff) {
        delete(tariff);
    }

    @Override
    public List findAllTariff(){
        return getSession().createQuery("SELECT t FROM Tariff t").getResultList();
    }

    @Override
    public Tariff findTariffById(int id) throws MyException {
        return findById(id);
    }

//    @Override
//    public void addOption(Option option) {
//    }

    @Override
    public void updateTariff(Tariff tariff) {
        update(tariff);
    }
}
