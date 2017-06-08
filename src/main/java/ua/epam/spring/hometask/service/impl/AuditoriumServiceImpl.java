package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * Created by artyom on 03.06.17.
 */
@Component
public class AuditoriumServiceImpl implements AuditoriumService {

    @Autowired
    private AuditoriumDao dao;

    public AuditoriumServiceImpl() {
    }

    public AuditoriumServiceImpl(AuditoriumDao dao)
    {
        this.dao = dao;
    }

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
