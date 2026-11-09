package mx.uv.tcsw.adapter.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class VentaApiTest {
    @Autowired MockMvc mvc;

    private String crearProducto() throws Exception {
        String json = mvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Teclado\",\"precio\":450.00}"))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.id");
    }

    @Test
    void flujoCompletoCreaProductoYVenta() throws Exception {
        String productoId = crearProducto();

        String ventaJson = mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineas\":[{\"productoId\":\"" + productoId + "\",\"cantidad\":2}]}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.total").value(900.00))
            .andReturn().getResponse().getContentAsString();

        String ventaId = JsonPath.read(ventaJson, "$.id");
        mvc.perform(get("/api/v1/ventas/" + ventaId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.lineas[0].cantidad").value(2));
    }

    @Test
    void ventaConProductoInexistenteDevuelve404() throws Exception {
        mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineas\":[{\"productoId\":\"no-existe\",\"cantidad\":1}]}"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    void cantidadCeroDevuelve400() throws Exception {
        mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineas\":[{\"productoId\":\"x\",\"cantidad\":0}]}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.details[0].field").value("lineas[0].cantidad"));
    }

    @Test
    void ventaSinLineasDevuelve400() throws Exception {
        mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON).content("{\"lineas\":[]}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
