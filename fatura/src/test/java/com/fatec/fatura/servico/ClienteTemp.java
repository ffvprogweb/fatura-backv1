package com.fatec.fatura.servico;

public record ClienteTemp(Long id, String cpf, String nome, String cep, String endereco, String email,
		String dataCadastro) {

}
