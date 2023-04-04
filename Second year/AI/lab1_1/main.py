"""1. Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea
într-un text care conține mai multe cuvinte separate prin ” ” (spațiu).
 De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene” este cuvântul "si"."""

def last_word(sentence):
    import re
    result = ""
    for word in re.split(" ", sentence):
        if result < word:
            result = word
    return result



"""2. Să se determine distanța Euclideană între două locații identificate prin perechi de numere. 
De ex. distanța între (1,5) și (4,1) este 5.0"""

def distance(xp,yp,xq,yq):
    import math
    dist = math.sqrt((xp-xq)*(xp-xq) + (yp-yq)*(yp-yq))
    return dist



"""3. Să se determine produsul scalar a doi vectori rari care conțin numere reale. 
Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni. 
De ex. produsul scalar a 2 vectori unisimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4."""

def produs(v1,v2):
    if len(v1) != len(v2):
        return 0
    p=0
    for x, y in zip(v1, v2):
        p += int(x)*int(y)
    return p


"""4. Să se determine cuvintele unui text care apar exact o singură dată în acel text. 
De ex. cuvintele care apar o singură dată în ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'."""
def frecvence(sentence):
    import re
    words={}
    for word in re.split(" ", sentence):
       if word in words:
           words.pop(word)
       else: words[word] = 0
    return words


"""5. Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1} astfel încât o 
singură valoare se repetă de două ori, să se identifice acea valoare care se repetă. 
De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori."""

def find_number(vector):
    frec={}
    for x in vector:
        if x in frec:
            return x
        else: frec[x]=0
    return ''



"""6. Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul majoritar 
(care apare de mai mult de n / 2 ori). De ex. 2 este elementul majoritar în șirul 
[2,8,7,2,2,5,2,3,1,2,2]."""

def find_maj(vector):
    n =  len(vector)
    frec = {}
    for x in vector:
        if x in frec:
            frec[x] = frec[x] + 1
            if frec[x] > n/2:
                return x
        else:
            frec[x] = 1
    return ''


"""7. Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n). 
De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7."""

def find_k_max(list, k):
    for i in range(k):
        max=-99999
        for x in list:
            if int(x) > max:
                max = int(x)
        while str(max) in list:
            list.remove(str(max))
        if i == k-1:
         return max



"""8. Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n.
 De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100."""

def binary(n):
    nr=0
    p=1
    while n > 0:
        nr = nr + n % 2*p
        n = n//2
        p = p*10
    return nr

def binary_gen(n):
    list=[]
    for i in range(1,n+1):
        list.append(binary(i))
    return list


"""9. Considerându-se o matrice cu n x m elemente întregi și o listă cu perechi formate din coordonatele 
a 2 căsuțe din matrice ((p,q) și (r,s)), să se calculeze suma elementelor din sub-matricile identificate 
de fieare pereche."""

def submatrice(matrix, pairs):
    sums=[]
    for pair in pairs:
        sum=0
        for i in range(pair[0],pair[2]+1):
            for j in range(pair[1], pair[3]+1):
                sum+=matrix[i][j]
        sums.append(sum)
    return sums

"""10. Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe linii, 
să se identifice indexul liniei care conține cele mai multe elemente de 1."""

def max_line(matrix):
    min=len(matrix[0])+1
    line=-1
    for i in range(len(matrix)):
        for j in range(len(matrix[0])):
            if matrix[i][j]==1:
                if(j<min):
                    min=j
                    line=i
                break
    return line+1


"""11. Considerându-se o matrice cu n x m elemente binare (0 sau 1), să se înlocuiască cu 1
 toate aparițiile elementelor egale cu 0 care sunt complet înconjurate de 1."""

"""def inlocuire_0(matrix): #nu e bun
    for i in range(1,len(matrix)):
        for j in range (1, len(matrix[0])):
            if matrix[i][j]==0:
                si=-1
                ji=1
                sj=-1
                dj=1
                if i+si>-1 and matrix[i + si][j] == 1:
                    si = 1
                while i+si>-1 and matrix[i+si][j]==0 :
                    if matrix[i+si][j]==1:
                        si=1
                        break
                    si = si - 1
                if i+ji<len(matrix) and matrix[i + ji][j] == 1:
                    ji = 0
                while i+ji<len(matrix) and matrix[i+ji][j]==0 :
                    if matrix[i+ji][j]==1:
                        ji=0
                        break
                    ji = ji + 1
                if j+sj>-1 and matrix[i][j + sj] == 1:
                    sj = 1
                while j+sj>-1 and  matrix[i][j+sj]==0:
                    if matrix[i][j+sj]==1:
                        sj=1
                        break
                    sj = sj - 1
                if j+dj<len(matrix[0]) and matrix[i][j + dj] == 1:
                    dj = 0
                while j+dj<len(matrix[0]) and matrix[i][j+dj]==0:
                    if matrix[i][j+dj]==1:
                        dj=0
                        break
                    dj = dj + 1
                if si==1 and ji==0 and sj==1 and dj==0:
                    matrix[i][j]=1
    return matrix"""


def fill(matrix, n, m, current_row, current_col):
        if current_row < 0 or current_row >= n or current_col < 0 or current_col >= m or matrix[current_row][current_col] != -1:
            return
        #se inlocuiesc doar val de -1 cu 0 si se parcurg valorile din stanga, dreapta, sus si jos valorii testate
        matrix[current_row][current_col] = 0
        fill(matrix,n,m,current_row + 1, current_col)
        fill(matrix,n,m,current_row - 1, current_col)
        fill(matrix,n,m,current_row, current_col + 1)
        fill(matrix,n,m,current_row, current_col - 1)

def zero_in_unu(matrix):
    #porneste din margini si cauta grupurile de valori care nu sunt inconjurate de 1
    n = len(matrix)
    m = len(matrix[0])
    #inlocuim toate val de 0 cu -1
    for row in range(n):
        for col in range(m):
            if matrix[row][col] == 0:
                matrix[row][col] = -1
    #facem fill pentru fiecare linie incepand pt coloanele marginale
    for row in range(n):
        if matrix[row][0] == -1:
            fill(matrix,n,m,row, 0)
    for row in range(n):
        if matrix[row][m - 1] == -1:
            fill(matrix,n,m,row, m - 1)
    #facem fill pentru fiecare coloane incepand de la liniile marginale
    for col in range(m):
        if matrix[0][col] == -1:
            fill(matrix,n,m,0, col)
    for col in range(m):
        if matrix[n - 1][col] == -1:
            fill(matrix,n,m,n - 1, col)
    #toate valorile care nu au fost verificate de fill sunt 1
    for row in range(n):
        for col in range(m):
            if matrix[row][col] == -1:
                matrix[row][col] = 1
    return matrix



def command():

    while(True):
        c = input("Introduceti numarul problemei, 0 pentru exit: ")
        c = int(c)
        if c == 0:
            return False
        elif c == 1:
            sentence = input("Introduceti o propozitie: ")
            print(last_word(sentence))
        elif c==2:
            xp = input("Coordonata x pentru primul punct: ")
            yp = input("Coordonata y pentru primul punct: ")
            xq = input("Coordonata x pentru al doilea punct: ")
            yq = input("Coordonata y pentru al doilea punct: ")
            xp = int(xp)
            yp = int(yp)
            xq = int(xq)
            yq = int(yq)
            print(distance(xp,yp,xq,yq))
        elif c==3:
            v1 = input("Vector: ")
            v2 = input("Vector: ")
            list1=v1.split(",")
            list2=v2.split(",")
            produs(list1,list2)
        elif c==4:
            sentence = input("Propozitie: ")
            print(key for key in frecvence(sentence))
        elif c==5:
            vector = input("Vector: ")
            list=vector.split(",")
            print(find_number(list))
        elif c==6:
            vector = input("Vector: ")
            list=vector.split(",")
            print(find_maj(list))
        elif c==7:
            vector = input("Vector= ")
            k = input("K= ")
            k = int(k)
            list = vector.split(",")
            print(find_k_max(list,k))
        elif c==8:
            n = input("N= ")
            n = int(n)
            print(binary_gen(n))
        elif c==9:
            n = input("N= ")
            n=int(n)
            m = input("M= ")
            m=int(m)
            matrix=[]
            print("Matricea: ")
            for i in range(n):
                a=[]
                for j in range(m):
                    a.append(int(input()))
                matrix.append(a)
            print(matrix)
            pairs=[]
            a=[]
            per=int(input("Numar perechi: "))
            x=0
            for i in range(per):
                a=[]
                for j in range(4):
                    a.append(int(input()))
                pairs.append(a)
            print(pairs)
            print(submatrice(matrix,pairs))
        elif c==10:
            n = input("N= ")
            n = int(n)
            m = input("M= ")
            m = int(m)
            matrix = []
            print("Matricea: ")
            for i in range(n):
                a = []
                for j in range(m):
                    a.append(int(input()))
                matrix.append(a)
            print(matrix)
            print(max_line(matrix))
        elif c==11:
            n = input("N= ")
            n = int(n)
            m = input("M= ")
            m = int(m)
            matrix = []
            print("Matricea: ")
            for i in range(n):
                a = []
                for j in range(m):
                    a.append(int(input()))
                matrix.append(a)
            print(matrix)
            print(zero_in_unu(matrix))
        else: print("Comanada invalida")


def test():
    assert(last_word('Ana are mere rosii si galbene') == 'si')
    assert(distance(1,5,4,1) == 5.0)
    assert(produs([1,0,2,0,3], [1,2,0,3,1]) == 4)
    assert(frecvence('ana are ana are mere rosii') == {'mere': 0, 'rosii': 0})
    assert(find_number([1,2,3,4,2]) == 2)
    assert(find_maj([2,8,7,2,2,5,2,3,1,2,2]) == 2)
    assert(find_k_max(['7','4','6','3','9','1'],2) == 7)
    assert(binary_gen(4) == [1, 10, 11, 100])
    matrix=[[0, 2, 5, 4, 1],
[4, 8, 2, 3, 7],
[6, 3, 4, 6, 2],
[7, 3, 1, 8, 3],
[1, 5, 7, 9, 4]]
    pairs=[[1,1,3,3], [2,2,4,4]]
    assert(submatrice(matrix,pairs)==[38,44])
    matrix=[[0,0,0,1,1],
[0,0,0,0,1],
[0,0,1,1,1]]
    assert(max_line(matrix)==3)
    matrix=[[1,1,1,1,0,0,1,1,0,1],
[1,0,0,1,1,0,1,1,1,1],
[1,0,0,1,1,1,1,1,1,1],
[1,1,1,1,0,0,1,1,0,1],
[1,0,0,1,1,0,1,1,0,0],
[1,1,0,1,1,0,0,1,0,1],
[1,1,1,0,1,0,1,0,0,1],
[1,1,1,0,1,1,1,1,1,1]]
    rez = [[1,1,1,1,0,0,1,1,0,1],
[1,1,1,1,1,0,1,1,1,1],
[1,1,1,1,1,1,1,1,1,1],
[1,1,1,1,1,1,1,1,0,1],
[1,1,1,1,1,1,1,1,0,0],
[1,1,1,1,1,1,1,1,0,1],
[1,1,1,0,1,1,1,0,0,1],
[1,1,1,0,1,1,1,1,1,1]]
    assert(zero_in_unu(matrix)==rez)


def main():
    test()
    command()


main()
