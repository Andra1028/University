#include <iostream>
#include <fstream>

using namespace std;

ifstream f("input.txt");

int a[100][100], n;

void citire()
{
	f >> n;
	int n1, n2;
	while (f >> n1 >> n2)
		a[n1][n2] = 1;
	for (int nod = 1; nod <= n; nod++)
	{
		a[nod][nod] = 1;
	}
}

void matricea_inchiderii_tranzitive()
{
	for (int k = 1; k <= n; k++)
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++)
				if (a[i][j] == 0)
					a[i][j] = (a[i][k] & a[k][j]);
}

void afisare()
{
	for (int i = 1; i <= n; i++)
	{
		for (int j = 1; j <= n; j++)
			cout << a[i][j] << " ";
		cout << endl;
	}
}

int main()
{
	citire();
	matricea_inchiderii_tranzitive();
	afisare();
	return 0;
}