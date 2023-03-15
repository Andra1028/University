#include "IteratorMultime.h"
#include "Multime.h"
#include <exception>


IteratorMultime::IteratorMultime(const Multime& m) : mult(m) {
	/* de adaugat */
	curent =0;
}

TElem IteratorMultime::element() const {
	/* de adaugat */
	if(!valid())
		throw std::exception();

	return mult.returneaza_lista()[curent];
}

bool IteratorMultime::valid() const {
	/* de adaugat */
	return curent < mult.dim();
}

void IteratorMultime::urmator() {
	/* de adaugat */
	curent++;
}

void IteratorMultime::prim() {
	/* de adaugat */
	curent = 0;
}

