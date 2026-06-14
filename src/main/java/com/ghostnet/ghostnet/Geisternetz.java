package com.ghostnet.ghostnet;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ghost_net")
public class Geisternetz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // GPS Koordinaten des Netzes
    private double latitude;
    private double longitude;

    private String geschaetzteGroesse;

    @Enumerated(EnumType.STRING)
    private Netzstatus status;

    // Ist null wenn noch niemand die Bergung übernommen hat
    @ManyToOne
    @JoinColumn(name = "recovering_person_id")
    private Person bergungsPerson;
}
