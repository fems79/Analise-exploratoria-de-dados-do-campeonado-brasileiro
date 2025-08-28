import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TelaPrincipal extends JFrame {
    private final PainelGrafico painelGraficoBarras;
    private final PainelMultiLinha painelGraficoMultiLinha;
    private final PainelPizza painelGraficoPizza;
    private Map<String, Time> times;
    private List<Partida> partidas;

    public TelaPrincipal() {
        setTitle("Análise do Campeonato Brasileiro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLayout(new BorderLayout());

        JPanel painelControles = new JPanel();
        JButton btnCarregar = new JButton("Carregar CSV");
        painelControles.add(btnCarregar);
        add(painelControles, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();

        JPanel abaClassificacao = new JPanel(new BorderLayout());
        JComboBox<String> comboAnalise = new JComboBox<>(new String[]{
                "Classificacao Geral", "Classificacao Mandante", "Classificacao Visitante",
                "Classificacao Gols-Pro", "Classificacao Gols Contra", "Classificacao Saldo de Gols"
        });
        abaClassificacao.add(comboAnalise, BorderLayout.NORTH);
        painelGraficoBarras = new PainelGrafico();
        abaClassificacao.add(new JScrollPane(painelGraficoBarras), BorderLayout.CENTER);
        abas.addTab("Classificação Geral", abaClassificacao);

        painelGraficoMultiLinha = new PainelMultiLinha();
        abas.addTab("Comparativo de Evolução", new JScrollPane(painelGraficoMultiLinha));

        JPanel abaPizza = new JPanel(new BorderLayout());
        JPanel painelSelecaoTimePizza = new JPanel();
        painelSelecaoTimePizza.add(new JLabel("Selecione o Time:"));
        JComboBox<String> comboTimesPizza = new JComboBox<>();
        comboTimesPizza.setEnabled(false); 
        painelSelecaoTimePizza.add(comboTimesPizza);
        abaPizza.add(painelSelecaoTimePizza, BorderLayout.NORTH);
        
        painelGraficoPizza = new PainelPizza();
        abaPizza.add(painelGraficoPizza, BorderLayout.CENTER);
        
        abas.addTab("Desempenho por Time", abaPizza);
        
        add(abas, BorderLayout.CENTER);

        btnCarregar.addActionListener(e -> carregarDados(comboAnalise, comboTimesPizza));

        comboAnalise.addActionListener(e -> {
            if (partidas != null) {
                atualizarGraficoBarras(comboAnalise.getSelectedItem().toString());
            }
        });

        comboTimesPizza.addActionListener(e -> {
            if (comboTimesPizza.getSelectedItem() != null) {
                atualizarGraficoPizza(comboTimesPizza.getSelectedItem().toString());
            }
        });
        
        setLocationRelativeTo(null);
    }

    private void carregarDados(JComboBox<String> comboAnalise, JComboBox<String> comboTimesPizza) {
        JFileChooser seletor = new JFileChooser();
        if (seletor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                partidas = AnalisadorDados.lerCsv(seletor.getSelectedFile().getAbsolutePath());
                times = AnalisadorDados.gerarTimes(partidas);
                
                atualizarGraficoBarras(comboAnalise.getSelectedItem().toString());
                
                Map<String, List<Integer>> evolucaoGeral = AnalisadorDados.calcularEvolucaoTodosTimes(partidas);
                painelGraficoMultiLinha.setDados(evolucaoGeral);

                comboTimesPizza.removeAllItems();
                List<String> nomesDosTimes = times.keySet().stream().sorted().collect(Collectors.toList());
                nomesDosTimes.forEach(comboTimesPizza::addItem);
                comboTimesPizza.setEnabled(true);

                if (!nomesDosTimes.isEmpty()) {
                    atualizarGraficoPizza(nomesDosTimes.get(0));
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar ou processar o arquivo: " + ex.getMessage(), "Erro de Arquivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void atualizarGraficoBarras(String tipo) {
        if (times == null) return;
        
        Function<Time, Integer> func;
        switch (tipo) {
            case "Classificacao Geral": func = Time::getPontos; break;
            case "Classificacao Mandante": func = Time::getPontosCasa; break;
            case "Classificacao Visitante": func = Time::getPontosFora; break;
            case "Classificacao Gols-Pro": func = Time::getGolsPro; break;
            case "Classificacao Gols Contra": func = Time::getGolsContra; break;
            case "Classificacao Saldo de Gols": func = Time::getSaldoDeGols; break;
            default: func = t -> 0;
        }
        
        String tituloEixoY;
        if (tipo.contains("Gols")) {
            tituloEixoY = "Gols";
        } else if (tipo.contains("Saldo")) {
            tituloEixoY = "Saldo de Gols";
        } else {
            tituloEixoY = "Pontos"; 
        }

        Map<String, Integer> dadosGrafico = new HashMap<>();
        for (Time t : times.values()) {
            dadosGrafico.put(t.getNome(), func.apply(t));
        }
        
        painelGraficoBarras.setDados(dadosGrafico, tipo, tituloEixoY);
    }

    private void atualizarGraficoPizza(String nomeTime) {
        if (partidas == null || nomeTime == null) return;
        
        AnalisadorDados.ResultadoTime resultados = AnalisadorDados.calcularResultados(nomeTime, partidas);
        painelGraficoPizza.setDados(resultados, nomeTime);
    }
}
