package mx.uv.tcsw.adapter.persistence.postgres;

import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.domain.Venta;

import java.sql.*;
import java.util.Optional;

public class VentaRepositoryJdbc implements VentaRepository {
    private final String url = "jdbc:postgresql://localhost:5432/ventas_db";
    private final String user = "postgres";
    private final String password = "secretpassword";

    @Override
    public void guardar(Venta venta) {
        String sql = "INSERT INTO ventas (id) VALUES (?) ON CONFLICT (id) DO NOTHING";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, venta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la venta en PostgreSQL", e);
        }
    }

    @Override
    public Optional<Venta> buscar(String id) {
        String sql = "SELECT id FROM ventas WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Venta(rs.getString("id")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la venta en PostgreSQL", e);
        }
        return Optional.empty();
    }
}

