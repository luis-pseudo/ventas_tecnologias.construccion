package mx.uv.tcsw.config;

import mx.uv.tcsw.adapter.memory.InMemoryProductoRepository;
import mx.uv.tcsw.adapter.memory.InMemoryVentaRepository;
import mx.uv.tcsw.application.ProductoRepository;
import mx.uv.tcsw.application.port.in.RegistrarVentaUseCase;
import mx.uv.tcsw.application.port.out.VentaRepository;
import mx.uv.tcsw.application.service.RegistrarVentaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ProductoRepository productoRepository() {
        return new InMemoryProductoRepository();
    }

    @Bean
    public VentaRepository ventaRepository() {
        return new InMemoryVentaRepository();
    }

    @Bean
    public RegistrarVentaUseCase registrarVentaUseCase(VentaRepository ventas, ProductoRepository productos) {
        return new RegistrarVentaService(ventas, productos);
    }
}