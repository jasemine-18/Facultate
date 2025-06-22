package ro.jas.snake.jocanimalempp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.jas.snake.jocanimalempp.model.Rezultat;

import java.util.List;

public interface RezultatRepository extends JpaRepository<Rezultat, Long> {
    List<Rezultat> findByJucatorPoreclaOrderByDataFinalizareDesc(String porecla);
}
