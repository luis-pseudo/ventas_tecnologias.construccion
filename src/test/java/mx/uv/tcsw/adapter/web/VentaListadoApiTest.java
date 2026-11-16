package mx.uv.tcsw.adapter.web;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** P10: el cliente Angular necesita listar las ventas registradas (GET /api/v1/ventas). */
@SpringBootTest
@AutoConfigureMockMvc
class VentaListadoApiTest {
    @Autowired MockMvc mvc;

    private String crearProducto() throws Exception {
        String json = mvc.perform(post("/api/v1/productos").contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Monitor\",\"precio\":3200.00}"))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();
        return JsonPath.read(json, "$.id");
    }

    @Test
    void listarDevuelve200ConUnArreglo() throws Exception {
        mvc.perform(get("/api/v1/ventas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void listarIncluyeLaVentaRegistrada() throws Exception {
        String productoId = crearProducto();
        String ventaJson = mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineas\":[{\"productoId\":\"" + productoId + "\",\"cantidad\":3}]}"))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();
        String ventaId = JsonPath.read(ventaJson, "$.id");

        mvc.perform(get("/api/v1/ventas"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].id", hasItem(ventaId)));
    }

    private int totalVentas() throws Exception {
        String json = mvc.perform(get("/api/v1/ventas"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        List<Object> ventas = JsonPath.read(json, "$");
        return ventas.size();
    }

    @Test
    void ventaInvalidaNoApareceEnElListado() throws Exception {
        int antes = totalVentas();

        mvc.perform(post("/api/v1/ventas").contentType(MediaType.APPLICATION_JSON)
                .content("{\"lineas\":[]}"))
            .andExpect(status().isBadRequest());

        assertEquals(antes, totalVentas());
    }
}
