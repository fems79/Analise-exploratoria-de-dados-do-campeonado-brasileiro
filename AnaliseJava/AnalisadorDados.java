import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalisadorDados {
    public record ResultadoTime(int vitorias, int empates, int derrotas) {}

    public static List<Partida> lerCsv(String caminhoArquivo) throws IOException {
        List<Partida> partidas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); 
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                if (campos.length < 5) continue;
                partidas.add(new Partida(
                        campos[1], campos[2],
                        Integer.parseInt(campos[3]),
                        Integer.parseInt(campos[4])
                ));
            }
        }
        return partidas;
    }

    public static Map<String, Time> gerarTimes(List<Partida> partidas) {
        Map<String, Time> times = new HashMap<>();
        for (Partida p : partidas) {
            times.putIfAbsent(p.getMandante(), new Time(p.getMandante()));
            times.putIfAbsent(p.getVisitante(), new Time(p.getVisitante()));
            times.get(p.getMandante()).atualizar(p);
            times.get(p.getVisitante()).atualizar(p);
        }
        return times;
    }

    public static Map<String, List<Integer>> calcularEvolucaoTodosTimes(List<Partida> todasPartidas) {
        Map<String, List<Integer>> evolucaoGeral = new HashMap<>();
        Map<String, Integer> pontosAtuais = new HashMap<>();

        todasPartidas.stream()
            .flatMap(p -> java.util.stream.Stream.of(p.getMandante(), p.getVisitante()))
            .distinct()
            .forEach(nomeTime -> {
                evolucaoGeral.put(nomeTime, new ArrayList<>());
                pontosAtuais.put(nomeTime, 0);
            });
        
        for (Partida p : todasPartidas) {
            int[] pontosPartida = p.getPontos();
            
            int pontosMandante = pontosAtuais.get(p.getMandante()) + pontosPartida[0];
            pontosAtuais.put(p.getMandante(), pontosMandante);
            evolucaoGeral.get(p.getMandante()).add(pontosMandante);
            
            int pontosVisitante = pontosAtuais.get(p.getVisitante()) + pontosPartida[1];
            pontosAtuais.put(p.getVisitante(), pontosVisitante);
            evolucaoGeral.get(p.getVisitante()).add(pontosVisitante);
        }

        return evolucaoGeral;
    }
    public static ResultadoTime calcularResultados(String nomeTime, List<Partida> todasPartidas) {
        int vitorias = 0;
        int empates = 0;
        int derrotas = 0;

        for (Partida p : todasPartidas) {
            if (p.getMandante().equals(nomeTime) || p.getVisitante().equals(nomeTime)) {
                int[] pontos = p.getPontos();
                boolean ehMandante = p.getMandante().equals(nomeTime);
                
                if ((ehMandante && pontos[0] == 3) || (!ehMandante && pontos[1] == 3)) {
                    vitorias++;
                } else if ((ehMandante && pontos[0] == 1) || (!ehMandante && pontos[1] == 1)) {
                    empates++;
                } else {
                    derrotas++;
                }
            }
        }
        return new ResultadoTime(vitorias, empates, derrotas);
    }
}
