def reuniune_submultimi(sub):
    vector = [] #noile submultimi formate din uniune
    vizitat = [] #tinem evidenta submultimior deva vizitate oentru a nu le dubla
    for s in range(len(sub)):
        if sub[s] not in vizitat: #verif daca submultimea s a fost deja vizitata
            nou = sub[s]
            for x in sub:
                if sub[s] & x: #verif daca submultimea x intersecteaza submultimea curenta
                    nou = nou | x #unim cele doua submultimi
                    vizitat.append(x) #adaugam in in vizitate
            vector.append(nou) #adaugam noua multime formata
    return vector #lista noilor submultimi formate prin unirea celor initiale


def gaseste_submultime(crom):
    #cauta submultimi de elemente ale cromozomului a.i. fiecare elem dintr o submultime sa fie in
    # relatie cu cel putin un alt elem sin acea submultime
    sub = [{x, crom[x]} for x in range(len(crom))] #lista in care fiecare elem este o submultime de cardinalitate 1 din cromozom
    rez = sub
    i = 0
    while i < len(rez): #inceraca sa uneasca submultimile pana nu se mai poate face nicio reuniune
        candidat = reuniune_submultimi(rez)
        if candidat != rez: #daca s-a format o submultime noua i se atribuie rezultatului
            rez = candidat
        else:
            break #se opreste cand nu se mai pot forma submultimi noi
        rez = candidat
        i += 1
    return rez #partitie a multimii initiale
               #fiecare submultime reprezinta o comunitate a grafului  descris de cromozom
