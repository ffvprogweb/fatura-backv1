package com.fatec.fatura.controller;

import java.awt.print.Book;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.fatura.model.FaturaDto;
import com.fatec.fatura.service.FaturaResponse;
import com.fatec.fatura.service.IFaturaServico;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*") // desabilita o cors do spring security
@RestController
@RequestMapping("/api/v1/faturas")
public class FaturaController {
	Logger logger = LogManager.getLogger(this.getClass());
	final IFaturaServico faturaServico;

	public FaturaController(IFaturaServico servico) {
		this.faturaServico = servico;
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Fatura registrada", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "Dados invalidos no corpo da requisição", content = @Content),
	})
	@PostMapping
	@io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
	public ResponseEntity<Object> saveFatura(@RequestBody FaturaDto fatura) {
		logger.info(">>>>>> 1 controller metodo savefatura --> " + fatura.servicoContratado());
		FaturaResponse f = faturaServico.registrar(fatura);
		if (!f.isSucesso()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
					.body(f.getMensagem());
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(f.getFatura());
		}
	}

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso", content = {
					@Content(mediaType = "application/json") }),

	})
	@GetMapping
	public ResponseEntity<Object> consultaFatura() {
		logger.info(">>>>>> 1 controller metodo consultafatura ");
		return ResponseEntity.status(HttpStatus.OK).body(faturaServico.consultaTodos());
	}
}
