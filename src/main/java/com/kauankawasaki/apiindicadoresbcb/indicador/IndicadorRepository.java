package com.kauankawasaki.apiindicadoresbcb.indicador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IndicadorRepository extends JpaRepository<Indicador, IndicadorId> {

	@Query("select distinct i.serie from Indicador i order by i.serie")
	List<String> listarSeries();

	List<Indicador> findBySerieOrderByDataAsc(String serie);

	List<Indicador> findBySerieAndDataBetweenOrderByDataAsc(String serie, LocalDate inicio, LocalDate fim);

	Optional<Indicador> findFirstBySerieOrderByDataDesc(String serie);

	boolean existsBySerie(String serie);

}
