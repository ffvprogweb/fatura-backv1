package com.fatec.fatura.model;

public record FaturaDto(String cnpj, String dataEmissao, String dataVencimento, String servicoContratado, String valor) {

}
