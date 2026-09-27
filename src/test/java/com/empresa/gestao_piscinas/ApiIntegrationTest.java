package com.empresa.gestao_piscinas;

import com.empresa.gestao_piscinas.repository.ClienteRepository;
import com.empresa.gestao_piscinas.repository.PiscinaRepository;
import com.empresa.gestao_piscinas.repository.VisitaTecnicaRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PiscinaRepository piscinaRepository;
    @Autowired
    private VisitaTecnicaRepository visitaRepository;

    @BeforeEach
    void limpar() {
        visitaRepository.deleteAll();
        piscinaRepository.deleteAll();
        clienteRepository.deleteAll();
    }

    private long postar(String url, String json) throws Exception {
        String body = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(body, "$.id")).longValue();
    }

    private long criarCliente(String nome) throws Exception {
        return postar("/api/clientes", """
                {"nome": "%s", "telefone": "11999999999", "endereco": "Rua A, 100"}
                """.formatted(nome));
    }

    private long criarPiscina(long clienteId) throws Exception {
        return postar("/api/piscinas", """
                {"clienteId": %d, "volumeLitros": 30000, "tipoRevestimento": "Vinil", "ambienteExterno": true}
                """.formatted(clienteId));
    }

    private void registrarVisita(long piscinaId, String data, double ph, double cloro) throws Exception {
        postar("/api/visitas", """
                {"piscinaId": %d, "dataVisita": "%s", "nivelPh": %s, "nivelCloro": %s, "status": "CONCLUIDA"}
                """.formatted(piscinaId, data, ph, cloro));
    }

    @Test
    void cadastraClienteEBuscaPorId() throws Exception {
        long id = criarCliente("Condomínio Sol");

        mockMvc.perform(get("/api/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Condomínio Sol"));
    }

    @Test
    void clienteInexistenteRetorna404ComProblemDetail() throws Exception {
        mockMvc.perform(get("/api/clientes/{id}", 9999))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Recurso não encontrado"));
    }

    @Test
    void validaCamposObrigatorios() throws Exception {
        mockMvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON).content("{\"nome\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.nome").exists())
                .andExpect(jsonPath("$.campos.telefone").exists());
    }

    @Test
    void piscinaParaClienteInexistenteRetorna404() throws Exception {
        mockMvc.perform(post("/api/piscinas").contentType(MediaType.APPLICATION_JSON).content("""
                        {"clienteId": 9999, "volumeLitros": 30000, "ambienteExterno": true}
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void listaPiscinasDoCliente() throws Exception {
        long cliente = criarCliente("Condomínio Sol");
        long outro = criarCliente("Casa Praia");
        criarPiscina(cliente);
        criarPiscina(cliente);
        criarPiscina(outro);

        mockMvc.perform(get("/api/clientes/{id}/piscinas", cliente))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].clienteNome").value("Condomínio Sol"));
    }

    @Test
    void historicoDaPiscinaVemOrdenadoEComCondicaoDaAgua() throws Exception {
        long piscina = criarPiscina(criarCliente("Condomínio Sol"));
        registrarVisita(piscina, "2026-09-01", 7.4, 2.0);
        registrarVisita(piscina, "2026-09-15", 8.5, 0.3);
        registrarVisita(piscina, "2026-09-08", 7.0, 1.5);

        mockMvc.perform(get("/api/piscinas/{id}/visitas", piscina))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].dataVisita").value("2026-09-15"))
                .andExpect(jsonPath("$[0].condicaoAgua").value("CRITICA"))
                .andExpect(jsonPath("$[1].condicaoAgua").value("ATENCAO"))
                .andExpect(jsonPath("$[2].condicaoAgua").value("IDEAL"));
    }

    @Test
    void recusaPhForaDaEscala() throws Exception {
        long piscina = criarPiscina(criarCliente("Condomínio Sol"));

        mockMvc.perform(post("/api/visitas").contentType(MediaType.APPLICATION_JSON).content("""
                        {"piscinaId": %d, "dataVisita": "2026-09-01", "nivelPh": 15, "nivelCloro": 2, "status": "CONCLUIDA"}
                        """.formatted(piscina)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.nivelPh").exists());
    }

    @Test
    void swaggerDisponivel() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/piscinas/{id}/visitas']").exists());
    }
}
