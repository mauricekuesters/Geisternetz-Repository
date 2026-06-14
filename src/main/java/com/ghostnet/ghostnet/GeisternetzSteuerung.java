package com.ghostnet.ghostnet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GeisternetzSteuerung {

    private final GeisternetzSpeicher geisternetzSpeicher;
    private final PersonSpeicher personSpeicher;

    // Alle Netze auf der Startseite anzeigen
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("nets", geisternetzSpeicher.findAll());
        return "index";
    }

    @GetMapping("/melden")
    public String meldenForm(Model model) {
        return "melden";
    }

    // Diese Methode legt einen neuen Eintrag in der Datenbank an, wobei die Person optional ist
    @PostMapping("/melden")
    public String melden(@RequestParam double latitude,
                         @RequestParam double longitude,
                         @RequestParam String geschaetzteGroesse,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String phone) {
        Geisternetz geisternetz = new Geisternetz();
        geisternetz.setLatitude(latitude);
        geisternetz.setLongitude(longitude);
        geisternetz.setGeschaetzteGroesse(geschaetzteGroesse);
        geisternetz.setStatus(Netzstatus.GEMELDET);

        // Falls eine Name angeben wird, wird dieser hier gespeichert
        if (name != null && !name.isEmpty()) {
            Person person = new Person();
            person.setName(name);
            person.setPhone(phone);
            person.setRecovering(false);
            personSpeicher.save(person);
        }
        geisternetzSpeicher.save(geisternetz);
        return "redirect:/";
    }

    // Zeigt nur Netze die noch nicht in Bergung sind
    @GetMapping("/bergung")
    public String bergungForm(Model model) {
        model.addAttribute("nets", geisternetzSpeicher.findByStatus(Netzstatus.GEMELDET));
        model.addAttribute("persons", personSpeicher.findByRecovering(true));
        return "bergung";
    }

    @PostMapping("/bergung")
    public String bergung(@RequestParam Long netId,
                          @RequestParam String name,
                          @RequestParam String phone) {
        Geisternetz netz = geisternetzSpeicher.findById(netId).orElseThrow();

        // hier wird eine neue Person mit Name und Telefon angelegt, als bergend markiert, gespeichert und dem Netz zugewiesen
        Person person = new Person();
        person.setName(name);
        person.setPhone(phone);
        person.setRecovering(true);
        personSpeicher.save(person);

        netz.setBergungsPerson(person);
        netz.setStatus(Netzstatus.BERGUNG_BEVORSTEHEND);
        geisternetzSpeicher.save(netz);
        return "redirect:/";
    }

    // diese Methode lädt das Netz anhand der ID, setzt den Status auf GEBORGEN und speichert die Änderung in der Datenbank
    @PostMapping("/geborgen/{id}")
    public String geborgen(@PathVariable Long id) {
        Geisternetz netz = geisternetzSpeicher.findById(id).orElseThrow();
        netz.setStatus(Netzstatus.GEBORGEN);
        geisternetzSpeicher.save(netz);
        return "redirect:/";
    }
}
