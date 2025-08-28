import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PainelGrafico extends JPanel {
    private Map<String, Integer> dados;
    private String tituloGrafico = "Gráfico";
    private String tituloEixoY = "Valor";

    public PainelGrafico() {
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(Color.WHITE);
    }

    public void setDados(Map<String, Integer> dados, String tituloGrafico, String tituloEixoY) {
        this.tituloGrafico = tituloGrafico;
        this.tituloEixoY = tituloEixoY;
        
        this.dados = dados.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        int larguraBarra = 50;
        int espacoEntreBarras = 20;
        int margemX = 80;
        int totalBarras = this.dados.size();
        int larguraTotal = margemX * 2 + totalBarras * larguraBarra + (totalBarras - 1) * espacoEntreBarras;
        this.setPreferredSize(new Dimension(larguraTotal, 400));

        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dados == null || dados.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int margemX = 80;
        int margemY = 50;
        int alturaDisponivel = getHeight() - 2 * margemY;
        
        int valorMaximoDados = dados.values().stream().mapToInt(Math::abs).max().orElse(1);
        
        int limiteEixoY = (int) (Math.ceil(valorMaximoDados / 10.0) * 10);
        if (limiteEixoY == 0) limiteEixoY = 10;
        if (valorMaximoDados > limiteEixoY - (limiteEixoY/4)) { 
             limiteEixoY += 10;
        }

        int numeroDeMarcasY = 10;
        for (int i = 0; i <= numeroDeMarcasY; i++) {
            int valorDaMarca = (i * limiteEixoY) / numeroDeMarcasY;
            int y = getHeight() - margemY - (i * alturaDisponivel) / numeroDeMarcasY;
            
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(margemX, y, getWidth() - margemX, y); 
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(valorDaMarca), margemX - 40, y + 4);
        }

        g2.setColor(Color.BLACK);
        g2.drawLine(margemX, getHeight() - margemY, margemX, margemY);
        
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        int larguraTitulo = g2.getFontMetrics().stringWidth(this.tituloGrafico);
        g2.drawString(this.tituloGrafico, (getWidth() - larguraTitulo) / 2, 30);
        
        AffineTransform oldTransform = g2.getTransform();
        g2.rotate(-Math.PI / 2);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        int larguraTituloY = g2.getFontMetrics().stringWidth(this.tituloEixoY);
        g2.drawString(this.tituloEixoY, -((getHeight() + larguraTituloY) / 2), 20);
        g2.setTransform(oldTransform);

        int larguraBarra = 50;
        int espacoEntreBarras = 20;
        int x = margemX + espacoEntreBarras; 
        for (Map.Entry<String, Integer> entry : dados.entrySet()) {
            int valor = entry.getValue();
            
            int alturaBarra = (int) (((double) Math.abs(valor) / limiteEixoY) * alturaDisponivel);

            g2.setColor(valor >= 0 ? Color.decode("#3498db") : Color.RED);
            g2.fillRect(x, getHeight() - margemY - alturaBarra, larguraBarra, alturaBarra);

            g2.setColor(Color.BLACK);
            String nome = entry.getKey();
            int nomeLargura = g2.getFontMetrics().stringWidth(nome);
            g2.drawString(nome, x + larguraBarra / 2 - nomeLargura / 2, getHeight() - margemY + 15);
            g2.drawString(String.valueOf(valor), x + larguraBarra / 2 - 5, getHeight() - margemY - alturaBarra - 5);

            x += larguraBarra + espacoEntreBarras;
        }
    }
}
