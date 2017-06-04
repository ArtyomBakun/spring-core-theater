package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by artyom on 03.06.17.
 */
public interface AuditoriumDao {

    @Nonnull
    Set<Auditorium> getAll();

    @Nullable
    Auditorium getByName(@Nonnull String name);
}
