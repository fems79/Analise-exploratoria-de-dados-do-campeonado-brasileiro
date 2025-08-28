import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import AnaliseJava.AnalisadorDados.ResultadoTime; // verificar o caminho correto na hora de executar

public class PainelPizza extends JPanel {
    private ResultadoTime resultados;
    private String nomeTime;

    private static final Color COR_VITORIA = new Color(34, 139, 34); 
    private static final Color COR_EMPATE = new Color(255, 215, 0);  
    private static final Color COR_DERROTA = new Color(220, 20, 60); 

    public PainelPizza() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));
    }

    public void setDados(ResultadoTime resultados, String nomeTime) {
        this.resultados = resultados;
        this.nomeTime = nomeTime;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (resultados == null) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int totalJogos = resultados.vitorias() + resultados.empates() + resultados.derrotas();
        if (totalJogos == 0) return;

        int anguloVitorias = (int) Math.round(((double) resultados.vitorias() / totalJogos) * 360);
        int anguloEmpates = (int) Math.round(((double) resultados.empates() / totalJogos) * 360);
        int anguloDerrotas = 360 - anguloVitorias - anguloEmpates; 

  
        int diametro = Math.min(getWidth(), getHeight()) - 100;
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;
        int anguloInicial = 0;

        g2.setColor(COR_VITORIA);
        g2.fillArc(x, y, diametro, diametro, anguloInicial, anguloVitorias);
        anguloInicial += anguloVitorias;

        g2.setColor(COR_EMPATE);
        g2.fillArc(x, y, diametro, diametro, anguloInicial, anguloEmpates);
        anguloInicial += anguloEmpates;

        g2.setColor(COR_DERROTA);
        g2.fillArc(x, y, diametro, diametro, anguloInicial, anguloDerrotas);

        desenharLegenda(g2, totalJogos);
    }

    private void desenharLegenda(Graphics2D g2, int totalJogos) {
        DecimalFormat df = new DecimalFormat("#.#");
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        g2.setColor(Color.BLACK);
        g2.drawString("Desempenho: " + this.nomeTime, 20, 30);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        int yPos = 60;

        g2.setColor(COR_VITORIA);
        g2.fillRect(20, yPos - 10, 15, 15);
        g2.setColor(Color.BLACK);
        double percVitorias = (double) resultados.vitorias() / totalJogos * 100;
        g2.drawString(String.format("Vitórias: %d (%s%%)", resultados.vitorias(), df.format(percVitorias)), 45, yPos + 5);
        
        yPos += 30;
        g2.setColor(COR_EMPATE);
        g2.fillRect(20, yPos - 10, 15, 15);
        g2.setColor(Color.BLACK);
        double percEmpates = (double) resultados.empates() / totalJogos * 100;
        g2.drawString(String.format("Empates: %d (%s%%)", resultados.empates(), df.format(percEmpates)), 45, yPos + 5);
        
        yPos += 30;
        g2.setColor(COR_DERROTA);
        g2.fillRect(20, yPos - 10, 15, 15);
        g2.setColor(Color.BLACK);
        double percDerrotas = (double) resultados.derrotas() / totalJogos * 100;
        g2.drawString(String.format("Derrotas: %d (%s%%)", resultados.derrotas(), df.format(percDerrotas)), 45, yPos + 5);
    }
}
