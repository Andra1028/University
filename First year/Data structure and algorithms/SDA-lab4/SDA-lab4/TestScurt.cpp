#include "TestScurt.h"
#include "Multime.h"
#include "Iterator.h"
#include  <iostream>
#include <assert.h>

using namespace std;

bool relatie1(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

void testAll() { //apelam fiecare functie sa vedem daca exista
	int vverif[5];
	int iverif;
	TElem e;

	Multime m1 = Multime(relatie1);
	m1.adauga(5);
	m1.adauga(1);
	m1.adauga(10);
	assert(m1.dim() == 3);
	///cout << m1.dim() << endl;

	///cout << m1.returneaza_lista()[0] << " " << m1.returneaza_lista()[1] << " " << m1.returneaza_lista()[2];
	Iterator im1 = m1.iterator();
	im1.prim();
	iverif = 0;
	e = im1.element();
	vverif[iverif++] = e;
	im1.urmator();
	while (im1.valid()) {
		assert(relatie1(e, im1.element()));
		e = im1.element();
		vverif[iverif++] = e;
		im1.urmator();
	}
	assert((vverif[0] == 1) && (vverif[1] == 5) && (vverif[2] == 10));


	Multime m=Multime(relatie1);
	//return;
	assert(m.vid() == true);
	assert(m.dim() == 0); //adaug niste elemente
	m.adauga(5);
	m.adauga(1);
	m.adauga(10);
	m.adauga(7);
	m.adauga(-3);
	assert(m.dim() == 5);
	assert(m.cauta(10) == true);
	assert(m.cauta(16) == false);
	assert(m.sterge(1) == true);
	assert(m.sterge(6) == false);
	assert(m.dim() == 4);

	Iterator im = m.iterator();
	iverif = 0;
	im.prim();
	e = im.element();
	vverif[iverif++] = e;
	im.urmator();
	while (im.valid()) {
		assert(relatie1(e, im.element()));
		e = im.element();
		vverif[iverif++] = e;

		im.urmator();
	}

	///cout << m.dim() << endl;
	///cout << vverif[0]<<" " << vverif[1] << " " << vverif[2] << " " << vverif[3]<<" ";
	assert((vverif[3] == 10) && (vverif[2] == 7) && (vverif[1] == 5) && (vverif[0] == -3));

}
