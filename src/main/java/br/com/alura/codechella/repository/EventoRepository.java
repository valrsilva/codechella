package br.com.alura.codechella.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import br.com.alura.codechella.model.Evento;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
}
