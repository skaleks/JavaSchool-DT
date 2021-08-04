package com.magenta.crud.showcase;

import com.magenta.crud.AbstractDao;
import com.magenta.crud.type.Status;
import com.magenta.myexception.DatabaseException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ShowcaseDao")
public class ShowcaseDaoImpl extends AbstractDao<ShowcaseItem> implements ShowcaseDao {


    @Override
    public ShowcaseItem findItemById(int id) throws DatabaseException {
        return findById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ShowcaseItem> findAllItems() {
        return (List<ShowcaseItem>) getSession()
                .createQuery("SELECT s FROM ShowcaseItem s")
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ShowcaseItem> findAllActiveItems() {
        return getSession()
                .createQuery("SELECT s FROM ShowcaseItem s WHERE s.status LIKE :status")
                .setParameter("status", Status.ACTIVE)
                .getResultList();
    }
}
