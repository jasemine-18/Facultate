package ro.jas.snake.jocanimalempp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.jas.snake.jocanimalempp.model.Configuratie;
import ro.jas.snake.jocanimalempp.model.Jucator;
import ro.jas.snake.jocanimalempp.model.Rezultat;
import ro.jas.snake.jocanimalempp.repository.ConfiguratieRepository;
import ro.jas.snake.jocanimalempp.service.JocService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/joc")
@RequiredArgsConstructor

public class JocController {
    private final JocService jocService;
    private final ConfiguratieRepository configuratieRepo;

    //AUTENTIFICARE JUCATOR
    @PostMapping("/autentificare")
    public Jucator autentificare(@RequestBody Map<String, String> payload) {
        String porecla = payload.get("porecla");
        return jocService.autentificaJucator(porecla);
    }

    //PORNIRE JOC
    @PostMapping("/start")
    public String startJoc(@RequestBody Map<String, String> payload) {
        String porecla = payload.get("porecla");
        jocService.pornesteJoc(porecla);
        return "Joc pornit pentru" + porecla;
    }

    //ALEGE POZITIE
    @PostMapping("/alege")
    public String alegePozitie(@RequestBody Map<String, String> payload) {
        String porecla = payload.get("porecla");
        int linie = Integer.parseInt(payload.get("linie"));
        int coloana = Integer.parseInt(payload.get("coloana"));
        return jocService.incearcaPozitie(porecla, linie, coloana);
    }


    //INCERCARE POZITIE
    @GetMapping("/clasament")
    public List<Rezultat> clasament() {
        return jocService.clasament();
    }

    //ISTORIC JUCATOR
    @GetMapping("/istoric/{porecla}")
    public List<Rezultat> istoric(@PathVariable String porecla) {
        return jocService.rezultateJucator(porecla);
    }

    @PostMapping("/configuratie")
    public Configuratie configuratie(@RequestBody Configuratie config) {
        return configuratieRepo.save(config);
    }
}
