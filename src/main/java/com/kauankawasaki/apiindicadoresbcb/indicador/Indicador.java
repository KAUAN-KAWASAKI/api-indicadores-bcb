package com.kauankawasaki.apiindicadoresbcb.indicador;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "indicadores")
@IdClass(IndicadorId.class)
public class Indicador {

	@Id
	private String serie;

	@Id
	private LocalDate data;

	@Column(precision = 12, scale = 4)
	private BigDecimal valor;

	protected Indicador() {
	}

	public String getSerie() {
		return serie;
	}

	public LocalDate getData() {
		return data;
	}

	public BigDecimal getValor() {
		return valor;
	}

}
