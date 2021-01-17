package com.magenta.crud.user;


import com.magenta.crud.AbstractDao;
import com.magenta.myexception.MyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    @Override
    public void save(User user) {
        create(user);
    }

    @Override
    public User findById(int id) throws MyException {
        User user = super.findById(id);
        if (user == null) throw new MyException("User isn't exist");
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAllUsers() {
        return (List<User>) getSession()
                .createQuery("SELECT c FROM User c")
                .getResultList();
    }

    @Override
    public void delete(int id) {
        List clients = getSession()
                .createQuery("FROM User c WHERE c.id = :id")
                .setParameter("id",id)
                .getResultList();
        delete((User) clients.get(0));
    }

    @Override
    public void update(User user){
        super.update(user);
    }

}