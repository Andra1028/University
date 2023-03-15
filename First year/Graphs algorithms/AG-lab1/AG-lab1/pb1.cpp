#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("date.txt");

int a[20][20], b[20][20];

void matrice_adiacenta(int &n, int x, int y)
{
	fin >> n;
	for (int i = 1; i <= n; i++)
	{
		fin >> x >> y;
		a[x][y] = a[y][x] = 1;
	}

	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			cout << a[i][j] << " ";
		cout << endl;
	}
}

void lista_de_adiacenta(int n)
{
	for (int i = 1; i <= n; i++)
	{
		cout << i << ": ";
		for (int j = 1; j <= n; j++)
			if (a[i][j] == 1)
				cout << j << " ";
		cout << endl;
	}
}

void matrice_incidenta(int n)
{
	int nr = 0;
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			if (a[i][j] == 1 && i<j)
			{
				nr++;
                b[i][nr] = 1;
				b[j][nr] = 1;
			}
	}
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= nr; j++)
			cout << b[i][j] << " ";
		cout << endl;
	}
}

int main()
{
	int n=0,x=0,y=0,b[20][20]={{0}}, nr = 0;
	matrice_adiacenta(n, x, y);
	cout << endl;
	lista_de_adiacenta(n);
	cout << endl;
	matrice_incidenta(n);
	return 0;
}