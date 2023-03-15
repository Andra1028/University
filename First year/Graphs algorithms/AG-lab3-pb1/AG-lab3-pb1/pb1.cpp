#include <fstream>
#include <iostream>
using namespace std;

ifstream fin("1in.txt");
ofstream fout("1out.txt");
const int INF = 100000000000;
int c[20][20], d[20], t[20], p[20];


void dijkstra(int s, int n)
{
    int i, j, k, minim;
    for (i = 0; i < n; i++)
    {
        d[i] = c[s][i];
        if (i != s && d[i] != INF)
            t[i] = s;
    }
    p[s] = 1;
    for (k = 0; k < n; k++)
    {
        minim = INF;
        for (i = 0; i < n; i++)
            if (!p[i] && d[i] < minim)
            {
                minim = d[i];
                j = i;
            }
        for (i = 0; i < n; i++)
            if (!p[i])
                if (d[i] > d[j] + c[j][i])
                {
                    d[i] = d[j] + c[j][i];
                    t[i] = j;
                }
        p[j] = 1;
    }
}

int main(/*int argc, char* argv[]*/)
{
    ///ifstream fin(argv[1]);
    ///ofstream fout(argv[2]);
    int i, j, x, y, cost, v, e, s;
    fin >> v >> e >> s;
    for (i = 0; i < v; i++)
        for (j = 0; j < v; j++)
            if (i == j) c[i][j] = 0;
            else c[i][j] = INF;
    for (int i = 1; i <= e; i++)
    {
        fin >> x >> y >> cost;
        c[x][y] = cost;
    }
    /*for (i = 0; i < v; i++)
    {
        for (j = 0; j < v; j++)
            cout<<c[i][j]<<" ";
        cout<<endl;
    }*/


    dijkstra(s, v);
    for (int i = 0; i < v; i++)
        if (d[i] == INF)
            fout << "INF ";
        else fout << d[i] << " ";
    ///fout << argv[1] << " " << argv[2];
    ///fin.close();
    ///fout.close();
    return 0;
}
