package com.fatec.fatura.service;

import java.util.List;
import java.util.Optional;

import com.fatec.fatura.model.Fatura;

public interface IFaturaServico {

	public FaturaResponse registrar(Fatura fatura);

	public FaturaResponse consultarPorId(String id);

	public List<Fatura> consultarData(String dataEmissao);

	public List<Fatura> consultarMes(int mesEmissao);

	public List<Fatura> consultaCnpj(String cnpj);
}
