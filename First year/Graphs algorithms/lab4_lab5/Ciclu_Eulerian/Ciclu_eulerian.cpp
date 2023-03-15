#include <iostream>
#include <fstream>
#include <vector>
#include <stack>
using namespace std;
ifstream f("ciclu.in");
ofstream g("ciclu.out");
const int dim=1e5+7;
vector <pair<int,int>> graf[dim];
int viz[5*dim];
stack <int> stiva;
int v,m,k,urm;


void Euler()
{
	int nod,muchie,y;
	stiva.push(0);///punem pe stiva primul nod
	while(!stiva.empty())
	{
		nod=stiva.top();///memoram nodul din varful stivei
		if(graf[nod].size()==0)///daca nodul nu mai are noduri adiacente il scoatem de pe stiva si il afisam
		{
			stiva.pop();
			g<<nod<<" ";
		}
		else
		{
			y=graf[nod].back().first;///ia ultimul nod care e adiacent cu nodul curent
			muchie=graf[nod].back().second;///ia a cata pozitie la citire are muchia nod y
			graf[nod].pop_back();///scoate din graf nodul
			if(viz[muchie]==0)///daca muchia nu a fost vizitata
			{
				viz[muchie]=1;///punem 1 pentru vizitat
				stiva.push(y);///punem pe stiva nodul y
			}
		}
	}
}


int main()
{
	int x,y;
	f>>v>>m;
	for(int i=0; i<m; i++)
	{
		f>>x>>y;
		graf[x].push_back({y,i});///salveaza muchia x, y si a cata apare in citire
		graf[y].push_back({x,i});///salveaza muchia y, x si pozitia pe care apare in citire
	}
	Euler();
	f.close();
	g.close();
	return 0;
}
