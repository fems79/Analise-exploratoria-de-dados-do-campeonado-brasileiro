package AnaliseJava.Dados;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalisadorDados {

    public static List<Partida> lerCsv(String caminhoArquivo) throws IOException {
        List<Partida> partidas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha = br.readLine(); 
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                Partida partida = new Partida(
                    campos[1],
                    campos[2],
                    Integer.parseInt(campos[3]),
                    Integer.parseInt(campos[4])
                );
                partidas.add(partida);
            }
        }
        return partidas;
    }

    public static Map<String, Integer> calcularVitoriasPorTime(List<Partida> partidas) {
        Map<String, Integer> vitorias = new HashMap<>();
        for (Partida p : partidas) {
            String vencedor = p.getVencedor();
            if (!"Empate".equals(vencedor)) {
                vitorias.put(vencedor, vitorias.getOrDefault(vencedor, 0) + 1);
            }
        }
        return vitorias;
    }
}
