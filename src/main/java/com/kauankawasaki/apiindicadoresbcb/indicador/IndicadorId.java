package com.kauankawasaki.apiindicadoresbcb.indicador;

import java.io.Serializable;
import java.time.LocalDate;

public record IndicadorId(String serie, LocalDate data) implements Serializable {
}
