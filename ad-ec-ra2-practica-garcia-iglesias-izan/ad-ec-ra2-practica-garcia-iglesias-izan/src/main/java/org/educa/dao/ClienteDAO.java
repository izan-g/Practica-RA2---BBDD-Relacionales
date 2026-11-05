package org.educa.dao;

import org.educa.entity.ClienteEntity;

public interface ClienteDAO {

    ClienteEntity findByDni(String dni);

    ClienteEntity insert(ClienteEntity cliente);
}
