package ro.jas.snake.jocanimalempp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.jas.snake.jocanimalempp.model.Jucator;

import java.util.Optional;

public interface JucatorRepository extends JpaRepository<Jucator, Long> {
    Optional<Jucator> findByPorecla(String porecla);
}
