package com.fatec.fatura.persistencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.fatec.fatura.model.Fatura;

class Req16EmissaoDaFaturaDDTests {
	Fatura fatura = new Fatura();
	public String obtemDataAtual() {
		DateTime data = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		// DateTime dataVencimento = dataAtual.plusDays(10);
		return data.toString(fmt);
	}

	@ParameterizedTest
	@CsvFileSource(files = "e:/dataset_fatura/fatura2.csv", numLinesToSkip = 1)
	void validaFatura(String numero, String cnpj, String dataVencimento, String desc, String valor, String re) {
		try {
			fatura = new Fatura(numero, cnpj, dataVencimento, desc, valor);
			assertNotNull(fatura);
			String dataDeHoje = obtemDataAtual();
			assertEquals(dataDeHoje,fatura.getDataEmissao());
			assertEquals (re,"satisfatório");
		} catch (Exception e) {
			assertEquals(re, e.getMessage());
		}
	}


}
