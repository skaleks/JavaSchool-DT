package com.magenta.crud.tariff;

import com.magenta.crud.tariff.dto.NewTariffDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.MyException;

import java.util.List;

public interface TariffService {

    void createNewTariff(NewTariffDto newTariffDto);

    void deleteExistTariff(TariffDto tariffDto);

    List<TariffDto> findAllTariff();

    TariffDto findTariffById(int id) throws MyException;

//    void addOption(Option option);

    void updateTariff(Tariff tariff);
}
