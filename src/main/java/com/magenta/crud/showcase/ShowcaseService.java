package com.magenta.crud.showcase;

import com.magenta.crud.showcase.dto.ShowcaseItemDto;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.myexception.DatabaseException;

import java.util.List;

public interface ShowcaseService {

    ShowcaseItem findItemById(int id) throws DatabaseException;
    List<ShowcaseItem> findAllItems();
    List<ShowcaseItem> findAllActiveItems();
    ShowcaseItem initTariff(TariffDto tariff, String description);
    List<ShowcaseItemDto> getItemDtoList();
}
