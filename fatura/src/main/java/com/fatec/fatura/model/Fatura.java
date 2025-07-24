package com.fatec.fatura.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Objects;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Fatura {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	String cpf;
	@JsonFormat(pattern = "dd/MM/yyyy")
	String dataEmissao;
	@JsonFormat(pattern = "dd/MM/yyyy")
	String dataVencimento;
	String servicoContratado;
	Double valor;

	public Fatura(String cpf, String dataVencimento, String servicoContratado, String valor) {

		setCpf(cpf);
		setDataEmissao();
		setDataVencimento(dataVencimento);
		setServicoContratado(servicoContratado);
		setValorFatura(valor);
	}

	public Fatura() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public double getValor() {
		return valor;
	}

	public String setDataEmissao() {
		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		this.dataEmissao = dataAtual.toString(fmt);
		return dataAtual.toString(fmt);
	}

	public String setDataVencimento(String data) {
		if ((data != null) && (dataIsValida(data)) && (dtVencMaiorDtAtual(getDataEmissao(), data))
				&& (!ehDomingo(data))) {
            this.dataVencimento = data;
			return data;
		} else {
			throw new IllegalArgumentException(
					"Data de vencimento: formato invalido ou domingo ou menor que data atual");
		}
	}

	public boolean ehDomingo(String data) {
		if (dataIsValida(data) && data != null) {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime umaData = fmt.parseDateTime(data);
			if (umaData.dayOfWeek().getAsText().equals("domingo")) {

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public boolean dataIsValida(String data) {
		if (data != null) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			df.setLenient(false);
			try {
				df.parse(data);
				return true;
			} catch (ParseException ex) {

				return false;
			}
		} else {
			return false;
		}
	}

	public boolean dtVencMaiorDtAtual(String dataAtual, String dataVencimento) {
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
			DateTime dtAtual = formatter.parseDateTime(dataAtual);
			DateTime dtVenc = formatter.parseDateTime(dataVencimento);

			Days d = Days.daysBetween(dtAtual, dtVenc);
			if (d.getDays() >= 0) {

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			throw new IllegalArgumentException("Fatura data invalida => " + e.getMessage());
		}
	}

	public double setValorFatura(String entrada) {
		try {
			Double valorTemp = Double.parseDouble(entrada);

			if (valorTemp > 0) {
				DecimalFormat formato = new DecimalFormat("#,##0.00");
				String valorFormatado = formato.format(valorTemp);
				this.valor = valorTemp;
				return valorTemp;
			} else {
				throw new IllegalArgumentException("Valor da fatura invalido");
			}
		} catch (Exception e) {

			throw new IllegalArgumentException("Valor da fatura invalido");
		}
	}

	/*
	 * atribui o cnpj vefica se o cpf é valido
	 */
	public String setCpf(String cpf) {
		try {
			if (cpfIsValido(cpf)) {
				this.cpf = cpf;
				return cpf;
			} else {
				throw new IllegalArgumentException("CPF invalido");
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("CPF invalido");
		}

	}

	public String getCpf() {
		return this.cpf;
	}

	public String getServicoContratado() {
		return servicoContratado;
	}

	public String setServicoContratado(String servico) {

		if ((servico == null) || (servico.isBlank())) {
			throw new IllegalArgumentException("Descricao do servico invalido");
		} else {
			this.servicoContratado = servico;
			return servico;
		}

	}

	public static boolean cpfIsValido(String cpf) {
		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999") || (cpf.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		try {
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				num = (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig10 = '0';
			} else {
				dig10 = (char) (r + 48);
			}

			sm = 0;
			peso = 11;

			for (i = 0; i < 10; i++) {
				num = (cpf.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);

			if ((r == 10) || (r == 11)) {
				dig11 = '0';
			} else {
				dig11 = (char) (r + 48);
			}

			if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
				return (true);
			} else {
				return (false);
			}
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataEmissao, dataVencimento, valor);
	}

	@Override
	public String toString() {
		return "Fatura [id=" + id + ", cpf=" + cpf + ", dataEmissao=" + dataEmissao + ", dataVencimento="
				+ dataVencimento + ", servicoContratado=" + servicoContratado + ", valor=" + valor + "]";
	}

}