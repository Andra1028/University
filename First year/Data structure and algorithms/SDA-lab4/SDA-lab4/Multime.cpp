#include "Iterator.h"
#include "Multime.h"
#include <iostream>
#include <vector>
#include <unordered_map>

#include <exception>
using namespace std;

Multime::Multime(Relatie r) { // complexitate-timp Theta(n)
	/* de adaugat */
	element = new TElem[cp];
	urmator = new int[cp];
	anterior = new int[cp];
	//relatia dintre elemente
	rel = r;
	//dimensiunea initiala e 0
	dimensiune = 0;
	//lista e vida
	primul = -1;
	//se initializeaza lista spatiului liber - toate pozitiile din vector sunt marcate ca fiind libere
	for (int i = 0; i < cp - 1; i++)
		urmator[i] = i + 1;
	urmator[cp - 1] = -1;
	for (int i = 1; i < cp; i++)
		anterior[i] = i - 1;
	anterior[0] = -1;
	//referinta spre prima pozitie libera din lista
	primLiber = 0;
}

int Multime::aloca() { // complexitate-timp Theta(1)
	//se sterge primul element din lista spatiului liber
	int i = primLiber;
	if (urmator[primLiber]!=-1)
		primLiber = urmator[primLiber];
	return i;
}

void Multime::dealoca(int i) { // complexitate-timp Theta(1)
	//se trece pozitia i in lista spatiului liber
	urmator[i] = primLiber;
	anterior[i] = -1;
	primLiber = i;
}

void Multime::redimensioneaza() { // complexitate-timp Theta(n)
	//dublam capacitatea
	cp *= 2;
	int* new_allocation_urmator = new int[cp];
	int* new_allocation_anterior = new int[cp];
	TElem* new_allocation_element = new TElem[cp];
	int i;
	//initializam prima parte a listelor (deja completata)
	for (i = 0; i < dimensiune; i++)
	{
		new_allocation_urmator[i] = urmator[i];
		new_allocation_anterior[i] = anterior[i];
		new_allocation_element[i] = element[i];
	}
	//initializam a doua parte a listelor
	for (i = dimensiune; i < cp - 1; i++)
	{
		new_allocation_urmator[i] = i + 1;
		new_allocation_anterior[i] = i - 1;
	}
	new_allocation_urmator[cp - 1] = -1;
	new_allocation_anterior[cp - 1] = cp - 2;

	//stergem datele din listele initiale
	delete[] urmator;
	delete[] anterior;
	delete[] element;
	//initializam listele cu listele redimensionate
	urmator = new_allocation_urmator;
	anterior = new_allocation_anterior;
	element = new_allocation_element;
}

//creeaza un nod in lista inlantuita unde se memoreaza informatia utila e
int Multime::creeazaNod(TElem e) { // complexitate-timp Theta(1)
	//daca s-ar folosi vector dinamic, s-ar redimensiona in cazul in care colectia ar fi plina (primLiber=0)
	int i = aloca();
	if (i != -1) { //exista spatiu liber in lista
		element[i] = e;
		urmator[i] = -1;
		anterior[i] = -1;
	}
	return i;
}

// adaugare element 
/*
	Complexitati:
		Caz favorabil: complexitate-timp Theta(1)
			- inseram pe prima pozitie
			- inseram inaintea primei pozitii
		Caz defavorabil: complexitate-timp Theta(n)
			- inseram pe ultima pozitie
		Caz mediu: complexitate-timp Theta(n) <= T(n) = (1+2+...+(n-1))/n
			- inseram undeva in interiorul listei
	Complexitate generala: complexitate-timp Theta(n)
*/
void Multime::adauga(TElem e) {
	/* de adaugat */
	if (dimensiune == cp - 1) {
		redimensioneaza();
	}
	TElem elementul(e);
	auto nou = creeazaNod(elementul);
	//in cazul folosirii unui vector static, e posibil ca i sa fie -1 in cazul in care lista e plina
	if (nou != -1) {
		//multimea e vida => adaugarea primului element
		if (primul == -1) {
			urmator[nou] = primul;
			primul = nou;
		}
		//adaugare inainte de primul element, dupa relatie
		else if (!rel(element[primul], e)) {
			anterior[primul] = nou;
			urmator[nou] = primul;
			primul = nou;
		}
		//adaugare inauntrul multimii sau la finalul multimii
		else {
			//parcurgem lista pana gasim elementul sau pana ajungem la final
			auto nodCurent = primul;
			while (urmator[nodCurent] != -1 && rel(element[urmator[nodCurent]], e) && e != element[nodCurent]) {
				nodCurent = urmator[nodCurent];
			}

			//daca mai exista elemente cu val e nu adaugam
			if (e == element[nodCurent]) {
				return;
			}

			//facem conexiunile pentru a adauga noul nod
			urmator[nou] = urmator[nodCurent];
			anterior[nou] = nodCurent;
			//daca NU e ultimul nod din lista
			if (urmator[nodCurent] != -1)
				anterior[urmator[nodCurent]] = nou;
			urmator[nodCurent] = nou;
		}
		//incrementam dimensiunea
		dimensiune++;
		///cout << e << endl;
	}
}

/*
	Complexitati:
		Caz favorabil: complexitate-timp Theta(1)
			- elementul s-ar afla in afara listei
			- elementul este pe prima pozitie
		Caz defavorabil: complexitate-timp Theta(n)
			- elementul exista si este pe ultima pozitie
		Caz mediu: complexitate-timp Theta(n) <= T(n) = (1+2+...+(n-1))/n
			- elementul este undeva in interiorul listei
	Complexitate generala: complexitate-timp Theta(n)
*/
bool Multime::cauta(TElem c) const {
	/* de adaugat */
	int point = primul;
	vector<TElem> vector_valori;

	if (point == -1) {
		return false;
	}

	while (point != -1)
	{
		if (element[point] == c)
			return true;
		point = urmator[point];
	}

	return false;
}

/*
	Complexitati:
		Caz favorabil: complexitate-timp Theta(1)
			- elementul nu exista in lista
			- elementul este pe prima pozitie
		Caz defavorabil: complexitate-timp Theta(n)
			- elementul exista si este pe ultima pozitie
		Caz mediu: complexitate-timp Theta(n) <= T(n) = (1+2+...+(n-1))/n
			- elementul este undeva in interiorul listei
	Complexitate generala: complexitate-timp Theta(n)
*/
bool Multime::sterge(TElem c) {
	/* de adaugat */
	int pointer = primul;
	if (pointer != -1) {
		//daca e primul si singurul element
		if (urmator[primul] == -1) {
			dealoca(primul);
			primul = -1;
		}
		//daca e primul element
		else if (element[primul] == c) {
			auto aux = urmator[primul];
			dealoca(primul);
			primul = aux;
			anterior[aux] = -1;
		}
		else {
			auto nodCurent = pointer;
			while (urmator[nodCurent] != -1 && element[nodCurent] != c ) {
				nodCurent = urmator[nodCurent];
			}

			//daca nu exista un nod cu valoarea data
			if ( element[nodCurent] != c) {
				return false;
			}
			//daca e ultimul element
			else if (urmator[nodCurent] == -1 && element[nodCurent] == c ) {
				dealoca(urmator[nodCurent]);
				urmator[nodCurent] = -1;
			}
			//daca e intre primul si ultimul element
			else {
				urmator[anterior[nodCurent]] = urmator[nodCurent];
				anterior[urmator[nodCurent]] = anterior[nodCurent];
				dealoca(nodCurent);
			}
		}
		dimensiune--;
		return true;
	}
	else {
		return false;
	}
}


/*
		* Complexitate:	Best case = Worst case = Average case = Theta(m) unde m este nr de elemente din multime
		SubAlgoritm reuniune(m,b)
			curentB <- b.primul
			CatTimp curentB <> -1 executa
				[m].adauga(b.element[curentB])
				curentB <- b.urmator[curentB]
			sf_catTimp
		sf_subAlg
		*/
void Multime::reuniune(const Multime& b)
{
	int curentB = b.primul;
	while (curentB != -1)
	{
		adauga(b.element[curentB]);
		curentB = b.urmator[curentB];
	}
}

//returnare dimensiune
//returneaza numarul de perechi (cheie, valoare) din dictionar
int Multime::dim() const { // complexitate-timp Theta(1)
	/* de adaugat */
	return this->dimensiune;
}

//verifica daca MDO e vid
bool Multime::vid() const { // complexitate-timp Theta(1)
	/* de adaugat */
	return this->dimensiune == 0;
}

Iterator Multime::iterator() const { // complexitate-timp Theta(1)
	return Iterator(*this);
}


//destructor
Multime::~Multime() { // complexitate-timp Theta(n)
	/* de adaugat */
	while (primul != -1) {
		auto aux = primul;
		primul = urmator[primul];
		dealoca(aux);
	}
}
