package com.kauankawasaki.apiindicadoresbcb.indicador;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/indicadores")
public class IndicadorController {

	private final IndicadorService service;

	public IndicadorController(IndicadorService service) {
		this.service = service;
	}

	@GetMapping
	public List<String> listarSeries() {
		return service.listarSeries();
	}

	@GetMapping("/{serie}")
	public List<IndicadorResponse> buscarValores(
			@PathVariable String serie,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
		return service.buscarValores(serie, inicio, fim).stream()
				.map(IndicadorResponse::de)
				.toList();
	}

	@GetMapping("/{serie}/ultimo")
	public IndicadorResponse buscarUltimo(@PathVariable String serie) {
		return IndicadorResponse.de(service.buscarUltimo(serie));
	}

}
