#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

ifstream f("input.txt");
const int DMAX = 100;
const int MAX = 1e9;
vector<int> graf[DMAX];
int dist[DMAX];
bool viz[DMAX];
queue<int> q;
int n, ok;

void citire()
{
	f >> n;
	int n1, n2;
	while (f >> n1 >> n2)
		graf[n1].push_back(n2);

}

void bfs(int start) {
	for (int i = 1; i <= n; i++)
		dist[i] = MAX;

	q.push(start);
	viz[start] = 1;
	dist[start] = 0;
	while (!q.empty()) {
		int nod = q.front();
		q.pop();
		cout  << nod << ", ";
		if (dist[nod] == MAX)
			cout << 'x' << '\n';
		else
		{
			if (ok == 0)
				cout << dist[nod];
			else cout << "0";
		}
			
		for (int vecin : graf[nod]) {
			if (viz[vecin] == 0) {
				viz[vecin] = 1;
				dist[vecin] = dist[nod] + 1;
				q.push(vecin);
			}
		}
		cout << '\n';
	}
}

void afisare_bfs() {
	cout << "start: ";
	int start;
	cin >> start;
	ok = 0;
	bfs(start);
	ok = 1;
	for (int i = 1; i <= n; i++) {
		if (viz[i] == 0) {
			bfs(i);
			cout << '\n';
			///break;
		}
	}
}

int main()
{
	citire();
	afisare_bfs();
	return 0;
}