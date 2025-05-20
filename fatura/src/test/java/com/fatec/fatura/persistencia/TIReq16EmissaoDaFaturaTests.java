package com.fatec.fatura.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

	@Test
	void ct01_quando_dados_validos_fatura_nao_eh_nulo() {
		try {

			// dado que as informacoes de fatura sao validas
			// quando confirmo a fatura
			FaturaDto fatura = new FaturaDto("71112917000126","","02/10/2025", "moveis planejados", "1000.50");
			// entao fatura é registrada com data de emisssao igual a data de hoje
			Fatura faturaCadastrada = servicoFatura.registrar(fatura).getFatura();
			assertEquals("71112917000126", faturaCadastrada.getCnpj());
			assertTrue(faturaRepository.count() > 0);
		} catch (Exception e) {
			logger.info(">>>>>> ct01 - nao deveria falhar => " + e.getMessage());
			fail("nao deveria falhar fatura valida-" + e.getMessage());

		}
	}
	/*
	 * Esta condição de teste ja foi verificada no teste unitario
	 */
	@Test
	void ct02_quando_dados_invalidos_retorna_mensagem_erro() {
		// dado que as informacoes de fatura sao invalidas
		FaturaDto fatura = new FaturaDto("","", "02/10/2025", "moveis planejados", "1000.50");
		// quando confirmo a fatura
		FaturaResponse resposta = servicoFatura.registrar(fatura);
		// entao retorna mensagem de erro
		assertEquals("Erro no registro da fatura", resposta.getMensagem());
	}
}
