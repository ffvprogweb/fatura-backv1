package com.fatec.fatura.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fatec.fatura.model.Fatura;

public class TUReq16EmissaoDaFaturaDD2Tests {
	CsvReader leitor;
	List<FaturaDadosDeTeste> d;
	Fatura fatura;
	DataFaturamento data = new DataFaturamento();

	@Test
	public void ct_verifica_comportamento_fatura() {
		String resultadoEsperado = "";
		try {
			d = CsvReader.lerArquivo("e:/dataset_fatura/fatura2.csv");
			System.out.println(">>>>>>>>> quantidade de registros =>" + d.size());
		} catch (IOException e) {
			System.out.println(">>>>>> Erro de IO => " + e.getMessage());
		}
		int registro = 1;
		for (FaturaDadosDeTeste f : d) {

			try {
				System.out.println(">>>>>> registro => " + registro);
				resultadoEsperado = f.re();
				fatura = new Fatura(f.cpf(), f.dtemissao(), f.servico(), f.valor());
				assertNotNull(fatura);
				assertEquals(resultadoEsperado, "satisfatório");

			} catch (Exception e) {
				System.out.println(">>>>>> classe invalida resultado esperado =>" + resultadoEsperado
						+ "- resultado obtido =>" + e.getMessage());
				assertEquals(resultadoEsperado, e.getMessage());
			}
			registro = registro + 1;
		}
	}

}
