package com.kauankawasaki.apiindicadoresbcb.indicador;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IndicadorService {

	private final IndicadorRepository repository;

	public IndicadorService(IndicadorRepository repository) {
		this.repository = repository;
	}

	public List<String> listarSeries() {
		return repository.listarSeries();
	}

	public List<Indicador> buscarValores(String serie, LocalDate inicio, LocalDate fim) {
		if (inicio == null && fim == null) {
			return repository.findBySerieOrderByDataAsc(serie);
		}
		if (inicio == null || fim == null) {
			throw new PeriodoInvalidoException("Informe as duas datas (inicio e fim) ou nenhuma");
		}
		if (inicio.isAfter(fim)) {
			throw new PeriodoInvalidoException("A data de início não pode ser depois da data de fim");
		}
		return repository.findBySerieAndDataBetweenOrderByDataAsc(serie, inicio, fim);
	}

	public Indicador buscarUltimo(String serie) {
		return repository.findFirstBySerieOrderByDataDesc(serie)
				.orElseThrow(() -> new SerieNaoEncontradaException(serie));
	}

}
