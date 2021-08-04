package com.magenta.crud.option;

import com.magenta.crud.AbstractDao;
import com.magenta.myexception.DatabaseException;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("optionDao")
public class OptionDaoImpl extends AbstractDao<Option> implements OptionDao {

    @Override
    public void createNewOption(Option option) {
        create(option);
    }
    @Override
    public void deleteExistOption(Option option) {
        delete(option);
    }
    @Override
    public List findAllOptions() {
        return getSession().createQuery("SELECT o FROM Option o").getResultList();
    }

    @Override
    public Set findSetOptionsById(List<Integer> optionsList) {
        List options = getSession()
                .createQuery("SELECT o FROM Option o WHERE o.id IN (:optionsList) ORDER BY o.id")
                .setParameter("optionsList",optionsList)
                .getResultList();
        return new HashSet(options);
    }

    @Override
    public Option findOptionById(int id) throws DatabaseException {
        return findById(id);
    }
    @Override
    public void updateOption(Option option) {
        update(option);
    }
}
