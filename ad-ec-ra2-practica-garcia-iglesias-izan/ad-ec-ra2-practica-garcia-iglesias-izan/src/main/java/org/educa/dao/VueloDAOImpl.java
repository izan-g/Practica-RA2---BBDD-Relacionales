package org.educa.dao;

import org.educa.entity.AeropuertoEntity;
import org.educa.entity.VueloEntity;
import org.educa.pool.ConnectionPool;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VueloDAOImpl implements VueloDAO{
    @Override
    public List<VueloEntity> findAll() {
        List<VueloEntity> vueloEntityList = new ArrayList<>();
        String sql = "SELECT * FROM vuelos WHERE id_origen = ? AND estado != 'CANCELADO'";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, idOrigen);

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    VueloEntity vueloEntity = new VueloEntity();
                    vueloEntity.setIdVuelo(rs.getInt("id_vuelo"));
                    vueloEntity.setNumVuelo(rs.getString("num_vuelo"));
                    AeropuertoEntity aeropuertoEntity = new AeropuertoEntity();
                    vueloEntity.setIdOrigen(rs.getInt("id_origen"));
                    vueloEntity.setOrigen(aeropuertoEntity.get);
                    vueloEntity.setIdDestino(rs.getInt("id_destino"));
                    vueloEntity.setIdAvion(rs.getInt("id_avion"));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ;
    }
}
