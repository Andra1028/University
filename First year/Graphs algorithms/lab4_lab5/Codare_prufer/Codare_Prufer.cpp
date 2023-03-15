
#include <iostream>
#include <fstream>
#include <vector>
#include <set>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");

int n;
vector <int> parinte,nr_succesori,k;
set <int> frunze;

void prufer()
{
    f>>n;
    parinte.resize(n+1);
    nr_succesori.resize(n+1);
    for(int i=0;i<n;i++)
    {
        f>>parinte[i];
        if(parinte[i]!=-1)
            nr_succesori[parinte[i]]++;///aflam numarul fiilor unui nod
    }
    for(int i=0; i<n;i++)
        if(nr_succesori[i]==0)///daca e 0 nodul e frunza si o bagam in setul de frunze
            frunze.insert(i);
    while(frunze.size())///cat timp exista frunze
    {
        int f=*frunze.begin();
        frunze.erase(f);///stergem prima frunza
        if(parinte[f]!=-1)///daca parintele nu e radacina
        {
            int pr=parinte[f];
            k.push_back(pr);///il adaugam in codare
            nr_succesori[pr]--;///decrementam succesorii
            if(nr_succesori[pr]==0)///daca nr de fii e 0 atunci e frunza si adaugam la setul de frunze
                frunze.insert(pr);
        }
    }
    g<<k.size()<<endl;
    for(int i=0;i<n-1;i++)
        g<<k[i]<<" ";
}

int main()
{
	prufer();
	return 0;
}
