#include "Cos.h"
#include "Repository.h"
#include <assert.h>
#include <exception>
using std::shuffle;

void Cos::valideazaCos(const Carte& c) {
	vector<Carte> carti = this->CosCarti;
	std::string titlu = c.getTitlu();
	if (std::any_of(carti.begin(), carti.end(),
		[&](Carte& carte) {return carte.getTitlu() == titlu; }))
		throw std::exception();
}

void Cos::addCos(const Carte& s) {
	this->CosCarti.push_back(s);
	this->notify();
}
void Cos::emptyCos() {
	this->CosCarti.clear();
	this->notify();
}

void Cos::addRandomBooks(vector<Carte> originalBooks, int howMany) {
	shuffle(originalBooks.begin(), originalBooks.end(), std::default_random_engine(std::random_device{}())); //amesteca vectorul v
	while (CosCarti.size() < howMany && originalBooks.size() > 0) {
		CosCarti.push_back(originalBooks.back());
		originalBooks.pop_back();
	}
	this->notify();
}
const vector<Carte>& Cos::getAllCos() {
	return this->CosCarti;
}

void test_adauga() {
	Cos cos;
	Carte carte1{ "carte1", "autor1", "sf", 2010 };
	cos.addCos(carte1);
	assert(cos.getAllCos().size() == 1);

	Carte carte2{ "carte2", "autor2", "sf", 2012 };
	//try {
	//	program.adauga_program(activity2);
	//}
	//catch (ProgException& ve) {
	//	std::cout << ve.get_errors();
	//	assert(ve.get_errors() == "");
	//}
	cos.addCos(carte2);
	assert(cos.getAllCos().size() == 2);

	/*try {
		cos.addCos(carte2);
	}
	catch (std::exception) {
		assert(false);
	}*/

	cos.emptyCos();
	assert(cos.getAllCos().size() == 0);
}


void test_adauga_random() {
	Cos cos;
	BookRepository repo;
	Carte carte1{ "carte1", "autor1", "sf", 2010 };
	repo.store(carte1);


	Carte carte2{ "carte2", "autor2", "sf", 2012 };
	repo.store(carte2);

	Carte carte3{ "carte3", "autor3", "sf", 2008 };
	repo.store(carte3);

	vector<Carte> carti;

	cos.addRandomBooks(carti, 2);
	assert(cos.getAllCos().size() == 0);

	cos.emptyCos();
	assert(cos.getAllCos().size() == 0);
}

void teste_cos()
{
	test_adauga();
	test_adauga_random();
}