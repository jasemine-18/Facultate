package ro.jas.snake.jocanimalempp.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.jas.snake.jocanimalempp.model.Configuratie;
import ro.jas.snake.jocanimalempp.model.Jucator;
import ro.jas.snake.jocanimalempp.model.Rezultat;
import ro.jas.snake.jocanimalempp.repository.ConfiguratieRepository;
import ro.jas.snake.jocanimalempp.repository.JucatorRepository;
import ro.jas.snake.jocanimalempp.repository.RezultatRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JocService {
    private final JucatorRepository jucatorRepo;
    private final RezultatRepository rezultatRepo;
    private final ConfiguratieRepository configuratieRepo;

    private final Map<String, Integer> incercariCurente = new HashMap<>();
    private final Map<String, Configuratie> configuratiiActive = new HashMap<>();

    //INREGISTRARE JUCATORI
    public Jucator autentificaJucator(String porecla) {
        return jucatorRepo.findByPorecla(porecla).orElseGet(() -> {
            var nou = new Jucator(null, porecla);
            return jucatorRepo.save(nou);
        });
    }

    //PORNIRE JOC -> ALEGE O CONFIGURATIE RANDOM PENTRU JUCATOR
    public void pornesteJoc(String porecla) {
        List<Configuratie> toate = configuratieRepo.findAll();
        if (toate.isEmpty()) throw new RuntimeException("No configuratie found in DB");

        Configuratie aleasa = toate.get(new Random().nextInt(toate.size()));
        configuratiiActive.put(porecla, aleasa);
        incercariCurente.put(porecla, 0);
    }

    //JUCATORUL ALEGE O POZITIE
    public String incearcaPozitie(String porecla, int linie, int coloana) {
        Configuratie config = configuratiiActive.get(porecla);
        if (config == null) throw new RuntimeException("Jocul nu a fost pornit");

        int incercari = incercariCurente.getOrDefault(porecla, 0) + 1;
        incercariCurente.put(porecla, incercari);

        if(config.getLinie() == linie && config.getColoana() == coloana) {
            //corect
            rezultatRepo.save(new Rezultat(null, jucatorRepo.findByPorecla(porecla).get(),
                    incercari, true, LocalDateTime.now()));
            configuratiiActive.remove(porecla);
            incercariCurente.remove(porecla);
            return "Ai ghicit corect! Animalul era: " + config.getAnimal();
        }

        //directie gresita
        String directie = determinaDirectie(config.getLinie(), config.getColoana(), linie, coloana);
        return "Gresit! Indiciu: " + directie;
    }

    //determina directia intre pozitia aleasa si cea corecta
    private String determinaDirectie(int corectLinie, int corectColoana, int incercatLinie, int incercatColoana) {
        StringBuilder dir=new StringBuilder();
        if (incercatLinie < corectLinie) dir.append("Sud");
        else if (incercatLinie > corectLinie) dir.append("Nord");

        if (incercatColoana < corectColoana) dir.append("-Est");
        else if (incercatColoana > corectColoana) dir.append("-Vest");

        return dir.toString();
    }

    //returneaza toate rezultatele unui jucator
    public List<Rezultat> rezultateJucator(String porecla) {
        return rezultatRepo.findByJucatorPoreclaOrderByDataFinalizareDesc(porecla);
    }

    //clasament pentru toti jucatorii
    public List<Rezultat> clasament() {
        return rezultatRepo.findAll().stream()
                .filter(Rezultat::isSucces)
                .sorted(Comparator.comparing(Rezultat::getDataFinalizare))
                .toList();
    }
}
