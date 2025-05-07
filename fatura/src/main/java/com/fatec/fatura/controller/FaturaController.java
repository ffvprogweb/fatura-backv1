package com.fatec.fatura.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.fatura.model.FaturaDto;
import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.service.FaturaResponse;
import com.fatec.fatura.service.IFaturaServico;

@CrossOrigin("*") // desabilita o cors do spring security
@RestController
@RequestMapping("/api/v1/faturas")
public class FaturaController {
	Logger logger = LogManager.getLogger(this.getClass());
	final IFaturaServico faturaServico;

	public FaturaController(IFaturaServico servico) {
		this.faturaServico = servico;
	}

	@PostMapping
	public ResponseEntity<Object> saveFatura(@RequestBody FaturaDto fatura) {
		logger.info(">>>>>> 1 controller metodo savefatura --> " + fatura.cnpj());
//		Fatura novaFatura = new Fatura ();
//		novaFatura.setCnpj(fatura.cnpj());
//		novaFatura.setDataVencimento(fatura.dataVencimento());
//		novaFatura.setServicoContratado(fatura.servicoContratado());
//		novaFatura.setValor(fatura.valor());
		FaturaResponse f = faturaServico.registrar(fatura);
		logger.info(">>>>>> 2 controller metodo savefatura --> " + f.getFatura().toString());
		if (!f.isSucesso()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(f.getMensagem());
		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body(f.getFatura());
		}
	}
}
