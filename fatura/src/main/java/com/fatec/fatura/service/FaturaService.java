package com.fatec.fatura.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.fatura.model.Fatura;
import com.fatec.fatura.model.FaturaDto;

@Service
public class FaturaService implements IFaturaServico {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	FaturaRepository faturaRepository;

	@Override
	public FaturaResponse registrar(FaturaDto f) {
		try {
			logger.info(">>>>>> 2 fatura service metodo registrar fatura --> " + f.servicoContratado());
			//obtem a data de hoje do sistema e nstancia o objeto fatura
			Fatura fatura = new Fatura(f.cnpj(), f.dataVencimento(), f.servicoContratado(), f.valor());
			Fatura novaFatura = faturaRepository.save(fatura);
			
			return new FaturaResponse(true, "Fatura registrada", novaFatura);
		} catch (Exception e) {
			logger.info(">>>>>> FaturaService metodo registrar fatura - erro no cadastro da fatura -> " + e.getMessage());
			return new FaturaResponse(false, "Erro no registro da fatura", null);
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
	public List<Fatura> consultaCnpj(String cnpj) {
		// TODO Auto-generated method stub
		return null;
	}

}
