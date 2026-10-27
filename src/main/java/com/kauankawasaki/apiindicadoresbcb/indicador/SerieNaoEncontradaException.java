package com.kauankawasaki.apiindicadoresbcb.indicador;

public class SerieNaoEncontradaException extends RuntimeException {

	public SerieNaoEncontradaException(String serie) {
		super("Série não encontrada: " + serie);
	}

}
