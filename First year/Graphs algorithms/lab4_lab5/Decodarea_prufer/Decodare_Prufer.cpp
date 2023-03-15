#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <queue>
using namespace std;
ifstream f("in.txt");
ofstream g("out.txt");
vector <int> parinte;
queue <int> coada;
map <int,int> nu_exista;
map <int,int> nrAparitii;

int main()
{
	int n,m,x,y;
	f>>m;
	n=m+1;
	parinte.resize(n);
	for(int i=0; i<n; i++)
	{
		parinte[i]=-1;///initializam cu -1 vectorul parinte (vectorul rezulat la decodare)
		nu_exista[i]=0;///initializez dictionarul care retine frunzele
	}
	for(int i=0; i<m; i++)
	{
		f>>x;
		coada.push(x);///pun in coada toate nodurile care apar in citire
		nu_exista.erase(x);///sterge din dictionar nodurile care apar asa incat sa ramana doar frunzele
		nrAparitii[x]++;///creste numarul de aparitii ca sa il folosesc dupa sa vad daca nodul e frunza
	}
	for(int i=0; i<m; i++)
	{
		x=coada.front();///ia primul element din coada
		coada.pop();///sterg din coada primul element
		y=(*nu_exista.begin()).first;///ia prima frunza(cea mai mica frunza)
		if (nrAparitii[x]==1)/// numarul de aparitii e 1 atunci devine frunza
			{
			nrAparitii.erase(x);
			nu_exista[x]=1;///se pune in lista de frunze
			}
		else
			nrAparitii[x]--;
		nrAparitii[y]++;
		nu_exista.erase(y);///sterge frunza minima din dictionar
		parinte[y]=x;///pune pe pozitia y(frunza) x(parintele)
	}
	g<<n<<endl;
	for(int i=0; i<n; i++)
		g<<parinte[i]<<" ";
	f.close();
	g.close();
	return 0;
}
