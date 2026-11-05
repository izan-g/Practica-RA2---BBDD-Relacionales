package org.educa.dao;

import org.educa.entity.VueloEntity;

import java.util.List;

public interface VueloDAO {
    List<VueloEntity> findAll();
}
