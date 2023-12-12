package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Pessoa {
    private String nome;
    private int idade;

    public Pessoa(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Idade: " + idade;
    }
}

public class Main {
    public static void main(String[] args) {
        // Nome
        String nome = "Vinícius";

        try {
            // URL com o nome
            URL url = new URL("https://api.agify.io/?name=" + nome);

            // Abre a conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Lê a resposta da API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            // Converte a resposta para um objeto JsonNode usando ObjectMapper questao 07
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = objectMapper.readTree(response.toString());

            // Obtém os valores do JsonNode
            String nomeResposta = jsonResponse.get("name").asText();
            int idadeResposta = jsonResponse.get("age").asInt();

            // Cria um objeto Pessoa com os valores obtidos
            Pessoa pessoa = new Pessoa(nomeResposta, idadeResposta);

            // Imprime o objeto Pessoa
            System.out.println(pessoa);

            // Fecha a conexão
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}