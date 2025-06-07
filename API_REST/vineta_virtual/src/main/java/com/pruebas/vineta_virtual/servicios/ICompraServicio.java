package com.pruebas.vineta_virtual.servicios;

import java.util.List;

import com.pruebas.vineta_virtual.entidades.Comic;
import com.pruebas.vineta_virtual.entidades.Compra;

public interface ICompraServicio {
	
	void generarCompra(int idLector, int idComic);
	List<Comic> obtenerCompras(int idLector);

}
