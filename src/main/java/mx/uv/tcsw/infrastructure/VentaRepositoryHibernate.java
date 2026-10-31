package mx.uv.tcsw.infrastructure;

import mx.uv.tcsw.domain.Venta;
import mx.uv.tcsw.application.VentaRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class VentaRepositoryHibernate implements VentaRepository {
    private SessionFactory sessionFactory;

    public VentaRepositoryHibernate() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public void guardar(Venta venta) {
        // La persistencia se maneja desde el servicio para controlar la transacción completa
        throw new UnsupportedOperationException("Usar guardar con contexto transaccional");
    }
}