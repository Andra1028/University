#pragma once
#include "Multime.h"

class Multime;

//iterator bidirectional pe multime
class Iterator {
private:
	//pentru a construi un iterator pe o multime este necesar ca un pointer sau referinta la aceasta sa ii fie oferit constructorului
	Iterator(const Multime& mul);
	//contine o referinta catre multimea care se itereaza
	const Multime& multime;
	//retine pozitia curenta in interiorul multimii - referintala nodul curent din lista asociata
	int curent;
public:

	friend class Multime;

	//reseteaza pozitia iteratorului la inceput
	void prim();
	//muta iteratorul pe urmatoarea pozitie
	void urmator();
	//verifica daca iteratorul e valid 
	bool valid() const;
	//returneaza valoarea curenta a elementului 
	TElem element() const;
};


