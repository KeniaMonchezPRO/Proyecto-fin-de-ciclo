package com.pruebas.vineta_virtual.servicios.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.vineta_virtual.dao.ComicRepositorio;
import com.pruebas.vineta_virtual.dao.CompraRepositorio;
import com.pruebas.vineta_virtual.dao.LectorRepositorio;
import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Compra;
import com.pruebas.vineta_virtual.servicios.ICompraServicio;

@Service
public class CompraServicioImpl implements ICompraServicio {
	
	@Autowired
	private CompraRepositorio compraRepositorio;
	
	@Autowired
	private LectorRepositorio lectorRepositorio;
	
	@Autowired
	private ComicRepositorio comicRepositorio;
	
	@Override
	public void generarCompra(int idLector, int idComic) {
		if (compraRepositorio.existsByLector_IdAndComicId(idLector, idComic)){
			return;
		}

        Compra compra = new Compra();
        compra.setLector(lectorRepositorio.findById(idLector).orElseThrow());
        compra.setComic(comicRepositorio.findById(idComic).orElseThrow());
        compra.setFechaCompra(LocalDate.now());
        compra.setMetodoPago("inApp");

        compraRepositorio.save(compra);
	}

	@Override
	public List<Comic> obtenerCompras(int idLector) {
		return compraRepositorio.findByLector_Id(idLector)
                .stream().map(Compra::getComic).toList();
	}

}
