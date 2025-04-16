package br.com.alura.codechella.model;

import java.time.LocalDate;

public record EventoDto(Long id, TipoEvento tipo, String nome, LocalDate data, String descricao, Integer totalIngressos,
		Integer ingressosDisponiveis) {

	public static EventoDto toDto(Evento evento) {
		return new EventoDto(evento.getId(), evento.getTipo(), evento.getNome(), evento.getData(),
				evento.getDescricao(), evento.getTotalIngressos(), evento.getIngressosDisponiveis());
	}

	public Evento toEntity() {
		Evento evento = new Evento();
		evento.setId(this.id);
		evento.setNome(this.nome);
		evento.setTipo(this.tipo);
		evento.setData(this.data);
		evento.setDescricao(this.descricao);
		evento.setTotalIngressos(this.totalIngressos);
		evento.setIngressosDisponiveis(this.ingressosDisponiveis);
		return evento;
	}

}