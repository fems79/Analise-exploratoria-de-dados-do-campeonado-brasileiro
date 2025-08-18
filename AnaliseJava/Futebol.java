import java.io.IOException;
import java.util.Scanner;

public class Futebol {
    public static void main(String[] args) {
        String[] nomesDosTimes = {
            "Athletico-PR", "Atletico-GO", "Atletico-MG", "Bahia", "Botafogo",
            "RB Bragantino", "Corinthians", "Criciuma", "Cruzeiro", "Cuiaba",
            "Flamengo", "Fluminense", "Fortaleza", "Gremio", "Internacional",
            "Juventude", "Palmeiras", "Sao Paulo", "Vasco da Gama", "Vitoria"
        };
        
        Campeonato brasileirao = new Campeonato(nomesDosTimes);
        
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo de partidas (ex: partidas.csv):");
        String arquivo = entrada.next();

        try {
            brasileirao.carregarPartidas(arquivo);
            brasileirao.exibirTodasAsClassificacoes();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro: O arquivo contém um número de gols inválido.");
        } finally {
            entrada.close();
        }
    }
}
