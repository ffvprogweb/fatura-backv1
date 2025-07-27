package com.fatec.fatura.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fatec.fatura.model.ClienteRecordDTO;
import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.model.FaturaDto;

@Service
public class FaturaService implements IFaturaServico {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	FaturaRepository faturaRepository;

	/*
	 * Uma fatura somente pode ser cadastrada se o cpf estiver cadastrado
	 */
	@Override
	public FaturaResponse registrar(FaturaDto f) {
		try {
			logger.info(">>>>>> 2 fatura service registrar fatura iniciado --> " + f.servicoContratado());
			if (cpfCadastrado(f.cpf())) {
				// obtem a data de hoje do sistema e instancia o objeto fatura
				Fatura fatura = new Fatura(f.cpf(), f.dataVencimento(), f.servicoContratado(), f.valor());
				Fatura novaFatura = faturaRepository.save(fatura);
				logger.info(">>>>>> 3 fatura service registrar fatura executado ");
				return new FaturaResponse(true, "Fatura registrada", novaFatura);
			} else {
				logger.info(">>>>>> 3 fatura service registrar fatura cpf não cadastrado ");
				return new FaturaResponse(false, "CPF invalido nao cadastrado", null);
			}
		} catch (Exception e) {
			logger.info(
					">>>>>> FaturaService metodo registrar fatura - erro no cadastro da fatura -> " + e.getMessage());
			return new FaturaResponse(false, "Erro no registro da fatura=> " + e.getMessage(), null);
		}
	}

	@Override
	public FaturaResponse consultarPorId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fatura> consultarData(String dataEmissao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fatura> consultarMes(int mesEmissao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fatura> consultaCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}
	/*
	 * cosulta as faturas registradas para este cliente
	 * a confirmação de paganmento nao foi implementada
	 */
	@Override
	public List<Fatura> consultaTodos() {
		return faturaRepository.findAll();
	}

	/*
	 * Verifica se o cliente esta cadastrado, o cpf é enviado no corpo da mensagem
	 * post, para nao trafegar na url.
	 * Para simplificar o tratamento na camada web, erros não esperados são tratados na 
	 * console de operação
	 */
	public boolean cpfCadastrado(String cpf) {
		logger.info(">>>>>> fatura servico cpfcadastrado iniciado =>" + cpf);
		String API_URL = "http://localhost:8081/api/v1/clientes/cpf";

		RestTemplate restTemplate = new RestTemplate();
		// cria o dto com o cpf para enviar no corpo da requisicao
		ClienteRecordDTO clienteRequest = new ClienteRecordDTO(cpf, "", "", "");
		// Configura os cabeçalhos para indicar que o corpo é JSON
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// Cria a entidade HTTP com o corpo (JSON do CPF) e os cabeçalhos
		HttpEntity<ClienteRecordDTO> requestEntity = new HttpEntity<>(clienteRequest, headers);
		try {
			//Consulta servico de cliente
			ResponseEntity<ClienteRecordDTO> response = restTemplate.postForEntity(API_URL, requestEntity,
					ClienteRecordDTO.class);
			// Verifica o código de status da resposta
			if (response.getStatusCode() == HttpStatus.OK) {
				logger.info(">>>>>> CPF " + cpf + " encontrado. Detalhes: " + response.getBody());
				// Se a API retornar o cliente, significa que está cadastrado
				return true;
			} else {
				// False cliente não cadastrado
				logger.warn(">>>>>> A API retornou status: " + response.getStatusCode());
				return false;
			}

		} catch (HttpClientErrorException e) {
			logger.warn(">>>>>> Erro não esperado no acesso a API => " + e.getMessage());
			return false;
		}

	}

}
