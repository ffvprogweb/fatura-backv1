package com.fatec.fatura.model;

public record FaturaDto(String cpf, String dataEmissao, String dataVencimento, String servicoContratado, String valor) {

}
