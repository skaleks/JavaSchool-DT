package com.magenta.service;

import com.magenta.dao.TariffDao;
import com.magenta.model.Tariff;
import com.magenta.myexception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tariffService")
@Transactional
public class TariffServiceImpl implements TariffService {

    TariffDao tariffDao;

    @Autowired
    public void setTariffDao(TariffDao tariffDao) {
        this.tariffDao = tariffDao;
    }

    @Override
    public void createNewTariff(Tariff tariff) {
        tariffDao.createNewTariff(tariff);
    }

    @Override
    public void deleteExistTariff(Tariff tariff) {
        tariffDao.deleteExistTariff(tariff);
    }

    @Override
    public List<Tariff> findAllTariff(){
        return tariffDao.findAllTariff();
    }

    @Override
    public Tariff findTariffById(int id) throws MyException {
        return tariffDao.findTariffById(id);
    }

    @Override
    public void updateTariff(Tariff tariff) {
        tariffDao.updateTariff(tariff);
    }
}
