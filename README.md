PROJETO ANÁLISE DE DADOS DO CAMPEONATO BRASILEIRO EM PYTHON E EM JAVA

1)PROBLEMA E SOLUÇÃO
1.1)LEITURA DO ARQUIVO DE ENTRADA
Primeiramente,para realizar a análise dos dados do campeonato brasileiro,precisamos criar um código em JAVA e em PYTHON que lesse um arquivo de extensão ".csv" que a cada linha houvesse informações exatamente nessa ordem e separadas pelo delimitador vírgula:Data,Time_Mandante,Time_Visitante,Gols_Mandante,Gols_Visitante.
1.2)CRIAÇÃO DA BASE DE DADOS
Após a etapa de leitura,o programa fica responsável pela criação dos times e da criação das partidas,assim,atribuindo vitória(+3 pontos),empate(+1 ponto) ou derrota(+0 pontos) para os times a cada partida a partir da leitura da quantidade de Gols,também salvando se o time estava sendo o Mandante ou o Visitante naquela partida e criando uma base de dados para cada time para depois ser usado nos gráficos.
1.3)CRIAÇÃO GRÁFICOS DE BARRA
A partir de toda a base de dados criada,o programa começará a ordenar pela e exibirá na primeira aba a classificação geral(maior quantidade de pontos),classificação como mandante(maior quantidade de pontos em casa),classificação como visitante(maior quantidade de pontos fora de casa),classificação gols-pró(maior quantidade de gols feitos),classificação gols-contra(maior quantidade de gols sofridos),classificação saldo de gols(maior diferença entre gols feitos e gols sofridos) e criará um gráfico de barras retangular para cada situação dessa.
1.4)CRIAÇÃO GRÁFICOS DE LINHA E DE PIZZA
Já na segunda aba,o programa exibirá um gráfico de linhas que mostra a evolução dos pontos dos times ao longo do campeonato,mostrando um gráfico geral e,caso o usuário queira,selecionando uma linha específica para ver a evolução específica de um time.
Já na Terceira aba,o programa exibirá um gráfico de pizza que mostra o desempenho individual de cada time no campeonato mediante seleção do usuário,contabilizando o número de vitórias,empates,derrotas e representando através de porcentagens no gráfico.

2)PARADIGMA OO APLICADO EM Python
2.1)Definição
A Orientação a Objetos é um paradigma de programação que organiza o código em torno de objetos que possuem atributos e métodos.Tanto Python quanto Java são linguagens que suportam OO, mas o fazem de formas diferentes,por isso,essas foram as linguagens utilizadas no projeto.

2.2) Características de OO em Python e Comparações com Java
2.2.1)Sintaxe concisa
Python permite classes muito mais simples, enquanto Java exige mais código (construtores, getters/setters, toString).
2.2.2)Tipagem dinâmica
Python não é necessário declarar tipos; em Java, sim.
2.2.3)Herança múltipla
permitida em Python,logo,uma classe pode herdar de várias classes,proibida em Java.
2.2.4)Atributos em tempo de execução
em Python, é possível adicionar atributos dinamicamente.
2.2.5)Dataclasses
Evitam repetição de código em python

2.3)Aplicação no projeto
No projeto, a OO foi aplicada por meio de dataclasses e modelagem de objetos:
Criamos a classe Time, com atributos como:nome,pontos,vitorias,empates,derrotas,gols_pro,gols_contra
Essa classe encapsula todos os dados relacionados a um time e pode conter métodos para atualizar estatísticas, calcular saldo de gols, etc.
Criamos a classe Partida, que recebe dois times (mandante e visitante), registra o resultado e atualiza automaticamente os atributos de cada time.
Além disso, há classes/funções responsáveis por gerar gráficos (linha, barra, pizza) a partir da base de dados dos objetos Time.
Essa modelagem orientada a objetos trouxe organização e modularidade para o projeto: cada responsabilidade ficou bem definida dentro de uma classe, facilitando manutenção e extensão do código.

3)EXECUÇÃO DO PROJETO EM JAVA
Todo o código foi estruturado com base no Java Swing e Java AWT que já vem na própria API do JAVA,logo,nada precisa ser baixado para rodar o código,basta apenas o usuário compilar o código e ter o arquivo de extensão ".csv" conforme os padrões esperados

4)EXECUÇÃO DO PROJETO EM PYTHON
Todo o código foi estruturado com base em Tkinter e dataclasses que já vem embutido no Python,logo,nada precisa ser baixado para rodar o código,basta apenas o usuário compilar o código e ter o arquivo de extensão ".csv" conforme os padrões esperados

5)Exemplo de Arquivo de entrada
O arquivo de entrada precisa seguir esse padrão para que o programa funcione conforme o esperado,o mesmo arquivo foi disponibilizado abaixo deste arquivo(README.md),entitulado "arquivo_teste.csv",basta que o usuário copie e cole em um bloco de notas e depois troque a extensão para ".csv"
*cada linha possui:data,nome_time_mandante,nome_time_visitante,gols_mandante,gols_visitante

13/04/2024,Flamengo,Palmeiras,2,1
13/04/2024,Fluminense,Atlético-MG,1,0
13/04/2024,São Paulo,Corinthians,0,2
13/04/2024,Grêmio,Santos,1,1
13/04/2024,Internacional,Fortaleza,2,2
13/04/2024,Atlético-GO,Cuiabá,0,1
13/04/2024,América-MG,Bahia,3,1
13/04/2024,Coritiba,Vasco,0,0
14/04/2024,Flamengo,Fluminense,2,2
14/04/2024,Palmeiras,São Paulo,1,0
14/04/2024,Atlético-MG,Grêmio,2,1
14/04/2024,Santos,Internacional,0,3
14/04/2024,Fortaleza,Atlético-GO,1,1
14/04/2024,Cuiabá,América-MG,0,2
14/04/2024,Bahia,Coritiba,1,1
14/04/2024,Vasco,Corinthians,0,2
15/04/2024,Flamengo,Atlético-MG,1,1
15/04/2024,Palmeiras,Santos,2,0
15/04/2024,Fluminense,Grêmio,0,1
15/04/2024,São Paulo,Internacional,1,2
15/04/2024,Coritiba,América-MG,0,0
15/04/2024,Bahia,Atlético-GO,1,2
15/04/2024,Vasco,Cuiabá,2,2
15/04/2024,Fortaleza,Corinthians,1,0
16/04/2024,Flamengo,Santos,3,1
16/04/2024,Palmeiras,Grêmio,1,2
16/04/2024,Fluminense,Internacional,2,1
16/04/2024,São Paulo,Atlético-GO,0,0
16/04/2024,Coritiba,Fortaleza,1,1
16/04/2024,Bahia,Cuiabá,2,0
16/04/2024,Vasco,América-MG,1,2
16/04/2024,Atlético-MG,Corinthians,3,0
17/04/2024,Flamengo,Grêmio,2,2
17/04/2024,Palmeiras,Internacional,0,1
17/04/2024,Fluminense,Atlético-GO,1,1
17/04/2024,São Paulo,Santos,2,0
17/04/2024,Coritiba,Atlético-MG,0,2
17/04/2024,Bahia,Vasco,1,1
17/04/2024,Fortaleza,América-MG,0,1
17/04/2024,Cuiabá,Corinthians,0,3
18/04/2024,Flamengo,Internacional,1,1
18/04/2024,Palmeiras,Atlético-GO,2,1
18/04/2024,Fluminense,Santos,1,0
18/04/2024,São Paulo,Grêmio,0,1
18/04/2024,Coritiba,Corinthians,0,2
18/04/2024,Bahia,Atlético-MG,1,2
18/04/2024,Fortaleza,Cuiabá,2,0
18/04/2024,Vasco,América-MG,1,1
19/04/2024,Flamengo,São Paulo,3,1
19/04/2024,Palmeiras,Fluminense,1,2
19/04/2024,Atlético-MG,Internacional,2,0
19/04/2024,Santos,Grêmio,1,1
19/04/2024,Fortaleza,Vasco,0,0
19/04/2024,Cuiabá,Bahia,1,1
19/04/2024,América-MG,Coritiba,2,0
19/04/2024,Corinthians,Atlético-GO,1,1
20/04/2024,Flamengo,Atlético-GO,2,1
20/04/2024,Palmeiras,América-MG,1,0
20/04/2024,Fluminense,Vasco,0,0
20/04/2024,São Paulo,Cuiabá,2,1
20/04/2024,Coritiba,Santos,1,2
20/04/2024,Bahia,Flamengo,0,3
20/04/2024,Fortaleza,Palmeiras,1,1
20/04/2024,Grêmio,Atlético-MG,0,1






