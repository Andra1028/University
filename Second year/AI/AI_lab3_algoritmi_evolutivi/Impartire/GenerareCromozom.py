from random import random

import numpy as np


def genereaza_cromozom(lungime_noduri, Adj):
    chrom = np.array([], dtype=int)  # aici vom stoca cromozomul
    for x in range(lungime_noduri):
        rand = np.random.randint(0, lungime_noduri)  # generam un numar random intre 0 si lungime_noduri
        # adj asigura faptul ca genele cromozomului sunt conectate intr-o retea
        # nu pot fi alese perechi de gene care nu sunt conectate
        pasi=lungime_noduri
        while Adj[x, rand] != 1 and pasi>0:  # cat timp in matricea de adiacenta elem de pe x si rand este 0
            rand = np.random.randint(0, lungime_noduri)  # generam alt numar random
            pasi=pasi-1
        chrom = np.append(chrom, rand)
    return chrom  # cromozomul generat de functie


def genereaza_cromozom2(lungime_noduri, Adj):
    indecsi = np.random.permutation(lungime_noduri)
    chrom = np.empty(lungime_noduri, dtype=int)
    i = 0
    print(Adj)
    while i < lungime_noduri:
        x = indecsi[i]
        non_zero_indices = np.nonzero(Adj[x])[0]
        if len(non_zero_indices) == 0:
            break
        rand = np.random.choice(non_zero_indices)
        chrom[x] = rand
        i += 1
    return chrom
