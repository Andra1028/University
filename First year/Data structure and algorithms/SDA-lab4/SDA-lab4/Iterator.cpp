#include "Iterator.h"
#include "Multime.h"


//constructor
Iterator::Iterator(const Multime& mul) :
	multime(mul) {
	curent = mul.primul;
}

void Iterator::prim() {
	curent = multime.primul;
}

void Iterator::urmator() {
	if (this->curent == -1) {
		throw exception();
	}
	this->curent = multime.urmator[curent];
}

bool Iterator::valid() const {
	return curent != -1;
}

TElem Iterator::element() const {
	if (curent == -1) {
		throw exception();
	}
	return multime.element[curent];
}
