import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Campeonato {

    private Map<String, Time> times;

    public Campeonato(String[] nomesDosTimes) {
        times = new HashMap<>();
        for (String nome : nomesDosTimes) {
            times.put(nome, new Time(nome));
        }
    }

    public void carregarPartidas(String nomeArquivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                processarLinhaPartida(linha);
            }
        }
    }

    private void processarLinhaPartida(String linha) {
        String[] campos = linha.split(",");
        if (campos.length < 4) return; 

        String nomeTimeCasa = campos[0].trim();
        String nomeTimeVisitante = campos[1].trim();
        int golsCasa = Integer.parseInt(campos[2].trim());
        int golsVisitante = Integer.parseInt(campos[3].trim());

        Time timeCasa = times.get(nomeTimeCasa);
        Time timeVisitante = times.get(nomeTimeVisitante);

        if (timeCasa != null && timeVisitante != null) {
            timeCasa.registrarPartida(true, golsCasa, golsVisitante);
            timeVisitante.registrarPartida(false, golsVisitante, golsCasa);
        }
    }

    public void exibirTodasAsClassificacoes() {
        
        imprimirClassificacao("Classificacao Geral", Comparator.comparingInt(Time::getPontos).reversed(), "Pontos", t -> t.getPontos());
        
        imprimirClassificacao("Classificacao Mandante", Comparator.comparingInt(Time::getPontosCasa).reversed(), "Pontos", t -> t.getPontosCasa());

        imprimirClassificacao("Classificacao Visitante", Comparator.comparingInt(Time::getPontosFora).reversed(), "Pontos", t -> t.getPontosFora());

        imprimirClassificacao("Classificacao Gols-Pro", Comparator.comparingInt(Time::getGolsPro).reversed(), "Gols-Pro", t -> t.getGolsPro());

        imprimirClassificacao("Classificacao Gols Contra (Menos Vazado)", Comparator.comparingInt(Time::getGolsContra), "Gols-Contra", t -> t.getGolsContra());
        
        imprimirClassificacao("Classificacao Saldo de Gols", Comparator.comparingInt(Time::getSaldoDeGols).reversed(), "Saldo-Gols", t -> t.getSaldoDeGols());
    }
    
    private void imprimirClassificacao(String titulo, Comparator<Time> criterio, String nomeStat, java.util.function.Function<Time, Integer> getStat) {
        System.out.println(titulo + ":");
        List<Time> listaTimes = new ArrayList<>(times.values());
        listaTimes.sort(criterio);

        for (Time time : listaTimes) {
             System.out.printf("%-20s --- Jogos: %-5d --- %s: %d\n", 
                time.getNome(), 
                getJogos(time, titulo),
                nomeStat, 
                getStat.apply(time));
        }
        System.out.println();
    }
    
    private int getJogos(Time time, String titulo) {
        if (titulo.contains("Mandante")) {
            return time.getJogosCasa();
        } else if (titulo.contains("Visitante")) {
            return time.getJogosFora();
        }
        return time.getJogos();
    }
}
