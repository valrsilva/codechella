package br.com.alura.codechella.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("eventos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Evento {
    @Id
    private Long id;
    private TipoEvento tipo;
    private String nome;
    private LocalDate data;
    private String descricao;

    //getter e setters	
}