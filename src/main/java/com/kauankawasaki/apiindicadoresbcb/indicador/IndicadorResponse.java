package com.kauankawasaki.apiindicadoresbcb.indicador;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IndicadorResponse(String serie, LocalDate data, BigDecimal valor) {

	public static IndicadorResponse de(Indicador indicador) {
		return new IndicadorResponse(indicador.getSerie(), indicador.getData(), indicador.getValor());
	}

}
