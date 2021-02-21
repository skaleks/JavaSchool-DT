package com.magenta.crud.showcase;

import com.magenta.myexception.DatabaseException;

import java.util.List;


public interface ShowcaseDao {

    ShowcaseItem findItemById(int id) throws DatabaseException;
    List<ShowcaseItem> findAllItems();
    List<ShowcaseItem> findAllActiveItems();
}
