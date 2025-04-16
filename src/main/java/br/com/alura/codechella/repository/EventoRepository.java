package br.com.alura.codechella.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.alura.codechella.model.Evento;
import br.com.alura.codechella.model.TipoEvento;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
	
	Flux<Evento> findByTipo(TipoEvento tipoEvento);
}
