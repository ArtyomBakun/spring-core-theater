package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by artyom on 03.06.17.
 */
public class UserDaoImpl implements UserDao {
    
    private static List<User> users = new ArrayList<>();

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User save(@Nonnull User object) {
        return users.add(object) ? object : null;
    }

    @Override
    public void remove(@Nonnull User object) {
        users.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return users;
    }

    public static void setUsers(List<User> users) {
        UserDaoImpl.users = users;
    }

}
