#include <exception>
#include <iostream>
#include "LI.h"
#include "IteratorLI.h"

Nod::Nod(TElem e, PNod urm, PNod ant) {
	this->e = e;
	this->urm = urm;
	this->ant = ant;
}

TElem Nod::element() {
	return e;
}

PNod Nod::urmator() {
	return urm;
}

PNod Nod::anterior() {
	return ant;
}

LI::LI() {
	/* de adaugat */
	prim = nullptr;
}


int LI::dim()  {
	/* de adaugat */
	int n = 0;
	PNod copie = prim;
	while (copie != nullptr) {
		copie = copie->urm;
		n++;
	}
	return n;
}


bool LI::vida() {
	/* de adaugat */
	
	return prim == nullptr;
}

TElem LI::element(int i){
	/* de adaugat */
	if (i > this->dim())
		throw std::exception();
	int nr = 0;
	PNod copie = prim; 
	while (copie != nullptr ) {
		
		if (nr == i)
			return copie->e;
		nr++;
		copie = copie->urm;
	}

}

TElem LI::modifica(int i, TElem e) {
	/* de adaugat */
	if (i > this->dim())
		throw std::exception();
	int nr = 0;
	PNod copie = prim;
	while (copie!= nullptr) {

		if (nr == i)
		{
             TElem val = copie->e;
			 copie->e = e;
			 return val;
		}
			
		nr++;
		copie = copie->urm;
	}
}

void LI::adaugaSfarsit(TElem e) {
	/* de adaugat */

	PNod nodNou = new Nod(e, nullptr, nullptr);

	if (prim == nullptr)
	{
		prim = nodNou;
		return;
	}
	else {
		PNod copie = prim;
		while (copie ->urm!= nullptr)
		{
			copie = copie->urm;
		}

		copie->urm = nodNou;
		copie->ant = copie;
	}
}

void LI::afisare()
{
	PNod copie = prim;
	while (copie != nullptr)
	{
		std::cout << copie->e << " ";
		copie = copie->urm;
	}
}

void LI::adauga(int i, TElem e) {
	/* de adaugat */
	if (i > this->dim())
		throw std::exception();
	if (i == 0)
	{
		PNod q = new Nod(e, nullptr, nullptr);
		q->urm = prim;
		prim->ant = q;
		prim = q;
	}
	else {
		int nr = 1;
		PNod copie = prim;
		copie = copie->urm;
		while (copie != nullptr)
		{
			if (nr == i)
			{
				PNod q = new Nod(e, nullptr, nullptr);
				q->urm = copie;
				q->ant = copie->ant;
				copie->ant->urm = q;
				copie->ant = q;
				return;
			}
			else {
				nr++;
				copie = copie->urm;
			}

		}
	}
	
}

TElem LI::sterge(int i) {
	/* de adaugat */
	if (i > this->dim())
		throw std::exception();
	int nr = 0;
	if (i == 0)
	{
		TElem val = prim->e;
		prim->urm->ant = nullptr;
		prim = prim->urm;
		return val;
	}
	PNod copie = prim;
	while (copie->urm != nullptr)
	{
		if (nr == i)
		{
			TElem val = copie->e;
			copie->urm->ant = copie->ant;
			copie->ant->urm = copie->urm;
			return val;
		}
		nr++;
		copie = copie->urm;
	}
}

int LI::cauta(TElem e) {
	/* de adaugat */
	int nr = 0;
	PNod copie = prim;
	while (copie != nullptr) {
		if (copie->e == e)
			return nr;
		copie = copie->urm;
		nr++;
	}
	return -1;
}

IteratorLI LI::iterator() const {
	return  IteratorLI(*this);
}

LI::~LI() {
	/* de adaugat */
	while (prim != nullptr) {
		PNod p = prim;
		prim = prim->urm;
		delete p;
	}
}
