package br.com.alura.codechella;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.alura.codechella.model.EventoDto;
import br.com.alura.codechella.model.TipoEvento;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodechellaApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
	}

	@Test
	void cadastraNovoEvento() {
		EventoDto dto = new EventoDto(null, TipoEvento.SHOW, "Kiss", LocalDate.parse("2025-01-01"),
				"Show da melhor banda que existe", 0, 0);

		webTestClient.post().uri("/eventos").bodyValue(dto).exchange().expectStatus().isCreated()
				.expectBody(EventoDto.class).value(response -> {
					assertNotNull(response.id());
					assertEquals(dto.tipo(), response.tipo());
					assertEquals(dto.nome(), response.nome());
					assertEquals(dto.data(), response.data());
					assertEquals(dto.descricao(), response.descricao());
				});

	}

	@Test
	void buscarEvento() {
		EventoDto dto = new EventoDto(13L, TipoEvento.SHOW, "The Weeknd", LocalDate.parse("2025-11-02"),
				"Um show eletrizante ao ar livre com muitos efeitos especiais.", 0, 0);

		webTestClient.get().uri("/eventos").exchange().expectStatus().is2xxSuccessful().expectBodyList(EventoDto.class)
				.value(response -> {
					EventoDto eventoResponse = response.get(12);
					assertEquals(dto.id(), eventoResponse.id());
					assertEquals(dto.tipo(), eventoResponse.tipo());
					assertEquals(dto.nome(), eventoResponse.nome());
					assertEquals(dto.data(), eventoResponse.data());
					assertEquals(dto.descricao(), eventoResponse.descricao());
				});
	}

}
