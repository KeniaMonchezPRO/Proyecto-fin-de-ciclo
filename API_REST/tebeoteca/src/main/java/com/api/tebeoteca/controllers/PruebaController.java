package com.api.tebeoteca.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hola")
public class PruebaController {
	@GetMapping("/mundo") //hai getmapping, postmapping, etc //es un requestmapping pero solo para get
	public String HolaMundo( ) {
		return "Hola Mundo!";
	}

}
