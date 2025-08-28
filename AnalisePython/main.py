import tkinter as tk
from tkinter import ttk, filedialog, messagebox
import csv
from collections import defaultdict
from dataclasses import dataclass
import math
import colorsys

@dataclass
class ResultadoTime:
    vitorias: int
    empates: int
    derrotas: int

@dataclass
class Partida:
    mandante: str
    visitante: str
    gols_mandante: int
    gols_visitante: int

    def get_pontos(self):
        if self.gols_mandante > self.gols_visitante:
            return 3, 0
        elif self.gols_visitante > self.gols_mandante:
            return 0, 3
        else:
            return 1, 1

class Time:
    def __init__(self, nome):
        self.nome = nome
        self.pontos = 0
        self.pontos_casa = 0
        self.pontos_fora = 0
        self.gols_pro = 0
        self.gols_contra = 0

    @property
    def saldo_de_gols(self):
        return self.gols_pro - self.gols_contra

    def atualizar(self, partida: Partida):
        pontos_mandante, pontos_visitante = partida.get_pontos()
        if partida.mandante == self.nome:
            self.pontos += pontos_mandante
            self.pontos_casa += pontos_mandante
            self.gols_pro += partida.gols_mandante
            self.gols_contra += partida.gols_visitante
        elif partida.visitante == self.nome:
            self.pontos += pontos_visitante
            self.pontos_fora += pontos_visitante
            self.gols_pro += partida.gols_visitante
            self.gols_contra += partida.gols_mandante


class AnalisadorDados:
    @staticmethod
    def ler_csv(caminho_arquivo):
        partidas = []
        with open(caminho_arquivo, mode='r', encoding='utf-8') as f:
            leitor = csv.DictReader(f)
            for linha in leitor:
                partidas.append(Partida(
                    mandante=linha['mandante'],
                    visitante=linha['visitante'],
                    gols_mandante=int(linha['gols_mandante']),
                    gols_visitante=int(linha['gols_visitante'])
                ))
        return partidas

    @staticmethod
    def gerar_times(partidas):
        times = {}
        for p in partidas:
            if p.mandante not in times: times[p.mandante] = Time(p.mandante)
            if p.visitante not in times: times[p.visitante] = Time(p.visitante)
            times[p.mandante].atualizar(p)
            times[p.visitante].atualizar(p)
        return times

    @staticmethod
    def calcular_evolucao_todos_times(partidas):
        evolucao_geral = defaultdict(list)
        pontos_atuais = defaultdict(int)
        todos_nomes_times = {p.mandante for p in partidas} | {p.visitante for p in partidas}
        for nome in todos_nomes_times:
            pontos_atuais[nome] = 0

        for p in partidas:
            pontos_mandante, pontos_visitante = p.get_pontos()
            pontos_atuais[p.mandante] += pontos_mandante
            evolucao_geral[p.mandante].append(pontos_atuais[p.mandante])
            pontos_atuais[p.visitante] += pontos_visitante
            evolucao_geral[p.visitante].append(pontos_atuais[p.visitante])
        return dict(evolucao_geral)

    @staticmethod
    def calcular_resultados(nome_time, partidas):
        v, e, d = 0, 0, 0
        for p in partidas:
            if p.mandante == nome_time or p.visitante == nome_time:
                pontos_m, pontos_v = p.get_pontos()
                eh_mandante = p.mandante == nome_time
                if (eh_mandante and pontos_m == 3) or (not eh_mandante and pontos_v == 3):
                    v += 1
                elif (eh_mandante and pontos_m == 1) or (not eh_mandante and pontos_v == 1):
                    e += 1
                else:
                    d += 1
        return ResultadoTime(v, e, d)


class PainelGrafico(tk.Canvas):
    def __init__(self, master, **kwargs):
        super().__init__(master, bg='white', **kwargs)
        self.dados = {}
        self.titulo_grafico = "Gráfico"
        self.titulo_eixo_y = "Valor"

    def set_dados(self, dados, titulo_grafico, titulo_eixo_y):
        self.dados = dict(sorted(dados.items(), key=lambda item: item[1], reverse=True))
        self.titulo_grafico = titulo_grafico
        self.titulo_eixo_y = titulo_eixo_y
        self.desenhar()

    def desenhar(self):
        self.delete("all")
        if not self.dados: return
        
        self.update_idletasks()

        margem_x, margem_y = 80, 50
        altura_disponivel = self.winfo_height() - 2 * margem_y
        
        valor_max_dados = max(abs(v) for v in self.dados.values()) if self.dados else 1
        limite_eixo_y = math.ceil(valor_max_dados / 10.0) * 10
        if limite_eixo_y == 0: limite_eixo_y = 10
        if valor_max_dados > limite_eixo_y - (limite_eixo_y / 4): limite_eixo_y += 10
        
        self.create_text(self.winfo_width() / 2, 30, text=self.titulo_grafico, font=("Arial", 16, "bold"))
        self.create_text(20, self.winfo_height() / 2, text=self.titulo_eixo_y, angle=90, font=("Arial", 12))
        
        for i in range(11):
            y = self.winfo_height() - margem_y - (i * altura_disponivel) / 10
            self.create_line(margem_x, y, self.winfo_width() - margem_x, y, fill="lightgray")
            valor_marca = round(i * limite_eixo_y / 10)
            self.create_text(margem_x - 10, y, text=str(valor_marca), anchor="e")
        
        largura_barra, espaco_barra = 50, 20
        x = margem_x + espaco_barra
        for nome, valor in self.dados.items():
            altura_barra = (abs(valor) / limite_eixo_y) * altura_disponivel if limite_eixo_y > 0 else 0
            cor = "#3498db" if valor >= 0 else "red"
            y0 = self.winfo_height() - margem_y - altura_barra
            self.create_rectangle(x, y0, x + largura_barra, self.winfo_height() - margem_y, fill=cor, outline="")
            self.create_text(x + largura_barra/2, self.winfo_height() - margem_y + 10, text=nome)
            self.create_text(x + largura_barra/2, y0 - 10, text=str(valor))
            x += largura_barra + espaco_barra

class PainelPizza(tk.Canvas):
    def __init__(self, master, **kwargs):
        super().__init__(master, bg='white', **kwargs)
        self.resultados = None
        self.nome_time = ""
        self.cores = {"v": "#228B22", "e": "#FFD700", "d": "#DC143C"}

    def set_dados(self, resultados, nome_time):
        self.resultados = resultados
        self.nome_time = nome_time
        self.desenhar()

    def desenhar(self):
        self.delete("all")
        if not self.resultados: return
        self.update_idletasks()

        total = self.resultados.vitorias + self.resultados.empates + self.resultados.derrotas
        if total == 0: return

        diametro = min(self.winfo_width(), self.winfo_height()) - 100
        x, y = (self.winfo_width() - diametro) / 2, (self.winfo_height() - diametro) / 2
        
        angulo_v = self.resultados.vitorias / total * 360
        angulo_e = self.resultados.empates / total * 360
        
        self.create_arc(x, y, x+diametro, y+diametro, start=0, extent=angulo_v, fill=self.cores["v"], outline="white", style=tk.PIESLICE)
        self.create_arc(x, y, x+diametro, y+diametro, start=angulo_v, extent=angulo_e, fill=self.cores["e"], outline="white", style=tk.PIESLICE)
        self.create_arc(x, y, x+diametro, y+diametro, start=angulo_v+angulo_e, extent=360-(angulo_v+angulo_e), fill=self.cores["d"], outline="white", style=tk.PIESLICE)

        self.create_text(20, 30, text=f"Desempenho: {self.nome_time}", font=("Arial", 16, "bold"), anchor="w")

class PainelMultiLinha(tk.Canvas):
    def __init__(self, master, **kwargs):
        super().__init__(master, bg='white', **kwargs)
        self.dados_evolucao, self.cores_dos_times, self.time_selecionado, self.legenda_bounds = {}, {}, None, {}
        self.bind("<Button-1>", self._on_mouse_click)
        self.bind("<Motion>", self._on_mouse_move)

    def set_dados(self, dados_evolucao):
        self.dados_evolucao, self.time_selecionado = dados_evolucao, None
        self._gerar_cores()
        self.desenhar()
        
    def _gerar_cores(self):
        self.cores_dos_times.clear()
        total = len(self.dados_evolucao)
        if total == 0: return
        for i, nome in enumerate(self.dados_evolucao.keys()):
            r, g, b = colorsys.hsv_to_rgb(i / total, 0.85, 0.9)
            self.cores_dos_times[nome] = f"#{int(r*255):02x}{int(g*255):02x}{int(b*255):02x}"
            
    def _on_mouse_click(self, event):
        clicado = None
        for nome, (x1, y1, x2, y2) in self.legenda_bounds.items():
            if x1 <= event.x <= x2 and y1 <= event.y <= y2:
                clicado = nome
                break
        self.time_selecionado = clicado if self.time_selecionado != clicado else None
        self.desenhar()

    def _on_mouse_move(self, event):
        cursor = ""
        for x1, y1, x2, y2 in self.legenda_bounds.values():
            if x1 <= event.x <= x2 and y1 <= event.y <= y2: cursor = "hand2"; break
        self.config(cursor=cursor)
        
    def desenhar(self):
        self.delete("all")
        if not self.dados_evolucao: return
        self.update_idletasks()
        
        max_pontos = max(max(v) for v in self.dados_evolucao.values() if v) if self.dados_evolucao else 0
        max_jogos = max(len(v) for v in self.dados_evolucao.values()) if self.dados_evolucao else 0
        limite_eixo_y = math.ceil(max_pontos / 10.0) * 10
        if limite_eixo_y == 0: limite_eixo_y = 10
        if max_pontos > limite_eixo_y - (limite_eixo_y / 4): limite_eixo_y += 10
        
        self._desenhar_eixos_e_grade(limite_eixo_y, max_jogos)

        padding, label_padding, legenda_width = 50, 25, 150
        altura_disponivel = self.winfo_height() - 2 * padding - label_padding
        largura_disponivel = self.winfo_width() - 2 * padding - label_padding - legenda_width
        x_scale = largura_disponivel / (max_jogos - 1) if max_jogos > 1 else 0
        y_scale = altura_disponivel / limite_eixo_y if limite_eixo_y > 0 else 0

        for nome, pontos in self.dados_evolucao.items():
            cor, largura_linha, cor_linha = self.cores_dos_times.get(nome, "black"), 1.0, "lightgray"
            if self.time_selecionado is None or nome == self.time_selecionado:
                largura_linha, cor_linha = 3.5, cor
            coords = [item for i, p in enumerate(pontos) for item in (padding + label_padding + i * x_scale, self.winfo_height() - padding - label_padding - p * y_scale)]
            if len(coords) >= 4: self.create_line(coords, fill=cor_linha, width=largura_linha)
        
        self._desenhar_legenda()

    def _desenhar_eixos_e_grade(self, limite_eixo_y, max_jogos):
        padding, label_padding, legenda_width, largura, altura = 50, 25, 150, self.winfo_width(), self.winfo_height()
        self.create_line(padding + label_padding, altura - padding - label_padding, largura - padding - legenda_width, altura - padding - label_padding)
        self.create_line(padding + label_padding, altura - padding - label_padding, padding + label_padding, padding)
        self.create_text(20, altura / 2, text="Pontos Acumulados", angle=90, font=("Arial", 12))
        self.create_text((largura - legenda_width) / 2, altura - 15, text="Jogos Disputados", font=("Arial", 12))
        
        for i in range(11):
            valor, y = (i * limite_eixo_y) / 10, altura - padding - label_padding - (i * (altura - 2 * padding - label_padding) / 10)
            self.create_line(padding + label_padding, y, largura - padding - legenda_width, y, fill="lightgray")
            self.create_text(padding, y, text=f"{valor:.0f}", anchor="e")

        if max_jogos > 1:
            for i in range(max_jogos):
                x = padding + label_padding + (i * (largura - 2 * padding - label_padding - legenda_width) / (max_jogos-1))
                self.create_line(x, altura - padding - label_padding, x, padding, fill="lightgray")
                self.create_text(x, altura - padding - label_padding + 10, text=str(i + 1))

    def _desenhar_legenda(self):
        self.legenda_bounds.clear()
        x, y, i = self.winfo_width() - 140, 50, 0
        self.create_text(x, y - 20, text="Legenda", font=("Arial", 12, "bold"), anchor="w")
        self.create_text(x, y - 5, text="(Clique para destacar)", font=("Arial", 10, "italic"), anchor="w")

        for nome in sorted(self.dados_evolucao.keys()):
            cor, y_pos = self.cores_dos_times.get(nome, "black"), y + (i * 15)
            self.create_rectangle(x, y_pos, x+10, y_pos+10, fill=cor, outline="")
            font_weight = "bold" if nome == self.time_selecionado else "normal"
            self.create_text(x+15, y_pos+5, text=nome, anchor="w", font=("Arial", 11, font_weight))
            self.legenda_bounds[nome] = (x, y_pos, self.winfo_width(), y_pos + 12)
            i += 1

class App(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Análise do Campeonato Brasileiro (Python)")
        self.geometry("900x650")

        self.partidas, self.times = [], {}

        top_frame = ttk.Frame(self); top_frame.pack(pady=10)
        ttk.Button(top_frame, text="Carregar CSV", command=self.carregar_dados).pack()

        notebook = ttk.Notebook(self); notebook.pack(expand=True, fill="both", padx=10, pady=10)
        
        aba1 = ttk.Frame(notebook); notebook.add(aba1, text="Classificação Geral")
        self.combo_analise = ttk.Combobox(aba1, values=["Classificacao Geral", "Classificacao Mandante", "Classificacao Visitante", "Classificacao Gols-Pro", "Classificacao Gols Contra", "Classificacao Saldo de Gols"]); self.combo_analise.pack(pady=5)
        self.combo_analise.set("Classificacao Geral"); self.combo_analise.bind("<<ComboboxSelected>>", self.atualizar_grafico_barras)
        self.painel_barras = PainelGrafico(aba1); self.painel_barras.pack(expand=True, fill="both")

        aba2 = ttk.Frame(notebook); notebook.add(aba2, text="Comparativo de Evolução")
        self.painel_multilinha = PainelMultiLinha(aba2); self.painel_multilinha.pack(expand=True, fill="both")

        aba3 = ttk.Frame(notebook); notebook.add(aba3, text="Desempenho por Time")
        frame_combo_pizza = ttk.Frame(aba3); frame_combo_pizza.pack(pady=5)
        ttk.Label(frame_combo_pizza, text="Selecione o Time:").pack(side="left", padx=5)
        self.combo_pizza = ttk.Combobox(frame_combo_pizza); self.combo_pizza.pack(side="left")
        self.combo_pizza.bind("<<ComboboxSelected>>", self.atualizar_grafico_pizza)
        self.painel_pizza = PainelPizza(aba3); self.painel_pizza.pack(expand=True, fill="both")

    def carregar_dados(self):
        caminho = filedialog.askopenfilename(filetypes=[("Arquivos CSV", "*.csv")])
        if not caminho: return
        try:
            self.partidas = AnalisadorDados.ler_csv(caminho)
            self.times = AnalisadorDados.gerar_times(self.partidas)
            
            nomes_times = sorted(self.times.keys())
            self.combo_pizza["values"] = nomes_times
            
            def atualizar_todos_graficos():
                self.atualizar_grafico_barras()
                
                self.painel_multilinha.set_dados(AnalisadorDados.calcular_evolucao_todos_times(self.partidas))
                
                if nomes_times:
                    self.combo_pizza.set(nomes_times[0])
                    self.atualizar_grafico_pizza()

            self.after(50, atualizar_todos_graficos)
        except Exception as e:
            messagebox.showerror("Erro", f"Ocorreu um erro ao processar o arquivo:\n{e}")

    def atualizar_grafico_barras(self, event=None):
        if not self.times: return
        tipo = self.combo_analise.get()
        func = {"Classificacao Geral": lambda t: t.pontos, "Classificacao Mandante": lambda t: t.pontos_casa, "Classificacao Visitante": lambda t: t.pontos_fora, "Classificacao Gols-Pro": lambda t: t.gols_pro, "Classificacao Gols Contra": lambda t: t.gols_contra, "Classificacao Saldo de Gols": lambda t: t.saldo_de_gols}.get(tipo, lambda t: 0)
        dados = {nome: func(time) for nome, time in self.times.items()}
        titulo_eixo_y = "Pontos"
        if "Gols" in tipo: titulo_eixo_y = "Gols"
        elif "Saldo" in tipo: titulo_eixo_y = "Saldo de Gols"
        self.painel_barras.set_dados(dados, tipo, titulo_eixo_y)

    def atualizar_grafico_pizza(self, event=None):
        if not self.partidas: return
        nome_time = self.combo_pizza.get()
        if not nome_time: return
        resultados = AnalisadorDados.calcular_resultados(nome_time, self.partidas)
        self.painel_pizza.set_dados(resultados, nome_time)

if __name__ == "__main__":
    app = App()
    app.mainloop()
