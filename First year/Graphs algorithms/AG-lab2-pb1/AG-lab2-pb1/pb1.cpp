#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

ifstream f("graf.txt");

const int MAX = 1e9;

int n;
vector<int> graf[100];
bool viz[100];
int dist[100];
int pred[100];
queue<int> q;

void citire_fisier() {
	f >> n;
	int n1, n2;
	while (f >> n1 >> n2)
	{
		graf[n1].push_back(n2);
		graf[n2].push_back(n1);
	}
		
}

void bfs_moore(int start) {
	for (int i = 1; i <= n; i++)
		dist[i] = MAX;

	q.push(start);
	viz[start] = 1;
	dist[start] = 0;
	pred[start] = 0;
	while (!q.empty()) {
		int nod = q.front();
		q.pop();
		for (int vecin : graf[nod]) {
			if (viz[vecin] == 0) {
				viz[vecin] = 1;
				dist[vecin] = dist[nod] + 1;
				pred[vecin] = nod;
				q.push(vecin);
			}
		}
	}
}

void afisare(int nod) {
	if (nod == 0)
		return;
	else
	{
       afisare(pred[nod]);
	   cout << nod << ' ';
	}
		
}

void moore() {
	cout << "Nodul de start: ";
	int start;
	cin >> start;

	bfs_moore(start);
	for (int nod = 1; nod<=n; nod++)
	{
		if (nod != start)
		{
           afisare(nod);
		   cout << endl;
		}	
	}
}

int main()
{
	citire_fisier();
	moore();
}