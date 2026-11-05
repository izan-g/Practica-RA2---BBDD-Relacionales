package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.pool.ConnectionPool;

import java.sql.*;

public class ClienteDAOImpl implements ClienteDAO{
    @Override
    public ClienteEntity findByDni(String dni) {
        String sql = "SELECT * FROM clientes where dni = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){


            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    ClienteEntity clienteEntity = new ClienteEntity();
                    clienteEntity.setIdCliente(rs.getInt("id_cliente"));
                    clienteEntity.setDni(rs.getString("dni"));
                    clienteEntity.setNombre(rs.getString("nombre"));
                    clienteEntity.setPrimerApellido(rs.getString("p_apellido"));
                    clienteEntity.setSegundoApellido(rs.getString("s_apellido"));
                    clienteEntity.setEmail(rs.getString("email"));
                    clienteEntity.setTelefono(rs.getString("telefono"));
                    return clienteEntity;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ClienteEntity insert(ClienteEntity cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, p_apellido, s_apellido, email, telefono)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setString(5, cliente.getEmail());
            ps.setString(6, cliente.getTelefono());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0){
                try (ResultSet rs = ps.getGeneratedKeys()){
                    if (rs.next()){
                        int generatedId = rs.getInt(1);
                        cliente.setIdCliente(generatedId);
                    }

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }
}
