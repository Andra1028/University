#pragma once

#include <vector>

#include <utility>
typedef int TElem;

using namespace std;

class Iterator;

typedef bool(*Relatie)(TElem, TElem);

class Multime {
	friend class Iterator;
private:
	/* aici e reprezentarea */
	Relatie rel;
	int dimensiune;
	int cp = 100;
	//vectorul dinamic de elemente de tip TElem (cu spatiu variabil de memorare - poate fi extins)
	TElem* element;
	//vectorii dinamici pentru legaturi - lista dublu inlantuita
	int* urmator;
	int* anterior;
	//referinta catre primul element al listei
	int primul;
	//referinta catre primul element din lista spatiului liber
	int primLiber;

	//functii pentru alocarea/dealocarea unui spatiu liber
	//se returneaza pozitia unui spatiu liber in lista
	int aloca();
	//dealoca spatiul de indice i
	void dealoca(int i);
	//functie privata care creeaza un nod in lista inlantuita
	int creeazaNod(TElem e);
	//functie privata care redimensioneaza listele
	void redimensioneaza();

public:

	// constructorul implicit al Multimii Ordonate
	Multime(Relatie r);

	// adauga un element in multime
	void adauga(TElem e);

	//cauta un element si returneaza true sau false
	bool cauta(TElem e) const;

	//sterge un element 
	//returneaza adevarat daca s-a gasit elementul de sters
	bool sterge(TElem e);


	//adauga toate elementele din multimea b in multimea curenta
	void reuniune(const Multime& b);
	

	//returneaza numarul elemente din multime 
	int dim() const;

	//verifica daca Multimea Ordonata e vida
	bool vid() const;

	// se returneaza iterator pe Multime
	// iteratorul va returna elementele in ordine in raport cu relatia de ordine
	Iterator iterator() const;

	// destructorul 	
	~Multime();


};
