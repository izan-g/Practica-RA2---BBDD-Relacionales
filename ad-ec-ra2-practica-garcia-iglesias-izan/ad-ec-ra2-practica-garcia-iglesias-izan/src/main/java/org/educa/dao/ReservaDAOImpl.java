package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.entity.ReservaEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAOImpl implements ReservaDAO{
    @Override
    public List<ReservaEntity> findByClientId(Integer clientId) {
        List<ReservaEntity> reservaEntityList = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE id_cliente = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    ReservaEntity reserva = new ReservaEntity();
                    reserva.setIdReserva(rs.getInt("id_reserva"));
                    reserva.setCodReserva(rs.getString("cod_reserva"));
                    ClienteEntity client = new ClienteEntity();
                    client.setIdCliente(rs.getInt("id_cliente"));
                    reserva.setCliente(client);

                    if (rs.getTimestamp("fec_reserva") != null) {
                        reserva.setFecReserva(rs.getTimestamp("fec_reserva").toLocalDateTime());
                    }

                    reserva.setEstado(rs.getString("estado"));
                    reservaEntityList.add(reserva);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservaEntityList;
    }
}
