#include <bits/stdc++.h>

using namespace std;

ifstream fin("date.in");
ofstream fout("date.out");

const int CMAX = 2e6+15;;

set < pair < int , int > > coding;
int length, contorizare[CMAX];

int n, stop;
int fr[CMAX];

long long temp;
struct noduri{
    int st, dr , niv;
    long long val;
    string codare;
}heap[CMAX];

char spatiu;
char litere[CMAX];
char dictionar[CMAX];

string text;
int lungime_text;

void read_adap(){
    ///Functie de citire a textului dat
    //ifstream fin("pb4.in");
    fin >> n;
    int i = 1;
    char linie[CMAX];

    fin.getline(linie,10);
    while(i<=n){
        fin.getline(linie,10);///citeste fiecare linie de tipul litera si frecventa
        int j = 2;
        fr[i] = 0;///seteaza frecventa literei de pe linia i cu 0
        while(linie[j]>='0'&&linie[j]<='9'){///cat timp caracterele incepand a doua pozitie din linie sunt cifre
            fr[i] = fr[i]*10 + linie[j] - 48;///construieste numarul frecventei
            j++;///trece la urmatorul caracter
        }
        litere[i] = linie[0];///pune in vectorul de litere litera de pe fiecare linie (nu apar doua litere identice in sir)
        coding.insert({fr[i],litere[i]});///insereaza in coding o pereche de forma frecventa litera
        i++;
    }
    fin >> text;///citeste codul huffman
    lungime_text = text.size();
    fin.close();
}

void afisare_adap(){
    ///Functie de afisare si convertire la un dictionar
    ///pentru utilizarea functiei huffman
    int i = 1;
    while(!coding.empty()){///cat timp coada nu e goala
        heap[i].val = (*coding.begin()).first;///nodul i din arbore primeste ca valoare frecventa primei litere din coding
        litere[i] = (char)(*coding.begin()).second;///pune pe pozitia i prima litera din coding
        fr[i] = heap[i].val;///frecventa lui i e frecventa nodului i
        coding.erase(coding.begin());///stergen din coding prima litera si frecventa ei
        i++;
    }
    ///fout << '\n';
}


void huffman(){
    heap[n+1].val = LONG_LONG_MAX;///creaza un nod cu valoare maxima pentru a afla unde sze opreste iteratorul
    int i, j, st , dr;
    i = 1;///indice pentru nodurile initiale
    j = n+2;///indice pentru nodurile nou create
    stop = n+1;
    while(i < n+1 || j < stop){
        if(j <= stop && heap[j].val <= heap[i].val)///daca valoarea nodului noi e mai mica sau egala cu val nodului initial
                st = j++;///stranga primeste valoare iteratorului nodurilor noi create, iteratorul creste cu o poz
        else{
            st = i++;
        }
        if(j <= stop && heap[j].val <= heap[i].val){
            dr = j++;///dreapta primeste valoare iteratorului nodurilor noi create, iteratorul creste cu o poz
        }
        else
        {
            dr = i++;
        }
        heap[++stop].val = heap[st].val + heap[dr].val;///valoarea insumata a noului nod
        heap[stop].st = st;///legatura intre noduri
        heap[stop].dr = dr;///legatura intre noduri
    }
}

void traducere(){
    ///Functia de traducere converteste textul nostru in
    ///codul binar format din codarea huffman

    int i=0;
    int nod = stop;///ultimul nod
    while(i<lungime_text){///parcurge codul huffman
        if(text[i]=='0'){///daca e zero se duce pe ramura stanga
            nod = heap[nod].st;
            if(heap[nod].dr==0){///daca nu are fiu drept(sau strang) e frunza si am ajung la litera din raspuns
                fout << (char)litere[nod];
                nod = stop;///ia iar valoarea primului nod
            }
        }
        else if(text[i]=='1'){///daca e unu se duce pe ramura din dreapta
            nod = heap[nod].dr;
            if(heap[nod].dr==0){///daca nu are fii e frunza si ajungem la raspuns
                fout << (char)litere[nod];
                nod = stop;
            }
        }
        i++;
    }
    fout.close();
}

int main()
{
    read_adap();
    afisare_adap();
    huffman();
    traducere();

    return 0;
}
