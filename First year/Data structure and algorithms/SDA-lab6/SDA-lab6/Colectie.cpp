#include "Colectie.h"
#include "Iterator.h"
#include <exception>
#include <iostream>

const int NIL = -100000;
const int STERS = 80000;
using namespace std;

int hashCode(TElem e)
{
	//complexitate theta(1)
	return abs(e);
}

int Colectie::hashfunction(TElem e, int i) const
{
	//complexitate theta(1)
	return (hashCode(e) % m + i / 2 + i * i / 2) % m;
}


Colectie::Colectie() {
	/* de adaugat */
	m = NMax;
	//complexitate theta(m)
	for (int i = 0; i < m; i++)
	{
		e[i] = NIL;
	}
	//complexitate theta(m)
	for (int i = 0; i < m; i++)
	{
		frecventa[i] = 0;
	}
}



void Colectie::adauga(TElem elem) {
	//locatia de dispersie a cheii
	//complexitate O(m)
	int i = 0;
	bool gasit = false;

	do
	{
		int ok = 0;
		int j = hashfunction(elem, i);

		if (e[j] == NIL || e[j] == STERS || e[j] == elem)
		{
			e[j] = elem;
			gasit = true;
			frecventa[j]++;
		}
		else
		{
			i++;
		}
	} while (i < m && !gasit);
	lungime++;
}


bool Colectie::sterge(TElem elem) {
	/* de adaugat */
	//complexitate O(m)
	if (vida())
	{
		return false;
	}
	int i = 0;
	bool gasit = false;
	do
	{
		int j = hashfunction(elem, i);
		if (e[j] == elem)
		{
			if (frecventa[j] == 1)
			{
				e[j] = STERS;
				frecventa[j] = 0;
			}
			else
			{
				frecventa[j]--;
			}
			gasit = true;
			lungime--;
		}
		else
		{
			i++;
		}
	} while (i < m && !gasit);
	return gasit;
}


bool Colectie::cauta(TElem elem) const {
	/* de adaugat */
	//complexitate O(m)
	if (vida())
	{
		return false;
	}
	int i = 0;
	bool gasit = false;
	int j;
	do
	{
		j = hashfunction(elem, i);
		if (e[j] == elem)
		{
			gasit = true;
		}
		else
		{
			i++;
		}
	} while (e[j] != NIL && i < m && !gasit);
	return gasit;
	/*for (int i = 0;i < m;i++)
	{
		if (e[i] == elem)
		{
			return true;
		}
	}
	return false;*/
}

int Colectie::numărăElementeUnice() const {
	/* de adaugat */
	//complexitate theta(n)
	if (vida())
	{
		return 0;
	}
	int nr=0;
	for (int i = 0; i < m; i++)
		if (nrAparitii(e[i]) == 1)
			nr++;
	return nr;
}

/*
subalgoritm numaraElementeUnice
daca vida == 0 atunci
	final subalgoritm
nr<-0
pentru i<-0, m executa
	daca nrAparitii(e[i]) = 1 atunci 
			nr<-nr+1
	sfarsit daca
final pentru
returneaza nr
final subalgoritm
*/

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */
	//complexitate O(n)
	if (vida())
	{
		return 0;
	}
	int i = 0;
	int j;
	do
	{
		j = hashfunction(elem, i);
		if (e[j] == elem)
		{
			return frecventa[j];
		}
		else
		{
			i++;
		}
	} while (e[j] != NIL && i < m);
	return 0;
}



int Colectie::dim() const {
	/* de adaugat */
	//complexitate theta(1)
	return lungime;
}


bool Colectie::vida() const {
	/* de adaugat */
	//complexitate theta(1)
	return lungime == 0;

}

IteratorColectie Colectie::iterator() const {
	//complexitate theta(1)
	return  IteratorColectie(*this);
}



Colectie::~Colectie() {
	/* de adaugat */
	//complexitate theta(1)
	//delete e;
	//delete frecventa;
}

/*
* caz favorabil theta(m)
* caz defavorabil O(m^2)
* caz mediu O(m^2)
* overall O(m^2)
*/


