package ua.epam.spring.hometask.service.impl;

import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by artyom on 03.06.17.
 */
public class AuditoriumServiceImpl implements AuditoriumService {

    private AuditoriumDao dao;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return dao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return dao.getByName(name);
    }

    public void setDao(AuditoriumDao dao) {
        this.dao = dao;
    }
}
