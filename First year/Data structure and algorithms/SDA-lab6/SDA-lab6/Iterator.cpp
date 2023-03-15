#include "Iterator.h"
#include "Colectie.h"

/*
* v = [1, 2, 3, 3, 4, 2, 1]
*
* D = [1, 2, 3, 4]
* P = [0, 1, 2, 2, 3, 1, 0]
*
*
*/
const int NIL = -100000;

void IteratorColectie::deplasare()
{
	//complexitate O(m)
	while (curent < col.m && col.e[curent] == NIL)
	{
		curent++;
	}
}

IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	/* de adaugat */
	//complexitate theta(1)
	curent = 0;
	frecventa = 1;
	deplasare();
}

void IteratorColectie::prim() {
	/*
	* i = 0
	*/
	//complexitate theta(1)
	curent = 0;
	frecventa = 1;
	deplasare();
}


void IteratorColectie::urmator() {
	/* de adaugat*/
	//complexitate theta(1)
	if (frecventa < col.frecventa[curent])
	{
		frecventa++;
	}
	else
	{
		curent++;
		frecventa = 1;
		deplasare();
	}
}



bool IteratorColectie::valid() const {
	/* de adaugat  i >= 0 &&i < c.dim()*/
	//complexitate theta(1)
	if (col.vida())
	{
		return false;
	}
	return (curent < col.m);
}


TElem IteratorColectie::element() const {
	/* de adaugat */
	//complexitate theta(1)
	return col.e[curent];
}