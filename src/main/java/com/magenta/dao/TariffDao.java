package com.magenta.dao;

import com.magenta.model.Option;
import com.magenta.model.Tariff;
import com.magenta.myexception.MyException;

import java.util.List;

public interface TariffDao {

    void createNewTariff(Tariff tariff);

    void deleteExistTariff(Tariff tariff);

    List<Tariff> findAllTariff();

    Tariff findTariffById(int id) throws MyException;

//    void addOption(Option option);

    void updateTariff(Tariff tariff);
}
