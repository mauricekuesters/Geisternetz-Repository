// Interface das den Zugriff auf die Personen in der Datenbank regelt, 
// ermöglicht das Speichern laden und Suchen von Personen
package com.ghostnet.ghostnet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonSpeicher extends JpaRepository<Person, Long> {
    List<Person> findByRecovering(boolean recovering);
}
