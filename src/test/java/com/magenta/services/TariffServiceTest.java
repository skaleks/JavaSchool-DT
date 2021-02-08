package com.magenta.services;

import com.magenta.crud.contract.ContractDao;
import com.magenta.crud.option.OptionDao;
import com.magenta.crud.tariff.TariffDao;
import com.magenta.crud.tariff.TariffServiceImpl;
import com.magenta.mapper.MyModelMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TariffServiceTest {

    @Mock
    TariffDao tariffDao;
    @Mock
    OptionDao optionDao;
    @Mock
    ContractDao contractDao;
    @Mock
    MyModelMapper modelMapper;
    @InjectMocks
    TariffServiceImpl tariffService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void createNewTariff() {

    }

    @Test
    void deleteExistTariff() {

    }

    @Test
    void findAllTariff() {
    }

    @Test
    void findTariffById() {
    }

    @Test
    void updateTariff() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void addOption() {
    }

    @Test
    void delOption() {
    }
}