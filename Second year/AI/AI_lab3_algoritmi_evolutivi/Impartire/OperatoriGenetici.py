import numpy as np


def selectie(elite):
    rand = np.random.random_sample() # gen un nr aleator intre 0 si 1
    #rand va decide care dintre cromozomii elitisti vor fi selectati pentru generatia urmatoare
    sum_cs = np.sum(elite["scor"]) #calc suma scorilor comunitatilor pt fiecare crom elitist (suma coloanei 'scor)
    x = 0
    #selecteaza un cromozom pentru a contia in generatia urmatoare
    for i in elite.index:
        x += elite["scor"][i] #suma scorurilor comunitatilor pentru crom pana la acel punct
        prob = x/sum_cs #probabilitatea ca acest crom sa fie selectat in functie de scorul sau de comunitate
        if rand < prob:
            chosen = i # indicele cromozomului care este selectat
            break
    return chosen


def recombinare(parinte1, parinte2, rata):
    if np.random.random_sample() < rata: # vedem daca o probabilitate aleatoare este mai mic adecat rata de recombinare
        l = len(parinte1) #dim ccromozomului parinte
        mask = np.random.randint(2, size=l) #generam o masca binara aleatoare de lungime l
        copil = np.zeros(l, dtype=int) #vecorul copil are dim cromozomului parinte
        #vectorul copil se populeaza cu elemente din parinte1 si parinte2 in functie de masca
        for i in range(len(mask)):
            if mask[i] == 1:
                copil[i] = parinte1[i]
            else:
                copil[i] = parinte2[i]
        return copil #noua combinatie a cromozomilor parinti

    else: # daca rata este mai mica decat probabilitatea aleatoare se returneaza un vector gol
        return np.array([])


def mutatie(crom, Adj, rata):
    if np.random.random_sample() < rata: #generam o probabilitate aleatoare si o comparam cu rata de mutatie
        vecin = []
        while len(vecin) < 2: #cautam un nod care are cel putin 2 vecini
            mutant = np.random.randint(1, len(crom)) #genereaza un nod aleator din cromozom
            linie = Adj.getrow(mutant).toarray()[0] #obtinem linia corespunzatoare genei mutant din matricea de adiacenta
            vecin = [i for i in range(len(linie)) if linie[i] == 1] #identifica vecinii nodului in graf
            if len(vecin) > 1: #am gasid nodul
                if crom[mutant] in vecin:
                    vecin.remove(crom[mutant]) #se elimina nodul din lista de vecini
                    schimba = int(
                        np.floor(np.random.random_sample()*(len(vecin))))#selectam un vecin aleator
                    crom[mutant] = vecin[schimba] #nodul initial este inlocuit de vecin
                    vecin.append(crom[mutant]) #adaugfa nodul schimbat
    return crom