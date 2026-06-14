package com.ghostnet.ghostnet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "person")

// Speichert eine Person die ein Netz gemeldet oder eine Bergung übernommen hat
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    // true wenn die Person aktiv eine Begung durchführt
    private boolean recovering;
}