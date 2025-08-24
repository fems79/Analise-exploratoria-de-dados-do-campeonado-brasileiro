import tkinter as tk
from tkinter import filedialog, messagebox
import csv
from collections import defaultdict

class Partida:
    def __init__(self, data, mandante, visitante, gols_mandante, gols_visitante):
        self.mandante = mandante
        self.visitante = visitante
        self.gols_mandante = int(gols_mandante)
        self.gols_visitante = int(gols_visitante)

    def get_vencedor(self):
        if self.gols_mandante > self.gols_visitante:
            return self.mandante
        elif self.gols_visitante > self.gols_mandante:
            return self.visitante
        else:
            return "Empate"

class AnalisadorDados:
    @staticmethod
    def ler_csv(caminho_arquivo):
        partidas = []
        try:
            with open(caminho_arquivo, mode='r', encoding='utf-8') as f:
                leitor = csv.DictReader(f)
                for linha in leitor:
                    partida = Partida(
                        linha['data'],
                        linha['mandante'],
                        linha['visitante'],
                        linha['gols_mandante'],
                        linha['gols_visitante']
                    )
                    partidas.append(partida)
        except FileNotFoundError:
            messagebox.showerror("Erro", f"Arquivo não encontrado: {caminho_arquivo}")
            return []
        except Exception as e:
            messagebox.showerror("Erro de Leitura", f"Ocorreu um erro ao ler o CSV: {e}")
            return []
        return partidas

    @staticmethod
    def calcular_vitorias_por_time(partidas):
        vitorias = defaultdict(int)
        for p in partidas:
            vencedor = p.get_vencedor()
            if vencedor != "Empate":
                vitorias[vencedor] += 1
        return dict(sorted(vitorias.items(), key=lambda item: item[1], reverse=True))

class TelaPrincipal(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Análise do Campeonato Brasileiro")
        self.geometry("850x500")

        frame_botoes = tk.Frame(self)
        frame_botoes.pack(pady=10)

        btn_carregar = tk.Button(frame_botoes, text="Carregar CSV", command=self.carregar_e_exibir_dados)
        btn_carregar.pack()

        self.canvas = tk.Canvas(self, bg='white')
        self.canvas.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
    def carregar_e_exibir_dados(self):
        caminho_arquivo = filedialog.askopenfilename(
            title="Selecione o arquivo CSV",
            filetypes=[("Arquivos CSV", "*.csv")]
        )
        if not caminho_arquivo:
            return

        partidas = AnalisadorDados.ler_csv(caminho_arquivo)
        if partidas:
            vitorias = AnalisadorDados.calcular_vitorias_por_time(partidas)
            self.desenhar_grafico_barras("Vitórias por Time", vitorias)

    def desenhar_grafico_barras(self, titulo, dados):
        self.canvas.delete("all") 
        
        if not dados:
            self.canvas.create_text(400, 200, text="Nenhum dado para exibir.", font=("Arial", 16))
            return

        margem_x = 60
        margem_y = 50
        largura_canvas = self.canvas.winfo_width()
        altura_canvas = self.canvas.winfo_height()
        altura_grafico = altura_canvas - 2 * margem_y
        
        self.canvas.create_text(largura_canvas / 2, 20, text=titulo, font=("Arial", 16, "bold"))
        
        valor_max = max(dados.values())
        num_barras = len(dados)
        
        largura_total_barras = largura_canvas - 2 * margem_x
        largura_barra = (largura_total_barras / num_barras) * 0.7
        espaco_barra = (largura_total_barras / num_barras) * 0.3
        
        x = margem_x
        for time, vitorias in dados.items():
            altura_barra = (vitorias / valor_max) * altura_grafico
            
            x0 = x
            y0 = altura_canvas - margem_y - altura_barra
            x1 = x + largura_barra
            y1 = altura_canvas - margem_y
            
            self.canvas.create_rectangle(x0, y0, x1, y1, fill="royalblue")
            
            self.canvas.create_text(x + largura_barra / 2, y0 - 10, text=str(vitorias))
            
            self.canvas.create_text(x + largura_barra / 2, y1 + 10, text=time, anchor=tk.N)
            
            x += largura_barra + espaco_barra

if __name__ == "__main__":
    app = TelaPrincipal()
    app.mainloop()
