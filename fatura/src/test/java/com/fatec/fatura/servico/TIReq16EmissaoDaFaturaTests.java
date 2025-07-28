package com.fatec.fatura.servico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.fatura.model.ClienteDto;
import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.model.FaturaDto;
import com.fatec.fatura.service.FaturaRepository;
import com.fatec.fatura.service.FaturaResponse;
import com.fatec.fatura.service.IFaturaServico;

@SpringBootTest
class TIReq16EmissaoDaFaturaTests {

	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	IFaturaServico servicoFatura;
	@Autowired
	FaturaRepository faturaRepository;
	// @Autowired
	private TestRestTemplate restTemplate;

	void setup() {
		try {
			restTemplate = new TestRestTemplate();
			String URLBase = "http://localhost:8081/api/v1/clientes";
			ClienteDto clienteDto = new ClienteDto("21805801007", "Jose Silva", "09931390", "disclabes@gmail.com");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<ClienteDto> request = new HttpEntity<>(clienteDto, headers);
			ResponseEntity<ClienteTemp> response = restTemplate.postForEntity(URLBase, request, ClienteTemp.class);
			System.out.println(">>>>>> setup de teste status code => " + response.getStatusCode());
			// assertEquals(HttpStatus.CREATED, response.getStatusCode());

		} catch (Exception e) {
			System.out.println(">>>>>> erro => " + e.getMessage());
			fail("nao deveria falhar => " + e.getMessage());

		}
	}

	@Test
	void ct01_quando_dados_validos_fatura_nao_eh_nulo() {
		try {
			setup();
			// dado que as informacoes de fatura sao validas
			// quando confirmo a fatura
			FaturaDto fatura = new FaturaDto("21805801007", "", "02/10/2025", "moveis planejados", "1000.50");
			// entao fatura é registrada com data de emisssao igual a data de hoje
			Fatura faturaCadastrada = servicoFatura.registrar(fatura).getFatura();
			assertEquals("21805801007", faturaCadastrada.getCpf());
			assertTrue(faturaRepository.count() > 0);
		} catch (Exception e) {
			logger.info(">>>>>> ct01 - nao deveria falhar cliente cadastrado?=> " + e.getMessage());
			fail("nao deveria falhar fatura valida-" + e.getMessage());

		}
	}

	/*
	 * Esta condição de teste ja foi verificada no teste unitario
	 */
	@Test
	void ct02_quando_dados_invalidos_retorna_mensagem_erro() {
		// dado que as informacoes de fatura sao invalidas
		FaturaDto fatura = new FaturaDto("", "", "02/10/2025", "moveis planejados", "1000.50");
		// quando confirmo a fatura
		FaturaResponse resposta = servicoFatura.registrar(fatura);
		// entao retorna mensagem de erro
		assertEquals("CPF invalido nao cadastrado", resposta.getMensagem());
	}
}
