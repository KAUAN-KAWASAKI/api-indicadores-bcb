package com.kauankawasaki.apiindicadoresbcb.erro;

import com.kauankawasaki.apiindicadoresbcb.indicador.PeriodoInvalidoException;
import com.kauankawasaki.apiindicadoresbcb.indicador.SerieNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(SerieNaoEncontradaException.class)
	public ProblemDetail tratarSerieNaoEncontrada(SerieNaoEncontradaException e) {
		ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		problema.setTitle("Série não encontrada");
		return problema;
	}

	@ExceptionHandler(PeriodoInvalidoException.class)
	public ProblemDetail tratarPeriodoInvalido(PeriodoInvalidoException e) {
		ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
		problema.setTitle("Período inválido");
		return problema;
	}

}
