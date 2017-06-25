package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * Created by artyom on 03.06.17.
 */
public interface UserDao extends AbstractDao<User> {
    @Nullable
    User getUserByEmail(@Nonnull String email);
}
