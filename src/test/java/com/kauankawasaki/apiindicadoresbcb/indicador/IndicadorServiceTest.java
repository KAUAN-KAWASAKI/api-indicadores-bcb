package com.kauankawasaki.apiindicadoresbcb.indicador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndicadorServiceTest {

	@Mock
	IndicadorRepository repository;

	@InjectMocks
	IndicadorService service;

	@Test
	void deveLancarExcecaoQuandoSerieNaoExiste() {
		when(repository.existsBySerie("xyz")).thenReturn(false);

		assertThrows(SerieNaoEncontradaException.class,
				() -> service.buscarValores("xyz", null, null));
	}

	@Test
	void deveLancarExcecaoQuandoInicioDepoisDeFim() {
		when(repository.existsBySerie("selic")).thenReturn(true);

		assertThrows(PeriodoInvalidoException.class,
				() -> service.buscarValores("selic", LocalDate.of(2026, 2, 1), LocalDate.of(2026, 1, 1)));
	}

	@Test
	void deveLancarExcecaoQuandoSoInicioPreenchido() {
		when(repository.existsBySerie("selic")).thenReturn(true);

		assertThrows(PeriodoInvalidoException.class,
				() -> service.buscarValores("selic", LocalDate.of(2026, 1, 1), null));
	}

}
