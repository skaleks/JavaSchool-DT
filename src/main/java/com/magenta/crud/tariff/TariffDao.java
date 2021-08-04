package com.magenta.crud.tariff;

import com.magenta.myexception.DatabaseException;
import java.util.List;

public interface TariffDao {

    void createNewTariff(Tariff tariff);

    void deleteExistTariff(Tariff tariff);

    List<Tariff> findAllTariff();

    Tariff findTariffById(int id) throws DatabaseException;

    void updateTariff(Tariff tariff);
}
