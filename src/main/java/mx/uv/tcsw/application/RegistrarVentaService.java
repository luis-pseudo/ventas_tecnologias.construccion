package mx.uv.tcsw.application;

import mx.uv.tcsw.domain.Venta;
import mx.uv.tcsw.infrastructure.VentaRepositoryHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;

public class RegistrarVentaService implements RegistrarVentaUseCase {
    private VentaRepositoryHibernate repository;

    public RegistrarVentaService(VentaRepositoryHibernate repository) {
        this.repository = repository;
    }

    @Override
    public void ejecutar(String cliente, Object detalles) throws Exception {
        Session session = repository.getSession();
        Transaction transaction = null;
        
        try {
            transaction = session.beginTransaction();
            
            Venta nuevaVenta = new Venta(cliente, new Date());
            session.persist(nuevaVenta);
            
            // Simulación de fallo en los detalles para detonar el rollback si algo sale mal
            if (cliente == null || cliente.trim().isEmpty()) {
                throw new RuntimeException("Error en detalles de venta. Forzando rollback.");
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Transacción revertida: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}