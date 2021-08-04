package com.magenta.crud.showcase;


import com.magenta.crud.showcase.dto.ShowcaseItemDto;
import com.magenta.crud.tariff.Tariff;
import com.magenta.crud.tariff.dto.TariffDto;
import com.magenta.crud.type.Status;
import com.magenta.mapper.MyModelMapper;
import com.magenta.myexception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ShowcaseService")
@Transactional
public class ShowcaseServiceImpl implements ShowcaseService{

    private final ShowcaseDao showcaseDao;
    private final MyModelMapper modelMapper;

    @Autowired
    public ShowcaseServiceImpl(ShowcaseDao showcaseDao, MyModelMapper modelMapper) {
        this.showcaseDao = showcaseDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShowcaseItem findItemById(int id) throws DatabaseException {
        return showcaseDao.findItemById(id);
    }

    @Override
    public List<ShowcaseItem> findAllItems() {
        return showcaseDao.findAllItems();
    }

    @Override
    public List<ShowcaseItem> findAllActiveItems() {
        return showcaseDao.findAllActiveItems();
    }

    @Override
    public ShowcaseItem initTariff(TariffDto tariff, String description) {
        ShowcaseItem showcaseItem = new ShowcaseItem(modelMapper.map(tariff, Tariff.class));
        showcaseItem.setDescription(description);
        showcaseItem.setStatus(Status.ACTIVE);
        return showcaseItem;
    }

    @Override
    public List<ShowcaseItemDto> getItemDtoList(){
        return modelMapper.mapToList(findAllActiveItems(),ShowcaseItemDto.class);
    }
}
