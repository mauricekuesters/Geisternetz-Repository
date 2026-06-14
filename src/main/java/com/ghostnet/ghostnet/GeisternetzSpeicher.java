package com.ghostnet.ghostnet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// Interface das den Zugriff auf die Geisternetze in der Datenbank regelt
public interface GeisternetzSpeicher extends JpaRepository<Geisternetz, Long> {
    List<Geisternetz> findByStatus(Netzstatus status);
}
