package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AeropuertoDAOImpl implements AeropuertoDAO{
    @Override
    public List<AeropuertoEntity> findAll() {
        List<AeropuertoEntity> aeropuertoEntityList = new ArrayList<>();
        String sql = "SELECT * FROM aeropuertos ORDER BY id_aeropuerto";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                AeropuertoEntity aeropuertoEntity = new AeropuertoEntity();
                aeropuertoEntity.setIdAeropuerto(rs.getInt("id_aeropuerto"));
                aeropuertoEntity.setCodIata(rs.getString("cod_iata"));
                aeropuertoEntity.setNombre(rs.getString("nombre"));
                aeropuertoEntity.setCiudad(rs.getString("ciudad"));
                aeropuertoEntity.setPais(rs.getString("pais"));

                aeropuertoEntityList.add(aeropuertoEntity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return aeropuertoEntityList;
    }
}
