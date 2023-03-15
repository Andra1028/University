#include <bits/stdc++.h>

using namespace std;

ifstream fin("date.in");
ofstream fout("date.out");
const int CMAX = 2e6+15;;

set < pair < int , int > > coding;
int length, contorizare[CMAX];

int n;
long long temp;
struct noduri{
    int st, dr , niv;
    long long val;
    string codare;
}heap[CMAX];

char litere[500];
string dictionar[500];

string text;
int lungime_text;

void read_adap(){
    fin >> noskipws; ///citire cu spatii
    char cr;
    lungime_text = 0;

    while(fin >> cr){
        text = text + cr;
        lungime_text++;


        coding.erase({contorizare[(int)cr],cr});/// sterg frecventa caracteruluio si caracterul din coding
        contorizare[(int)cr]++; /// cresc frecventa
        coding.insert({contorizare[(int)cr],cr});/// adaug caracterul cu frecventa noua
    }
    set<pair<int,int>>::iterator itr;
    int i = 0;
    for(itr = coding.begin();itr!=coding.end();itr++){
            litere[i++] = (*itr).second;///pune fiecare litera din coding
            ///in coding apar literele o singura data sortate dupa frecventa
    }
    n = coding.size();
    n--;
    ///cout<<n<<endl;
}


void parc_srd(int nod, string cod){///parcurgem arborele si traduc literele
    if(nod>0){///legaturile nodurilor initiale sunt 0
    heap[nod].codare = cod;
    parc_srd(heap[nod].st,cod+"0");/// pune pe partea stanga 0 la cod
    parc_srd(heap[nod].dr,cod+"1");/// pune pe partea dreapta 1 la cod
    }
}


void huffman(){
    heap[n+1].val = LONG_LONG_MAX;
    int i, j, stop , st , dr;
    i = 1;/// iterator noduri inceput
    j = n+2;/// iterator noduri noi
    stop = n+1;/// urmatorul nod care se creaza
    while(i < n+1 || j < stop){
        if(j <= stop && heap[j].val < heap[i].val)///compara nodurile create noi cu nodurile
                                                  ///citite(nodurile initiale create dupa litere)
                st = j++;
        ///daca valoarea nodului nou e mai mica decat valoarea nodului curent din nodurile initiale
        ///stanga primeste j si creste iteratorul nodurilor noi
        else{
            st = i++;
        }
        if(j <= stop && heap[j].val < heap[i].val){
            dr = j++;
        }
         ///daca valoarea nodului nou e mai mica decat valoarea nodului curent din nodurile initiale
        ///dreapta primeste j si creste iteratorul nodurilor noi
        else
        {
            dr = i++;
        }
        heap[++stop].val = heap[st].val + heap[dr].val;///creaza un nod nou cu valoarea sumelor nodurilor precedente(stanga, dreapta)
        heap[stop].st = st;///cream legaturile intre noduri
        heap[stop].dr = dr;
    }
    parc_srd(stop,"");
}

void convert(){///afiseaza litera si codarea ei
    int i = 1;
    while(i<=n){
        dictionar[litere[i]] = heap[i].codare;/// pune la litera curenta din dictionar codul ei
        fout<<litere[i]<<" "<<heap[i].codare<<endl;///afisez litera si codul ei
        i++;
    }
}



void traducere(){
    ///parcurge textul initial si afiseza pentru fiecare litera codul respectiv din dictionar
    for(int i=0;i<lungime_text;i++)
    {
        fout << dictionar[text[i]];
    }
}

int main()
{
    read_adap();
    huffman();
    convert();
    traducere();
    return 0;
}
