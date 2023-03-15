#include <iostream>
#include <fstream>
#include <limits.h>


using namespace std;

ifstream fin("date.txt");
int inf = 999999;

void citire_matrice(int& n, int a[20][20])
{
	fin >> n;
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
			fin >> a[i][j];
}

void afisare_matrice(int n, int a[20][20])
{
	for (int i = 1; i <= n; i++)
	{
       for (int j = 1; j <= n; j++)
			cout << a[i][j];
       cout << endl;
	}
		
}

void nod_izolat(int a[20][20], int n)
{
	cout << "Noduri izolate: ";
	int ok = 0;
	for (int i = 1; i <= n; i++)
	{
		int s = 0;
		for (int j = 1; j <= n; j++)
		{
			s += a[i][j];
		}
		if (s == 0)
			cout << i << " ", ok = 1;
	}
	if (ok == 0)
		cout << " nu exista";
}

int graf_regular(int n, int a[20][20])
{
	int s = 0;
	for (int i = 1; i <= n; i++)
		s += a[1][i];
	for (int i = 2; i <= n; i++)
	{
		int slinie = 0;
		for (int j = 1; j <= n; j++)
			slinie = slinie + a[i][j];
		if (s != slinie)
			return 0;
	}
	return 1;
}

void parcurgere(int x, int v[20], int n , int a[20][20])
{
	v[x] = 1;
	for (int i = 1; i <= n; i++)
		if (a[x][i] == 1 && v[i] == 0)
			parcurgere(i,v,n,a);
}

int conex(int n, int a[20][20])
{
	int v[20] = { 0 };
	parcurgere(1,v,n,a);
	for (int i = 1; i <= n; i++)
		if (v[i] == 0)
			return 0;
	return 1;
}

void distanta(int n, int a[20][20], int dist[20][20])
{
	for (int i = 1; i <= n; i++)
		for (int j = 1; j <= n; j++)
		{
			if (a[i][j] == 1)
				dist[i][j] = 1;
			else
				if (i == j)
					dist[i][j] = 0;
				else
					dist[i][j] = inf;
		}

	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
		{
			for (int k = 1; k <= n; k++)
			{
				dist[i][j] = min(dist[i][k] + dist[k][j], dist[i][j]);
			}
		}
	}
}

void afisare_matrice_distanta(int n, int a[20][20])
{
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			if (a[i][j] == inf)
				cout << "inf ";
			else
				cout << a[i][j] << " ";
		cout << "\n";
	}
}

int main()
{
	int n, a[20][20], b[20][20];
	citire_matrice(n, a);
	nod_izolat(a, n);
	cout << endl;
	int gr = graf_regular(n, a);
	if (gr == 1)
		cout << "graf regular";
	else cout << "nu e graf regular";
	cout << endl;
	int c = conex(n, a);
	if (c == 1)
		cout << "conex";
	else cout << "nu e conex";
	cout << endl;
	distanta(n, a, b);
	afisare_matrice_distanta(n, b);
	return 0;
}