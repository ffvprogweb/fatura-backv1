package com.fatec.fatura.service;

import java.util.List;

import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.model.FaturaDto;

public interface IFaturaServico {

	public FaturaResponse registrar(FaturaDto fatura);

	public FaturaResponse consultarPorId(String id);

	public List<Fatura> consultarData(String dataEmissao);

	public List<Fatura> consultarMes(int mesEmissao);

	public List<Fatura> consultaCnpj(String cnpj);
	
	public List<Fatura> consultaTodos();
	
	
}
