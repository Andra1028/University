import networkx as nx
import numpy as np
import pandas as pd
import os
import matplotlib.pyplot as plt

from Impartire.GenerareCromozom import genereaza_cromozom
from Impartire.OperatoriGenetici import selectie, recombinare, mutatie
from Impartire.ScorComunitate import scor_comunitate
from Impartire.UtilsSubmultime import gaseste_submultime
from Impartire.GenerareCromozom import genereaza_cromozom2


def comunitate(noduri, muchii, nr_populatie=500, generatii=1000, r=1.5):
    graf = nx.Graph() #fac graful
    graf.add_nodes_from(noduri) #adaug nodurile
    graf.add_edges_from(muchii) #adaug muchiile
    Adj = nx.adjacency_matrix(graf) #creeaza matricea de adiacenta a grafului
    lungime = len(graf.nodes())

    #generam o populatie de cromozomi
    d = {"cromozom": [genereaza_cromozom(lungime,Adj) #construim un dictionar cu cheia cromozom
                   for n in range(nr_populatie)]} #cream un data frame din dictionar
    frame = pd.DataFrame(data=d)

    frame["submultimi"] = frame["cromozom"].apply(gaseste_submultime) #adaugam o coloana pentru submultimi
    frame["scor"] = frame.apply(
        lambda x: scor_comunitate(x["cromozom"], x["submultimi"], r, Adj), axis=1) #adaugam o coloana scor folosim functia de scor_comunitate

    #creaza o listă goală pentru fiecare dintre scorurile maxime, medii și minime pentru fiecare generație,
    # iar apoi calculează aceste scoruri pentru fiecare generație, efectuând selecție și
    # încrucișare la fiecare iterație
    
    max_scor = []
    med_scor = []
    min_scor = []
    gen = 0
    populatie = nr_populatie
    while gen < generatii:
        for i in range(int(np.floor(nr_populatie/10))):
            p1 = 0
            p2 = 0
            elite = frame.sort_values("scor", ascending=True)[
                int(np.floor(nr_populatie/10)):]
            #selectam doi indivizi parinti pe baza scorurilor lor
            p1 = selectie(elite)
            """p2 = selectie(elite)
            #aplicam recombinare pentru cei doi parinti cu probabilitatea de 0.8 de a se intampla
            c = recombinare(
                frame["cromozom"][p1], frame["cromozom"][p2], 0.8)
            if len(c) == 0:
                continue
            #verificam daca cromozomul obtinut a suferit o mutatie cu probabilitatea de 0.2
            c = mutatie(c, Adj, 0.2)
            #cautam submultimile cromozomului
            c_submultimi = gaseste_submultime(c)
            #calculam scorul comunitatilor
            c_cs = scor_comunitate(c, c_submultimi, r, Adj)
            #adaugam noul cromozom la frame
            frame.loc[populatie] = [c, c_submultimi, c_cs]
            populatie += 1
        sortat = frame.sort_values("scor", ascending=False) #sortam fram ul in ordine descrescatoare
        sterge = sortat.index[nr_populatie:]
        frame.drop(sterge, inplace=True) #stergem cei mai slabi indivizi
        # adaugam scorul maxim, minim si mediu al comunitatii in liste
        max_scor.append(sortat["scor"].max())
        med_scor.append(sortat["scor"].mean())
        min_scor.append(sortat["scor"].min())"""
        gen += 1
    sortatt = frame.sort_values("scor", ascending=False).index[0]

    # plot pentru progresul fiecarei comunitati in timpul algoritmului
    """plotProgres(max_scor,med_scor,min_scor)

    noduri_submultime = frame["submultimi"][sortatt] #se ia mai intai submultimea de noduri a celui mai bun cromozom gasit de algoritm
    noduri_lista = list(graf.nodes()) #toate nodurile
    rezultat = []
    for subs in noduri_submultime:
        subset = []
        for n in subs:
            subset.append(noduri_lista[n]) #adaugam in subset nodurile din comunitate
        rezultat.append(subset) #lista cu comunitati
    return rezultat"""



def plotProgres(max, mid, min):
    # plot pentru progresul fiecarei generatii in timpul algoritmului
    fig, ax = plt.subplots()
    ax.plot(max, label="Maxim")
    ax.plot(mid, label="Mediu")
    ax.plot(min, label="Minim")

    ax.set_xlabel("Generatie")
    ax.set_ylabel("Scor")
    ax.set_title("Progres")
    ax.legend()
    plt.show()

def plotNetwork(G, communities=[1, 1, 1, 1, 1, 1]):
    # to freeze the graph's view (networks uses a random view)
    np.random.seed(123)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(8, 8))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=300,
                           cmap=plt.cm.RdYlBu, node_color=communities)
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    plt.show()



def main():
    crtDir = os.getcwd()
    print("ok")
    filePath = os.path.join(crtDir, "data/given/dolphins", "dolphins.gml")
    G = nx.read_gml(filePath, label='id')
    print("ok")

    communities = comunitate(G.nodes(), G.edges())
    print("ok")
    nodeCommunities = [0] * len(G.nodes())
    """for i, community in enumerate(communities):
        for node in community:
            nodeCommunities[node] = i

    print("Communities: ", nodeCommunities)
    print("Nr. of communities: ", len(set(nodeCommunities)))"""
    print("termina")
    #plotNetwork(G, nodeCommunities)

main()