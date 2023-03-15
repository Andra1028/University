#include "Carte.h"
#include <assert.h>
string Carte::getTitlu() const {
	return this->titlu;
}
string Carte::getAutor() const {
	return this->autor;
}
string Carte::getGen() const {
	return this->gen;
}
int Carte::getAn() const {
	return this->an;
}

void Carte::setAutor(string autorNou) {
	this->autor = autorNou;
}
void Carte::setTitlu(string titluNou) {
	this->titlu = titluNou;
}
void Carte::setGen(string genNou) {
	this->gen = genNou;
}
void Carte::setAn(int anNou) {
	this->an = anNou;
}


bool Carte::equal(Carte c) {
	if (this->titlu == c.getTitlu() && this->autor == c.getAutor() && this->gen == c.getGen() && this->an == c.getAn())
		return true;
	return false;
}

bool cmpTitlu(const Carte& c1, const Carte& c2) {
	return c1.getTitlu() < c2.getTitlu();
}

bool cmpAutor(const Carte& c1, const Carte& c2) {
	return c1.getAutor() < c2.getAutor();
}

bool cmpGenAn(const Carte& c1, const Carte& c2) {
	if (c1.getAn() == c2.getAn())
		return c1.getGen() < c2.getGen();
	else return c1.getAn() < c2.getAn();
}


void testGetSet() {
	Carte carte1{ "Marele Gatsby", "Scott Fitzgerald", "Roman modernist", 1925 };
	assert(carte1.getTitlu() == "Marele Gatsby");
	assert(carte1.getAutor() == "Scott Fitzgerald");
	assert(carte1.getGen() == "Roman modernist");
	assert(carte1.getAn() == 1925);

	Carte carte2{ "Jocurile foamei", "Suzanne Collins", "SF", 2008 };
	assert(carte2.getTitlu() == "Jocurile foamei");
	assert(carte2.getAutor() == "Suzanne Collins");
	assert(carte2.getGen() == "SF");
	assert(carte2.getAn() == 2008);

	carte2.setTitlu("Harry Potter");
	carte2.setAutor("J. K. Rowling");
	carte2.setGen("Fantastic");
	carte2.setAn(1997);

	assert(carte2.getTitlu() == "Harry Potter");
	assert(carte2.getAutor() == "J. K. Rowling");
	assert(carte2.getGen() == "Fantastic");
	assert(carte2.getAn() == 1997);



}

void testeDomain() {
	testGetSet();
}