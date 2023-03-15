#pragma once

typedef int TElem;
class IteratorLI;

//referire a clasei Nod

class Nod;



//se defineste tipul PNod ca fiind adresa unui Nod

typedef Nod* PNod;



class Nod

{

public:

	friend class IteratorLI;
	friend class LI;

	//constructor

	Nod(TElem e, PNod urm, PNod ant);

	TElem element();

	PNod urmator();
	PNod anterior();

private:

	TElem e;

	PNod urm;
	PNod ant;

};

class LI {
private:
	friend class IteratorLI;
	friend class Nod;
	PNod prim;
	

public:
	// constructor implicit
	LI();

	// returnare dimensiune
	int dim() ;

	// verifica daca lista e vida
	bool vida();

	// returnare element
	//arunca exceptie daca i nu e valid
	TElem element(int i) ;

	// modifica element de pe pozitia i si returneaza vechea valoare
	//arunca exceptie daca i nu e valid
	TElem modifica(int i, TElem e);

	void afisare();

	// adaugare element la sfarsit
	void adaugaSfarsit(TElem e);

	// adaugare element pe o pozitie i 
	//arunca exceptie daca i nu e valid
	void adauga(int i, TElem e);

	// sterge element de pe o pozitie i si returneaza elementul sters
	//arunca exceptie daca i nu e valid
	TElem sterge(int i);

	// cauta element si returneaza prima pozitie pe care apare (sau -1)
	int cauta(TElem e);

	// returnare iterator
	IteratorLI iterator() const;

	//destructor
	~LI();

};
