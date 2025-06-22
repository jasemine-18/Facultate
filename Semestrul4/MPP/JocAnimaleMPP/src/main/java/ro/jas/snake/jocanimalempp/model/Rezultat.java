package ro.jas.snake.jocanimalempp.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Rezultat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Jucator jucator;

    private int incercari;
    private boolean succes;
    private LocalDateTime dataFinalizare;
}
