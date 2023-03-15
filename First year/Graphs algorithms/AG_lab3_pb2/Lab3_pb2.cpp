#include <iostream>
#include <fstream>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

ifstream fin("intrare.txt");
ofstream fout("iesire.txt");

struct triplet{
    int x , y , c;
};
const int CMAX = 5e4+15;
int muchie , cost , n , m;
int h[CMAX];
int D[CMAX];
int matrice_D[CMAX/5][CMAX/5];
int viz[CMAX];
const long long int oo = 1000*50000;
vector < pair < int , int > > graf[CMAX];
vector < triplet > muchii;

struct comp{
    bool operator()(int x, int y){
        return D[x] > D[y];
    }
};

queue < int > coada;
priority_queue < int , vector < int >, comp > Q;

void citire()
{
    int x , y , c;
    fin >> n >> m;
    for(int i=1;i<=m;i++)
    {
        fin >> x >> y >> c;
        muchii.push_back({x,y,c});
        graf[x].push_back({y,c});
    }

}

bool BellmanFord(vector<pair<int,int>>graf[CMAX],int start){
    for(int i=0;i<=n;i++)
    {
        viz[i] = 0;
        D[i] = oo;
    }
    D[start] = 0;
    coada.push(start);
    while(!coada.empty()){
        int nodcurent = coada.front();
        viz[nodcurent]++;
        coada.pop();
        if(viz[nodcurent]>=(n+1)){
            return false;
        }
        for(int i=0;i<graf[nodcurent].size();i++)
        {
            int vecin = graf[nodcurent][i].first;
            int cost =  graf[nodcurent][i].second;
            if(D[nodcurent]+cost<D[vecin]){
                D[vecin] = D[nodcurent] + cost;
                coada.push(vecin);
            }
        }
    }
    return true;
}

void Dijkstra(vector<pair<int,int>> graf[CMAX], int start)
{
    for(int i=0;i<=n;i++)
        D[i] = oo;
    D[start] = 0;
    Q.push(start);
    while(!Q.empty()){
        int nodcurent = Q.top();
        Q.pop();
        viz[nodcurent] = 0;
        for(int i=0;i<graf[nodcurent].size();i++)
        {
            int cost, vecin;
            vecin = graf[nodcurent][i].first;
            cost  = graf[nodcurent][i].second;
            if(D[nodcurent] + cost < D[vecin]){
                D[vecin] = D[nodcurent] + cost;
                if(viz[vecin]==0){
                    Q.push(vecin);
                    viz[vecin] = 1;
                }
            }
        }
    }
}

int reponderare(int drum, int val_a , int val_b){
    return drum + val_a - val_b;
}

int reponderare_inversa(int drum, int val_a , int val_b){
    return drum - val_a + val_b;
}

void afisare(vector < pair < int , int > > graf[CMAX]){
    for(int i=0;i<=n;i++)
    {
        for(int j=0;j<graf[i].size();j++)
        {
           fout << i << " " << graf[i][j].first << " " << graf[i][j].second << '\n';
        }
    }
}

bool Johnson(){
    vector < pair < int , int > > graf_modif[CMAX];
    int h[CMAX];
    for(int i=0;i<n;i++)
    {
        //Adaugam nodul 0
        graf[n].push_back({i,0});
    }
    if(BellmanFord(graf,n)==false){
        return false;
    }
    else{
        for(int i=0;i<n;i++){
            h[i] = D[i];
        }
        for(int i=0;i<muchii.size();i++){
            //reponderam graful cu ce am gasit dupa executia algoritmului Bellman Ford
            graf_modif[muchii[i].x].push_back({muchii[i].y,reponderare(muchii[i].c,D[muchii[i].x],D[muchii[i].y])});
        }


        for(int j=0;j<n;j++)
        {
            //dijkstra pentru toate nodurile
            memset(viz,0,CMAX);
            Dijkstra(graf_modif,j);

            for(int z=0;z<n;z++){
                //matricea costurilor, repondnerarea inversa
                matrice_D[j][z] = reponderare_inversa(D[z],h[j],h[z]);
            }
        }

    }
    afisare(graf_modif);
    return true;

}

int main()
{
    citire();
    if(Johnson() == false)
    {
        fout << -1;
    }
    else{
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++){
                if(matrice_D[i][j]>=oo){
                    fout << "INF ";
                }
                else
                    fout << matrice_D[i][j] << " ";
            }
            fout << '\n';
        }
    }
    return 0;
}
