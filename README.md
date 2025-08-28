1)PROBLEMA E SOLUÇÃO
Primeiramente,para realizar a análise dos dados do campeonato brasileiro,precisamos criar um código em JAVA e em PYTHON que lesse um arquivo de extensão ".csv" que a cada linha houvesse informações exatamente nessa ordem e separadas pelo delimitador da vírgula:Data,Time_Mandante,Time_Visitante,Gols_Mandante,Gols_Visitante.
Após a etapa de leitura,o programa fica responsável pela criação dos times e da criação das partidas,assim,atribuindo vitória(+3 pontos),empate(+1 ponto) ou derrota(+0 pontos) para os times a cada partida a partir da leitura da quantidade de Gols,também salvando se o time estava sendo o Mandante ou o Visitante naquela partida e criando uma base de dados para cada time para depois ser usado nos gráficos.
A partir de toda a base de dados criada,o programa começará a ordenar pela e exibirá na primeira aba a classificação geral(maior quantidade de pontos),classificação como mandante(maior quantidade de pontos em casa),classificação como visitante(maior quantidade de pontos fora de casa),classificação gols-pró(maior quantidade de gols feitos),classificação gols-contra(maior quantidade de gols sofridos),classificação saldo de gols(maior diferença entre gols feitos e gols sofridos) e criará um gráfico de barras retangular para cada situação dessa.
Já na segunda aba,o programa exibirá um gráfico de linhas que mostra a evolução dos pontos dos times ao longo do campeonato,mostrando um gráfico geral e,caso o usuário queira,selecionando uma linha específica para ver a evolução específica de um time.
Já na Terceira aba,o programa exibirá um gráfico de pizza que mostra o desempenho individual de cada time no campeonato mediante seleção do usuário,contabilizando o número de vitórias,empates,derrotas e representando através de porcentagens no gráfico.

2)PARADIGMA OO APLICADO EM PYTHON

3)EXECUÇÃO DO PROJETO EM JAVA
Todo o código foi estruturado com base no Java Swing e Java AWT que já vem na própria API do JAVA,logo,nada precisa ser baixado para rodar o código,basta apenas o usuário compilar o código e ter o arquivo de extensão ".csv" conforme os padrões esperados

4)EXECUÇÃO DO PROJETO EM PYTHON
Todo o código foi estruturado com base em Tkinter e dataclasses que já vem embutido no Python,logo,nada precisa ser baixado para rodar o código,basta apenas o usuário compilar o código e ter o arquivo de extensão ".csv" conforme os padrões esperados

5)Exemplo de Arquivo de entrada

foi i
