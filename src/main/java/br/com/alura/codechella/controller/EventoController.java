package br.com.alura.codechella.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.codechella.model.EventoDto;
import br.com.alura.codechella.service.EventoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/eventos")
public class EventoController {


	private final EventoService servico;
	private final Sinks.Many<EventoDto> eventoSink;
	private final Sinks.Many<EventoDto> ingressoSink;

	public EventoController(EventoService servico) {
        this.servico = servico;
        this.eventoSink = Sinks.many().multicast().onBackpressureBuffer();
        this.ingressoSink = Sinks.many().multicast().onBackpressureBuffer();
	}
	
	@GetMapping
	public Flux<EventoDto> obterTodos() {
		return servico.obterTodos();
	}
	
	@GetMapping("/{id}")
    public Mono<EventoDto> obterPorId(@PathVariable Long id) {
        return servico.obterPorId(id);
    }
	
	@GetMapping("/{id}/traduzir/{idioma}")
    public Mono<String> traduzir(@PathVariable Long id, @PathVariable String idioma) {
        return servico.obterTraducao(id, idioma);
    }
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<EventoDto> cadastrar(@RequestBody EventoDto dto) {
	        return servico.cadastrar(dto).doOnSuccess(e -> eventoSink.tryEmitNext(e));
	}
	
	@GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> obterPorTipo(@PathVariable String tipo) {
		return Flux.merge(servico.obterPorTipo(tipo), eventoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }
	
	@DeleteMapping("/{id}")
    public Mono<Void> excluir(@PathVariable Long id) {
        return servico.excluir(id);
    }
	
	@PutMapping
    public Mono<EventoDto> atualizar(@RequestBody EventoDto evento) {
        return servico.atualizar(evento);
    }
	
	@GetMapping(value = "/{id}/ingresso/status", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoDto> statusVendaIngresso(@PathVariable Long id) {
		return Flux.merge(servico.obterPorId(id), ingressoSink.asFlux())
                .delayElements(Duration.ofSeconds(4));
    }
	
	@PostMapping("/{id}/ingresso")
	public Mono<EventoDto> comprarIngresso(@PathVariable Long id) {
		return servico.comprarIngresso(id).doOnSuccess(e -> ingressoSink.tryEmitNext(e));
	}
	
}
