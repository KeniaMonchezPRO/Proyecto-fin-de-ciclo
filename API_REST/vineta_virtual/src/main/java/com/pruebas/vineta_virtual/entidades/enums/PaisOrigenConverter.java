package com.pruebas.vineta_virtual.entidades.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaisOrigenConverter implements AttributeConverter<PaisOrigen, String> {

    @Override
    public String convertToDatabaseColumn(PaisOrigen paisOrigen) {
        return paisOrigen != null ? paisOrigen.getValor() : null;
    }

    @Override
    public PaisOrigen convertToEntityAttribute(String valor) {
        return valor != null ? PaisOrigen.fromValor(valor) : null;
    }
}
