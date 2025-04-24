package br.com.alura.codechella.model;

public record DadosTraducao(DadosResposta responseData) {

	public String getTexto() {
		return this.responseData.translatedText();
	}
}
