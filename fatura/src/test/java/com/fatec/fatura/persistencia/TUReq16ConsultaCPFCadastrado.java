package com.fatec.fatura.persistencia;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.ResourceAccessException;

import com.fatec.fatura.service.FaturaService;

@SpringBootTest
class TUReq16ConsultaCPFCadastrado {
	@Autowired
	FaturaService servico;

	@Test
	void ct01_consulta_cpf_de_cliente_cadastrado() {
		try {
			//o cpf deve ser cadastrado no servico mantem cliente antes da pesquisa
			assertTrue(servico.cpfCadastrado("34722268037"));
		} catch (ResourceAccessException e) {
			fail("nao deveria falhar");
			System.out.println(e.getMessage());
			//I/O error on POST request for "https://localhost:8081/api/v1/clientes/cpf": Connection refused: connect
		}
	}

}
