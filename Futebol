import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Comparator;
import java.util.Arrays;

class Time{
    String nome;
    int vitorias=0;int vitorias_casa=0;int vitorias_fora=0;
    int empates=0;
    int derrotas=0;int derrotas_casa=0;int derrotas_fora=0;
    int golsPro=0;
    int golsContra=0;
    int pontos=0;int pontos_casa=0;int pontos_fora=0;
    int jogos=0;int jogos_casa=0;int jogos_fora=0;
    int saldo_de_gols=golsPro-golsContra;

    Time(String nome){
        this.nome=nome;
    }
    ///resultado=>0 representa vitoria,1 representa empate e 2 representa derrota
    ///local=>0 representa em casa e 1 representa fora
    void Partidas(int resultado,int local,int golsPro,int golsContra){
        jogos+=1;
        if(resultado==0){
            vitorias+=1;
            pontos+=3;
            if(local==0){
                vitorias_casa+=1;
                pontos_casa+=3;
                jogos_casa+=1;
            }
            else{
                vitorias_fora+=1;
                pontos_fora+=3;
                jogos_fora+=1;
            }
        }
        else if(resultado==1){
            empates+=1;
            pontos+=1;
            if(local==0){
                pontos_casa+=1;
                jogos_casa+=1;
            }
            else{
                pontos_fora+=1;
                jogos_fora+=1;
            }
        }
        else if(resultado==2){
            derrotas+=1;
            if(local==0){
                derrotas_casa+=1;
                jogos_casa+=1;
            }
            else{
                derrotas_fora+=1;
                jogos_fora+=1;
            }
        }
        this.golsPro+=golsPro;
        this.golsContra+=golsContra;
    }
}
public class futebol{
    public static void main(String[] args) {
        Scanner entrada=new Scanner(System.in);
        System.out.println("digite o nome do arquivo:");
        String arquivo =entrada.next();
        String linha = "";
        ///limitações=>Inicial do nome sempre maiúsculo e o resto minusculo(exceção de siglas)
        ///sem acentuação pois em JAVA é melhor evitar
        /// se no arquivo nao tiver exatamente igual como tá aqui vai dar erro(tentei toLowerCase() mas da erro nos que tem "-")
        String nomes_times[] = {"Athletico-PR","Atletico-GO","Atletico-MG","Bahia","Botafogo",
        "RB Bragantino","Corinthians","Criciuma","Cruzeiro","Cuiaba","Flamengo","Fluminense",
        "Fortaleza","Gremio","Internacional","Juventude","Palmeiras","Sao Paulo","Vasco da Gama","Vitoria"};

     Time[]times=new Time[nomes_times.length];
     for(int i=0;i<nomes_times.length;i++){
        times[i]=new Time(nomes_times[i]);
     }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                String timeCasa = campos[0];
                String timeVisitante = campos[1];
                int golsCasa = Integer.parseInt(campos[2]);
                int golsVisitante = Integer.parseInt(campos[3]);
                int resultado_timeCasa;int resultado_timeFora;
                if(golsCasa>golsVisitante){
                   resultado_timeCasa=0;resultado_timeFora=2;
                }
                else if(golsCasa==golsVisitante){
                   resultado_timeCasa=1;resultado_timeFora=1;
                }
                else{
                    resultado_timeCasa=2;resultado_timeFora=0;
                }
                for(int i=0;i<nomes_times.length;i++){
                    if(timeCasa.equals(nomes_times[i])==true){
                         times[i].Partidas(resultado_timeCasa
                         ,0,golsCasa,golsVisitante);
                    }
                    if(timeVisitante.equals(nomes_times[i])==true){
                         times[i].Partidas(resultado_timeFora,1,golsVisitante,golsCasa);
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       Arrays.sort(times, Comparator.comparingInt((Time t) -> t.pontos).reversed());
       System.out.println("Classificao Geral:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Pontos:%d\n", times[i].nome,times[i].jogos,times[i].pontos);
}
System.out.println();

Arrays.sort(times, Comparator.comparingInt((Time t) -> t.pontos_casa).reversed());
    System.out.println("Classificao Mandante:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Pontos:%d\n", times[i].nome,times[i].jogos_casa,times[i].pontos_casa);
}
System.out.println();

Arrays.sort(times, Comparator.comparingInt((Time t) -> t.pontos_fora).reversed());
    System.out.println("Classificao Visitante:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Pontos:%d\n", times[i].nome,times[i].jogos_fora,times[i].pontos_fora);
}
System.out.println();

Arrays.sort(times, Comparator.comparingInt((Time t) -> t.golsPro).reversed());
    System.out.println("Classificao Gols-Pro:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Gols-Pro:%d\n", times[i].nome,times[i].jogos,times[i].golsPro);
}
System.out.println();

Arrays.sort(times, Comparator.comparingInt((Time t) -> t.golsContra).reversed());
    System.out.println("Classificao Gols Contra:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Gols-Contra:%d\n", times[i].nome,times[i].jogos,times[i].golsContra);
}
System.out.println();

Arrays.sort(times, Comparator.comparingInt((Time t) -> t.saldo_de_gols).reversed());
    System.out.println("Classificao Saldo de Gols:");
for (int i = 0; i < nomes_times.length; i++) {
    System.out.printf("%s ---Jogos:%d---Saldo-Gols:%d\n", times[i].nome,times[i].jogos,times[i].saldo_de_gols);
}
System.out.println();
        entrada.close();
    }
}
