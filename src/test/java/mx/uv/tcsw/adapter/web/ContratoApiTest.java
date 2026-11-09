package mx.uv.tcsw.adapter.web;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import java.io.FileReader;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.yaml.snakeyaml.Yaml;

@SpringBootTest
@AutoConfigureMockMvc
class ContratoApiTest {
    @Autowired MockMvc mvc;

    @Test
    @SuppressWarnings("unchecked")
    void todasLasRutasDelContratoExistenEnLaApi() throws Exception {
        Map<String, Object> yaml;
        try (FileReader reader = new FileReader("docs/api/openapi.yaml")) {
            yaml = new Yaml().load(reader);
        }
        Map<String, Object> rutasContrato = (Map<String, Object>) yaml.get("paths");

        String vivo = mvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        Map<String, Object> rutasVivas = JsonPath.read(vivo, "$.paths");

        for (String ruta : rutasContrato.keySet()) {
            assertTrue(rutasVivas.containsKey(ruta), "Falta en la API la ruta del contrato: " + ruta);
        }
    }
}
