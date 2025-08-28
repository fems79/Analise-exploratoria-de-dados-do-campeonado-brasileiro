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

    public String getMandante() { return mandante; }
    public String getVisitante() { return visitante; }
    public int getGolsMandante() { return golsMandante; }
    public int getGolsVisitante() { return golsVisitante; }

    public int[] getPontos() {
        if (golsMandante > golsVisitante) return new int[]{3, 0};
        else if (golsVisitante > golsMandante) return new int[]{0, 3};
        else return new int[]{1, 1};
    }
}
