package com.magenta.crud.tariff;

import com.magenta.crud.global.dto.ChangeStatusDto;
import com.magenta.crud.option.dto.EditTariffOptionDto;
import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.DatabaseException;
import com.magenta.myexception.MyException;

import java.util.List;

public interface TariffService {

    void createNewTariff(NewTariffDto newTariffDto);

    void deleteExistTariff(int id) throws DatabaseException, MyException;

    List<TariffDto> findAllTariff();

    TariffDto findTariffById(int id) throws DatabaseException;

    void updateTariff(Tariff tariff);

    void setStatus(ChangeStatusDto statusDto) throws MyException, DatabaseException;

    void addOption(EditTariffOptionDto optionToTariff) throws DatabaseException, MyException;

    void delOption(EditTariffOptionDto optionToTariff) throws DatabaseException, MyException;
}
