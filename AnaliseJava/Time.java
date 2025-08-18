public class Time {
    private String nome;
    private int vitorias = 0;
    private int empates = 0;
    private int derrotas = 0;
    private int golsPro = 0;
    private int golsContra = 0;
    private int pontos = 0;

    private int jogosCasa = 0;
    private int pontosCasa = 0;
    private int vitoriasCasa = 0;
    private int derrotasCasa = 0;

    private int jogosFora = 0;
    private int pontosFora = 0;
    private int vitoriasFora = 0;
    private int derrotasFora = 0;

    public Time(String nome) {
        this.nome = nome;
    }

    public void registrarPartida(boolean emCasa, int golsFeitos, int golsSofridos) {
        this.golsPro += golsFeitos;
        this.golsContra += golsSofridos;

        if (emCasa) {
            jogosCasa++;
        } else {
            jogosFora++;
        }

        if (golsFeitos > golsSofridos) {
            this.vitorias++;
            this.pontos += 3;
            if (emCasa) {
                this.vitoriasCasa++;
                this.pontosCasa += 3;
            } else {
                this.vitoriasFora++;
                this.pontosFora += 3;
            }
        } else if (golsFeitos == golsSofridos) {
            this.empates++;
            this.pontos += 1;
            if (emCasa) {
                this.pontosCasa += 1;
            } else {
                this.pontosFora += 1;
            }
        } else {
            this.derrotas++;
            if (emCasa) {
                this.derrotasCasa++;
            } else {
                this.derrotasFora++;
            }
        }
    }

    // --- Métodos "Getters" para acessar os dados encapsulados ---
    public String getNome() { return nome; }
    public int getPontos() { return pontos; }
    public int getVitorias() { return vitorias; }
    public int getEmpates() { return empates; }
    public int getDerrotas() { return derrotas; }
    public int getGolsPro() { return golsPro; }
    public int getGolsContra() { return golsContra; }
    public int getJogos() { return vitorias + empates + derrotas; }
    public int getSaldoDeGols() { return golsPro - golsContra; }
    public int getPontosCasa() { return pontosCasa; }
    public int getJogosCasa() { return jogosCasa; }
    public int getPontosFora() { return pontosFora; }
    public int getJogosFora() { return jogosFora; }
}
