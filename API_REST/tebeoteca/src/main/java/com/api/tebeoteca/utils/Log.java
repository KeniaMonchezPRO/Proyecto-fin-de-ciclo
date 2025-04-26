package com.api.tebeoteca.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	
	private static String ruta;
	private String nombreArchivo;
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final long LIMITE = 30;
	
	public Log(String ruta, String nombreArchivo) {
		this.ruta = ruta;
		this.nombreArchivo = nombreArchivo;
	}
	
	public static void info(String mensaje) {
		anadirLog("INFO", mensaje);
	}
	
	public static void error(String mensaje) {
		anadirLog("ERROR", mensaje);
	}
	
	private static void anadirLog(String tipo, String mensaje) {
		LocalDateTime now = LocalDateTime.now();
		String timestamp = now.format(DATE_TIME_FORMATTER);
		String entradaLog = "[" + timestamp + "] " + "[" + tipo + "] " + mensaje;
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(ruta, true))) {
			writer.write(entradaLog);
		} catch (IOException e) {
			System.err.println("Erro al escribir en el archivo de log: " + e.getMessage());
		}
	}
	
	
	

}
