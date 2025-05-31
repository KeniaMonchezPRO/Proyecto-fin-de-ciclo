package com.pruebas.vineta_virtual.entidades.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaisOrigen {
	//Alemania, Argentina, Canadá, España, EstadosUnidos, Francia, México, Portugal, ReinoUnido
	ALEMANIA("Alemania"),
    ARGENTINA("Argentina"),
    CANADA("Canadá"),
    ESPANA("España"),
    ESTADOS_UNIDOS("Estados Unidos"), // Nombre de constante sin espacios
    FRANCIA("Francia"),
    MEXICO("México"),
    PORTUGAL("Portugal"),
    REINO_UNIDO("Reino Unido");

    private final String valor;

    PaisOrigen(String valor) {
        this.valor = valor;
    }
    
    @JsonValue
    public String getValor() {
        return valor;
    }
    
    @JsonCreator
    public static PaisOrigen fromValor(String valor) {
        for (PaisOrigen pais : PaisOrigen.values()) {
            if (pais.valor.equalsIgnoreCase(valor)) {
                return pais;
            }
        }
        throw new IllegalArgumentException("País no encontrado: " + valor);
    }
}
