package com.fatec.fatura.persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<FaturaDadosDeTeste> lerArquivo(String caminhoArquivo) throws IOException {
        List<FaturaDadosDeTeste> faturas = new ArrayList<>();
        String linha;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            // Ler a primeira linha (cabeçalho) e ignorar, se houver
            br.readLine();

            while ((linha = br.readLine()) != null) {
                // Dividir a linha por vírgula
                String[] campos = linha.split(",");

                // Verificar se a linha tem o número correto de campos
                if (campos.length == 6) {
                    try {
                        String numero = campos[0].trim();
                        String cnpj = campos[1].trim();
                        String dataVencimento = campos[2].trim();
                        String desc = campos[3].trim();
                        String valor = campos[4].trim();
                        String re = campos[5].trim();

                        // Criar um objeto Fatura com os dados lidos
                        FaturaDadosDeTeste fatura = new FaturaDadosDeTeste(numero, cnpj, dataVencimento, desc, valor, re);
                        faturas.add(fatura);
                    } catch (Exception e) {
                        System.err.println("Erro na leitura do arquivo na linha: " + linha);
                        // Você pode optar por lançar a exceção novamente ou tratar de outra forma
                    }
                } else {
                    System.err.println("Linha inválida no arquivo CSV (número incorreto de campos): " + linha);
                    // Você pode optar por lançar uma exceção ou ignorar a linha
                }
            }
        }
        return faturas;
    }
}