import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PainelMultiLinha extends JPanel {

    private Map<String, List<Integer>> dadosEvolucao;
    private Map<String, Color> coresDosTimes;
    private String timeSelecionado = null; 
    private final Map<String, Rectangle> legendaBounds; 
    private static final int PADDING = 50;
    private static final int LABEL_PADDING = 25;
    private static final int LEGENDA_WIDTH = 150;

    public PainelMultiLinha() {
        this.setBackground(Color.WHITE);
        this.coresDosTimes = new LinkedHashMap<>();
        this.legendaBounds = new LinkedHashMap<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String timeClicado = null;
                for (Map.Entry<String, Rectangle> entry : legendaBounds.entrySet()) {
                    if (entry.getValue().contains(e.getPoint())) {
                        timeClicado = entry.getKey();
                        break;
                    }
                }
                if (timeClicado != null && timeClicado.equals(timeSelecionado)) {
                    timeSelecionado = null;
                } else {
                    timeSelecionado = timeClicado;
                }
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                boolean sobreLegenda = false;
                for (Rectangle areaClicavel : legendaBounds.values()) {
                    if (areaClicavel.contains(e.getPoint())) {
                        sobreLegenda = true;
                        break;
                    }
                }
                if (sobreLegenda) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }

    public void setDados(Map<String, List<Integer>> dadosEvolucao) {
        this.dadosEvolucao = dadosEvolucao;
        this.timeSelecionado = null; 
        this.legendaBounds.clear(); 
        this.coresDosTimes.clear();
        int totalTimes = dadosEvolucao.size();
        int i = 0;
        for (String nomeTime : dadosEvolucao.keySet()) {
            float hue = (float) i / totalTimes;
            float saturation = 0.8f;
            float brightness = 0.9f;
            coresDosTimes.put(nomeTime, Color.getHSBColor(hue, saturation, brightness));
            i++;
        }
        this.setPreferredSize(new Dimension(1000, 600));
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (dadosEvolucao == null || dadosEvolucao.isEmpty()) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int maxPontosDados = getMaxPontos();
        int limiteEixoY = (int) (Math.ceil(maxPontosDados / 10.0) * 10);
        if (limiteEixoY == 0) limiteEixoY = 10;
        if (maxPontosDados > limiteEixoY - (limiteEixoY/4)) {
             limiteEixoY += 10;
        }

        desenharEixos(g2, limiteEixoY);

        double xScale = ((double) getWidth() - 2 * PADDING - LABEL_PADDING - LEGENDA_WIDTH) / (getMaxJogos() > 1 ? getMaxJogos() - 1 : 1);
        double yScale = ((double) getHeight() - 2 * PADDING - LABEL_PADDING) / limiteEixoY;

        for (Map.Entry<String, List<Integer>> entry : dadosEvolucao.entrySet()) {
            String nomeTime = entry.getKey();
            List<Integer> pontos = entry.getValue();
            Color corOriginal = coresDosTimes.get(nomeTime);

            if (timeSelecionado == null || nomeTime.equals(timeSelecionado)) {
                g2.setStroke(new BasicStroke(3.5f));
                g2.setColor(corOriginal);
            } else {
                g2.setStroke(new BasicStroke(1.0f));
                g2.setColor(new Color(corOriginal.getRed(), corOriginal.getGreen(), corOriginal.getBlue(), 70));
            }
            
            for (int i = 0; i < pontos.size() - 1; i++) {
                int x1 = (int) (i * xScale + PADDING + LABEL_PADDING);
                int y1 = getHeight() - PADDING - LABEL_PADDING - (int) (pontos.get(i) * yScale);
                int x2 = (int) ((i + 1) * xScale + PADDING + LABEL_PADDING);
                int y2 = getHeight() - PADDING - LABEL_PADDING - (int) (pontos.get(i + 1) * yScale);
                g2.drawLine(x1, y1, x2, y2);
            }
        }
        desenharLegenda(g2, getWidth() - LEGENDA_WIDTH + 10, PADDING);
    }
    
    private void desenharEixos(Graphics2D g2, int limiteEixoY) {
        int maxJogos = getMaxJogos();
        
        int numeroDeMarcasY = 10;
        for (int i = 0; i <= numeroDeMarcasY; i++) {
            int valorDaMarca = (i * limiteEixoY) / numeroDeMarcasY;
            int y = getHeight() - PADDING - LABEL_PADDING - (i * (getHeight() - 2 * PADDING - LABEL_PADDING)) / numeroDeMarcasY;
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(PADDING + LABEL_PADDING, y, getWidth() - PADDING - LEGENDA_WIDTH, y);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(valorDaMarca), PADDING, y + 4);
        }

        double xScale = ((double) getWidth() - 2 * PADDING - LABEL_PADDING - LEGENDA_WIDTH) / (maxJogos > 1 ? maxJogos - 1 : 1);
        for (int i = 0; i < maxJogos; i++) {
            int x = PADDING + LABEL_PADDING + (int)(i * xScale);
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(x, getHeight() - PADDING - LABEL_PADDING, x, PADDING);
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(i + 1), x - 3, getHeight() - PADDING - LABEL_PADDING + 20);
        }

        g2.setColor(Color.BLACK);
        g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING, PADDING + LABEL_PADDING, PADDING);
        g2.drawLine(PADDING + LABEL_PADDING, getHeight() - PADDING - LABEL_PADDING, getWidth() - PADDING - LEGENDA_WIDTH, getHeight() - PADDING - LABEL_PADDING);
        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        String tituloX = "Jogos Disputados";
        int larguraTituloX = g2.getFontMetrics().stringWidth(tituloX);
        g2.drawString(tituloX, (getWidth() - LEGENDA_WIDTH - larguraTituloX) / 2, getHeight() - 15);
        AffineTransform oldTransform = g2.getTransform();
        g2.rotate(-Math.PI / 2);
        String tituloY = "Pontos Acumulados";
        int larguraTituloY = g2.getFontMetrics().stringWidth(tituloY);
        g2.drawString(tituloY, -((getHeight() + larguraTituloY) / 2), 20);
        g2.setTransform(oldTransform);
    }
    
    private void desenharLegenda(Graphics2D g2, int x, int y) {
        legendaBounds.clear(); 
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.setColor(Color.BLACK);
        g2.drawString("Legenda", x, y - 15);
        
        g2.setFont(new Font("Arial", Font.ITALIC, 10));
        g2.setColor(Color.DARK_GRAY);
        g2.drawString("(Clique para destacar)", x, y);

        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        List<String> nomesTimes = new ArrayList<>(dadosEvolucao.keySet());
        Collections.sort(nomesTimes);

        int i = 0;
        int yOffset = y + 15;
        for (String nomeTime : nomesTimes) {
            Color corTime = coresDosTimes.get(nomeTime);
            int yPos = yOffset + (i * 15);
            
            g2.setColor(corTime);
            g2.fillRect(x, yPos, 10, 10);
            
            g2.setColor(Color.BLACK);
            if (nomeTime.equals(timeSelecionado)) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD));
            }
            g2.drawString(nomeTime, x + 15, yPos + 10);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN));

            legendaBounds.put(nomeTime, new Rectangle(x, yPos, LEGENDA_WIDTH, 12));
            i++;
        }
    }
    
    private int getMaxPontos() {
        if (dadosEvolucao == null) return 0;
        return dadosEvolucao.values().stream().flatMap(List::stream).mapToInt(v -> v).max().orElse(0);
    }
    
    private int getMaxJogos() {
        if (dadosEvolucao == null) return 0;
        return dadosEvolucao.values().stream().mapToInt(List::size).max().orElse(0);
    }
}
