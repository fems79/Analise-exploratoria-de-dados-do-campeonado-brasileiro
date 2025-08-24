package AnaliseJava.Dados;
public class Partida {
    private final String mandante;
    private final String visitante;
    private final int golsMandante;
    private final int golsVisitante;

    public Partida(String mandante, String visitante, int golsMandante, int golsVisitante) {
        this.mandante = mandante;
        this.visitante = visitante;
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }

    public String getVencedor() {
        if (golsMandante > golsVisitante) {
            return mandante;
        } else if (golsVisitante > golsMandante) {
            return visitante;
        } else {
            return "Empate";
        }
    }
}
