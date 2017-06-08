package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by artyom on 03.06.17.
 */
@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return dao.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User object) {
        return dao.save(object);
    }

    @Override
    public void remove(@Nonnull User object) {
        dao.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return dao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return dao.getAll();
    }

    public void setDao(UserDao dao) {
        this.dao = dao;
    }
}
