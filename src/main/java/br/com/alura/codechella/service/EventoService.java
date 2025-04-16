package br.com.alura.codechella.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.alura.codechella.model.EventoDto;
import br.com.alura.codechella.repository.EventoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EventoService {

	@Autowired
	private EventoRepository repositorio;

	public Flux<EventoDto> obterTodos() {
		return repositorio.findAll().map(EventoDto::toDto);
	}

	public Mono<EventoDto> obterPorId(Long id) {
        return  repositorio.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(EventoDto::toDto);
    }
	
	public Mono<EventoDto> cadastrar(EventoDto dto) {
        return repositorio.save(dto.toEntity())
                .map(EventoDto::toDto);
    }
	
	public Mono<Void> excluir(Long id) {
        return repositorio.findById(id)
                .flatMap(repositorio::delete);
    }

	public Mono<EventoDto> atualizar(EventoDto dto) {
		return repositorio.save(dto.toEntity())
                .map(EventoDto::toDto);
	}
}