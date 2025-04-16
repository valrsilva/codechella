package br.com.alura.codechella.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.codechella.model.EventoDto;
import br.com.alura.codechella.service.EventoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/eventos")
public class EventoController {

	@Autowired
	private EventoService servico;

	@GetMapping //(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<EventoDto> obterTodos() {
		return servico.obterTodos();
	}
	
	@GetMapping("/{id}")
    public Mono<EventoDto> obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }
	
	@PostMapping
	public Mono<EventoDto> cadastrar(@RequestBody EventoDto dto) {
	        return servico.cadastrar(dto);
	}
	
	@DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id);
    }
	
	@PutMapping
    public Mono<EventoDto> atualizar(@RequestBody EventoDto evento) {
        return servico.atualizar(evento);
    }
	
}
