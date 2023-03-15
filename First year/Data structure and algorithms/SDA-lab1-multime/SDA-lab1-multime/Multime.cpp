#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

Multime::Multime() {
	/* de adaugat */
		//setam capacitatea
	this->cp = 100;

	//alocam spatiu de memorare pentru vector
	e = new TElem[cp];

	//setam numarul de elemente
	this->n = 0;
}


bool Multime::adauga(TElem elem) {
	/* de adaugat */
	
	if (cauta(elem))
		return false;

	if (n == cp)
		redim();


	if (!rel(elem, e[n-1]))
	{
       e[n++] = elem;
	   return true;
	}
		
	for (int i = 0; i < n; i++)
	{
		if (rel(elem ,e[i]))
		{
			for (int j = n-1; j >= i; j--)
				e[j+1] = e[j];
			e[i] = elem;
			n++;
			return true;
		}
	}
	return false;
}


bool Multime::sterge(TElem elem) {
	/* de adaugat */
	if(cauta(elem) == false)
		return false;

	if (e[n - 1] == elem)
	{
		n--;
		return true;
	}

	for (int i = 0; i < n - 1; i++)
	{
		if (elem == e[i])
		{
			for (int j = i; j < n - 1; j++)
				e[j] = e[j+1];
			n--;
			return true;
		}
	}

}


bool Multime::cauta(TElem elem) const {
	/* de adaugat */
	for (int i = 0; i < n; i++)
		if (elem == e[i])
			return true;
	return false;
}


int Multime::dim() const {
	/* de adaugat */

	return n;
}



bool Multime::vida() const {
	/* de adaugat */
	return n==0;
}

TElem* Multime::returneaza_lista() const
{
	return e;
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}

void Multime::redim() 
{
	//alocam un spatiu de capacitate dubla
	TElem* eNou = new int[2 * cp];

	//copiem vechile valori in noua zona
	for (int i = 0; i < n; i++)
		eNou[i] = e[i];

	//dublam capacitatea
	cp = 2 * cp;

	//eliberam vechea zona
	delete[] e;

	//vectorul indica spre noua zona
	e = eNou;
}

Multime::~Multime() {
	/* de adaugat */
	delete[] e;
}






