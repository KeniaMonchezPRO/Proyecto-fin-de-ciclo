package com.pruebas.vineta_virtual.entidades;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pruebas.vineta_virtual.entidades.enums.Audiencia;
import com.pruebas.vineta_virtual.entidades.enums.EstadoComic;
import com.pruebas.vineta_virtual.entidades.enums.IdiomaOriginal;
import com.pruebas.vineta_virtual.entidades.enums.PaisOrigen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "compra")
public class Compra {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne
	@JoinColumn(name="id_lector")
    private Lector lector;
	
	@ManyToOne
	@JoinColumn(name="id_comic")
    private Comic comic;
	
	@Column(name="fecha_compra")
	private LocalDate fechaCompra;
	
	@Column(name="metodo_pago")
	private String metodoPago;

}
