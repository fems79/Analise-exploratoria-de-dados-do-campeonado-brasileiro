public class Time {
    private final String nome;
    private int pontos = 0;
    private int pontosCasa = 0;
    private int pontosFora = 0;
    private int golsPro = 0;
    private int golsContra = 0;

    public Time(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
    public int getPontos() { return pontos; }
    public int getPontosCasa() { return pontosCasa; }
    public int getPontosFora() { return pontosFora; }
    public int getGolsPro() { return golsPro; }
    public int getGolsContra() { return golsContra; }
    public int getSaldoDeGols() { return golsPro - golsContra; }

    public void atualizar(Partida p) {
        if (p.getMandante().equals(nome)) {
            int pts = p.getPontos()[0];
            pontos += pts;
            pontosCasa += pts;
            golsPro += p.getGolsMandante();
            golsContra += p.getGolsVisitante();
        } else if (p.getVisitante().equals(nome)) {
            int pts = p.getPontos()[1];
            pontos += pts;
            pontosFora += pts;
            golsPro += p.getGolsVisitante();
            golsContra += p.getGolsMandante();
        }
    }
}
