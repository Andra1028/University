import numpy as np


def scor_comunitate(chrom, subset, r, Adj):
    matrice = Adj.toarray()
    CS = 0
    # luam fiecare submultime (comunitate identificate in cromozom)
    for s in subset:
        subm = np.zeros((len(chrom), len(chrom)), dtype=int)
        for i in s:
            for j in s:
                subm[i][j] = matrice[i][j] #adaugam in submatricea specifica comunitatii din cromozom elementele din matricea initiala  pentru liniile si coloanele reprezentate de s
        media = 0
        suma = 0
        for linie in list(s):
            liniem = np.sum(subm[linie])/len(s) # calc media pe linie
            suma += np.sum(subm[linie]) #calc suma tuturor valorilor din submatrice care sunt in comunitatea s
            media += (liniem**r)/len(s) #calc media ponderata a mediei liniilor submatricei
            #ponderea este data de r
        CS += media*suma
    return CS