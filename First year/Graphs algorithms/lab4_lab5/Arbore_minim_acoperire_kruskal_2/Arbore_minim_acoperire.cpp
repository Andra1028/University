#include <iostream>
#include <algorithm>
#include <vector>
#include <fstream>
using namespace std;
ifstream f("arbore.in");
ofstream g("arbore.out");


const int N=1e5;
int cost_minim,cost_sortare[N+1],comp_conexa[N+1];
vector <int> arbore;
int x[N+1],y[N+1],c[N+1];
///x vector pentru nod1 din muchii
///y vector pentru nod2 din muchii
///c vector pentru costul muchiei

bool cmp(int i,int j)
{
	return (c[i]<c[j]);
}


int grupa(int i)
{
	if(comp_conexa[i]==i)///comp_conexa functioneaza ca un fel de vector de tati
		return i;
	comp_conexa[i]=grupa(comp_conexa[i]);
	return comp_conexa[i];///returneaza nodul cel mai distre radacina ca sa ne putem da seama daca nodurile fac parte din aceeasi padure
}


void reuniune(int i,int j)
{
	comp_conexa[grupa(i)]=grupa(j);///uneste padurile
}


void Kruskal(int n,int m)
{
	for(int i=0; i<n; i++)
		comp_conexa[i]=i;///formam n componente conexe
	sort(cost_sortare,cost_sortare+m,cmp);///sorteaza vectorul de pozitii dupa costul muchiei respective
	for(int i=0; i<m; i++)
	{
		if(grupa(x[cost_sortare[i]])!=grupa(y[cost_sortare[i]]))///daca doua noduri adiacente sunt in grupe(paduri, componente conexe) diferite
		{
			cost_minim=cost_minim+c[cost_sortare[i]];///adaugam la costul minim costul muchiei
			reuniune(x[cost_sortare[i]],y[cost_sortare[i]]);///face reuniunea componentelor conexe de cost minim
			arbore.push_back(cost_sortare[i]);/// pune in arbore pozitia pe care se afla in fisier muchia adaugata
		}
	}
}


int main()
{
	int n,m;
	f>>n>>m;
	for(int i=0; i<m; i++)
	{
		f>>x[i]>>y[i]>>c[i];///citeste nodurile care formeaza muchia si costul
		cost_sortare[i]=i;///pune pozitia pe care se gaseste muchia in fisier
	}
	Kruskal(n,m);
	sort(arbore.begin(), arbore.end());///sorteaza arborele
	g<<cost_minim<<endl;
	g<<n-1<<endl;///arbopre n-1 muchii
	for(int i=0; i<n-1; i++)
		g<<x[arbore[i]]<<" "<<y[arbore[i]]<<endl;///x eprimul nod sin muchie
    ///y e al doilea nod din muchie
    ///afisam dupa indiceled muchiei
	f.close();
	g.close();
	return 0;
}
