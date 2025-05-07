package com.fatec.fatura.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.model.FaturaDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Req16EmissaoDaFaturaTests {
	@Autowired
	private TestRestTemplate restTemplate;
	Logger logger = LogManager.getLogger(this.getClass());

	@Test
	void test() {
		String URLBase = "/api/v1/faturas";
		// ********************************************************************************************
		// Given - dado que as informacoes de cliente sao validas E que existem 3
		// clientes cadastrados
		// ********************************************************************************************
		FaturaDto faturaDto = new FaturaDto("71112917000126","", "02/10/2025", "moveis planejados", "1000.50");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<FaturaDto> request = new HttpEntity<>(faturaDto, headers);
		// ********************************************************************
		// When - quando confirmo o cadastro do cliente
		// ********************************************************************
		//ResponseEntity<Fatura> response = restTemplate.exchange(URLBase, HttpMethod.POST, request, Fatura.class);
		ResponseEntity<Fatura> response = restTemplate.postForEntity(URLBase, request, Fatura.class);
		// ********************************************************************
		// Then - entao retorna os detalhes do cliente cadastrado
		// ********************************************************************
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Fatura faturaCadastrada = response.getBody();
		logger.info(">>>>>> teste web post registrar fatura --> " + response.getBody().toString());
	
			
		assertEquals("71112917000126", faturaCadastrada.getCnpj());
		assertEquals(1, faturaCadastrada.getId()); // Verifica se o ID foi setado corretamente
	}

}
