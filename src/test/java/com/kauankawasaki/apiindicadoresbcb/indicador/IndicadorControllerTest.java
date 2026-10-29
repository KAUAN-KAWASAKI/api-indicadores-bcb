package com.kauankawasaki.apiindicadoresbcb.indicador;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IndicadorController.class)
class IndicadorControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	IndicadorService service;

	@Test
	void deveListarAsSeries() throws Exception {
		when(service.listarSeries()).thenReturn(List.of("dolar", "ipca", "selic"));

		mockMvc.perform(get("/indicadores"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(3))
				.andExpect(jsonPath("$[2]").value("selic"));
	}

	@Test
	void deveRetornarUltimoValorDaSerie() throws Exception {
		Indicador indicador = mock(Indicador.class);
		when(indicador.getSerie()).thenReturn("selic");
		when(indicador.getData()).thenReturn(LocalDate.of(2026, 10, 28));
		when(indicador.getValor()).thenReturn(new BigDecimal("15.0000"));
		when(service.buscarUltimo("selic")).thenReturn(indicador);

		mockMvc.perform(get("/indicadores/selic/ultimo"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.serie").value("selic"))
				.andExpect(jsonPath("$.data").value("2026-10-28"))
				.andExpect(jsonPath("$.valor").value(15.0));
	}

	@Test
	void deveRetornar404QuandoSerieNaoExiste() throws Exception {
		when(service.buscarUltimo("xyz")).thenThrow(new SerieNaoEncontradaException("xyz"));

		mockMvc.perform(get("/indicadores/xyz/ultimo"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Série não encontrada"));
	}

	@Test
	void deveRetornar400QuandoPeriodoInvalido() throws Exception {
		when(service.buscarValores(eq("selic"), any(), any()))
				.thenThrow(new PeriodoInvalidoException("A data de início não pode ser depois da data de fim"));

		mockMvc.perform(get("/indicadores/selic").param("inicio", "2026-02-01").param("fim", "2026-01-01"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.title").value("Período inválido"));
	}

}
