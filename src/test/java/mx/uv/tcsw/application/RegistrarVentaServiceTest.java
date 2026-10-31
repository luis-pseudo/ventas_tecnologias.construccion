package mx.uv.tcsw.application;

import mx.uv.tcsw.infrastructure.VentaRepositoryHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrarVentaServiceTest {
    private VentaRepositoryHibernate repositoryMock;
    private Session sessionMock;
    private Transaction transactionMock;
    private RegistrarVentaService service;

    @BeforeEach
    void setUp() {
        repositoryMock = mock(VentaRepositoryHibernate.class);
        sessionMock = mock(Session.class);
        transactionMock = mock(Transaction.class);

        when(repositoryMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.beginTransaction()).thenReturn(transactionMock);

        service = new RegistrarVentaService(repositoryMock);
    }

    @Test
    void testEjecutarVentaExitosaRealizaCommit() throws Exception {
        service.ejecutar("Cliente Válido", null);

        verify(sessionMock, times(1)).persist(any());
        verify(transactionMock, times(1)).commit();
        verify(sessionMock, times(1)).close();
    }

    @Test
    void testEjecutarVentaFallidaGeneraRollback() {
        Exception exception = assertThrows(Exception.class, () -> {
            service.ejecutar("", null);
        });

        verify(transactionMock, times(1)).rollback();
        verify(sessionMock, times(1)).close();
        assertTrue(exception.getMessage().contains("Transacción revertida"));
    }
}