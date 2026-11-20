package mx.uv.tcsw.adapter.web;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoApiTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void listarDevuelve200YArreglo() throws Exception {
        mvc.perform(get("/api/v1/productos"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void crearInvalidoDevuelve400ConFormatoControlado() throws Exception {
        String body = "{\"nombre\":\"\",\"precio\":-1}";

        mvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void obtenerInexistenteDevuelve404ConCodeNotFound() throws Exception {
        mvc.perform(get("/api/v1/productos/no-existe"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(containsString("\"code\":\"NOT_FOUND\"")));
    }
}