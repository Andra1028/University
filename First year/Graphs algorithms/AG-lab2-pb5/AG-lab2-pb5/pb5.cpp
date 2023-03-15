#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

ifstream f("input.txt");
const int DMAX = 100;
int n;
vector<int> g[DMAX];
int viz[DMAX];

void citire()
{
	f >> n;
	int n1, n2;
	while (f >> n1 >> n2)
		g[n1].push_back(n2);
}

void dfs(int start)
{
	viz[start] = 1;
	cout << start << " ";
	for (int vecin : g[start])
	{
		if (viz[vecin] == 0)
			dfs(vecin);
	}
}

void afisare_dfs()
{
	for(int i=1;i<=n;i++)
		if (viz[i] == 0)
		{
			dfs(i);
			cout << endl;
		}
}

int main()
{
	citire();
	afisare_dfs();
	return 0;
}