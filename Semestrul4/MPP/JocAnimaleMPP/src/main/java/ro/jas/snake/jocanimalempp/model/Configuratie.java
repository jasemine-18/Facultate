package ro.jas.snake.jocanimalempp.model;


import jakarta.persistence.*;
import lombok.*;

@Entity //hibernatate creeaza un tabel din aceasta clasa
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Configuratie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id ul este auto generat
    private Long id;
    private String animal;
    private int linie; //pozitia animalului pe harta
    private int coloana; //pozitia animalului pe harta
    private String imagineUrl; //linkul imaginii
}
