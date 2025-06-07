package com.pruebas.vineta_virtual.entidades;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pruebas.vineta_virtual.entidades.enums.Audiencia;
import com.pruebas.vineta_virtual.entidades.enums.EstadoComic;
import com.pruebas.vineta_virtual.entidades.enums.IdiomaOriginal;
import com.pruebas.vineta_virtual.entidades.enums.PaisOrigen;
import com.pruebas.vineta_virtual.entidades.enums.PaisOrigenConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comic")
public class Comic {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
	private String portada;
	private String titulo;
	@Enumerated(EnumType.STRING)
	private Audiencia audiencia;
	@Column(name = "sello_editorial")
	private String selloEditorial;
	@Column(name = "fecha_lanzamiento")
	private LocalDate fechaLanzamiento;
	@Enumerated(EnumType.STRING)
	private EstadoComic estado;
    private String autores;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "pais_origen")
    @JsonProperty("pais_origen")
    @Convert(converter = PaisOrigenConverter.class)
    private PaisOrigen paisOrigen;
    @Enumerated(EnumType.STRING)
    @Column(name = "idioma_original")
    private IdiomaOriginal idiomaOriginal;
    private String categorias;
    /*@JsonIgnore
    @ManyToMany(mappedBy = "favoritos")
    private List<Lector> lectoresFavoritos;*/
}
