#include <bits/stdc++.h>

using namespace std;

const int NMAX = 1e3+5;


ifstream fin("karp.in");
ofstream fout("karp.out");

int n , m;

vector < int > graf[NMAX];
int cost[NMAX][NMAX], reziduri[NMAX][NMAX];
int viz[NMAX] , pred[NMAX];

void citire(){
    int x , y , z;
    fin >> n >> m;
    for(int i=1;i<=m;i++){
        fin >> x >> y >> z;
        cost[x][y] += z;///matrice de costuri
        graf[x].push_back(y);///adaug muchiile in graf
        graf[y].push_back(x);
    }
}

int bfs()
{
    for(int i=0;i<=n;i++)
        viz[i] = 0;
    queue < int > noduri;
    noduri.push(0);///punem nodul 0 in coada
    viz[0] = 1;
    while(!noduri.empty()){
        ///nod curent
        int nc = noduri.front();
        ///in cazul in care nodul curent este
        ///chiar nodul destinatie pe care dorim sa-l atingem
        ///nu mai are rost sa-l vizitam,

        ///incepem sa vizitam vecinii
        for(int i=0;i<graf[nc].size()&&nc!=(n-1);i++){
            ///nod vecin
            int nv = graf[nc][i];
            ///in cazul in care fluxul pe care il avem pe muchie este atins(e full fluxul de pe muchie),
            ///nu mai putem folosi muchia pentru ca a fost fluxul maxim de pe ea atins
            ///adica nu mai putem sa trecem ceva prin flux , nu ne dorim sa-l parcurgem
            if(reziduri[nc][nv]==cost[nc][nv]||viz[nv]==1)continue;///sare peste urmatoarele instructiuni
            ///daca rezidurile au ajuns sa aiba aceeasi valoare cu costul
            viz[nv] = 1;
            noduri.push(nv);///punem nodul vecin in coada
            ///salveaza si drumul inapoi
            pred[nv] = nc;
        }
        ///eliminam nodul curent din lista
        noduri.pop();
    }
    ///returnam viz[n] deoarece
    ///dorim sa stim daca am reusit
    ///sa atingem nodul n adica nodul destinatie
    ///cu parcurgerea bfs pe care am facut-o
    return viz[(n-1)];
}

int edmonds_karp(){
    ///atat timp cat putem sa ajungem la nodul nostru
    ///algoritmul continua parcurgerea
    int noduri, fminim, flux;
    flux = 0;
    while(bfs()==1){
            ///refacem traseul invers
            for(int i=0;i<graf[(n-1)].size();i++)
            {
                int nc = graf[(n-1)][i];///nodul curent e al i-lea vecin al ultimului nod
                ///in cazul in care vecinul este full(a atins fluxul maxim pe muchia respectiva)
                ///pe muchie , nu putem sa-l luam in calcul
                ///pentru transport
                if(cost[nc][(n-1)]==reziduri[nc][(n-1)]||viz[nc]==0)continue;///costul dintre nodul curent si ultimul nod
                pred[(n-1)] = nc;///predecesor de ultimul nod e nodul curent
                fminim = 550000005;
                for(noduri = (n-1); noduri != 0; noduri = pred[noduri]){///parcurgen vectorul de predecesori de la sfasit la inceput
                    fminim = min(fminim,cost[pred[noduri]][noduri]-reziduri[pred[noduri]][noduri]);///calculeaza fluxul
                    ///minim folosit matricea de costuri si cea de reziduri dintre nodul curent si predecesorul lui
                }
                if(fminim==0)continue;
                for(noduri = (n-1); noduri != 0; noduri = pred[noduri]){///parcurgem vectorul de predecesori
                    reziduri[pred[noduri]][noduri] += fminim;///adaugam la muchie fluxul minim (muchia predecesor nod si nod)
                    reziduri[noduri][pred[noduri]] -= fminim;///scadem din fluxul muchiei fluxul minim (muchia nod si predecesorul ei)
                }
                flux += fminim;///adaugam la flux fluxul minim de la muchie
            }
    }
    return flux;
}

int main()
{
    citire();
    fout << edmonds_karp();
    return 0;
}

