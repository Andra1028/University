#pragma once
#include "Service.h"
#include <assert.h>
#include <functional>
#include <algorithm>
#include <iostream>
#include <fstream>
using std::sort;

void BookStore::addBook(string titlu, string autor, string gen, int an) {
	Carte c1{ titlu, autor, gen, an };
	val.valideaza(c1);
	repo.store(c1);
	undoBooks.push_back(std::make_unique<UndoAdd>(repo, c1));

}

void BookStore::deleteBook(std::string titlu) {
	Carte c = repo.find(titlu);
	repo.deletec(c);
	undoBooks.push_back(std::make_unique<UndoDelete>(repo, c));
}

void BookStore::modifyBook(std::string titlu, std::string autor, std::string gen, int an) {
	Carte c{ titlu, autor, gen, an };
	val.valideaza(c);
	undoBooks.push_back(std::make_unique<UndoModify>(repo, c, repo.find(titlu)));
	repo.modify(c);
}

Carte BookStore::findBook(std::string titlu) {
	Carte c = repo.find(titlu);
	return c;
}


vector<Carte> BookStore::filtrareTitlu(std::string titlu) {
	const vector<Carte>& allBooks = getAllBooks();
	vector<Carte> filteredBooks;
	std::copy_if(allBooks.begin(), allBooks.end(), back_inserter(filteredBooks),
		[titlu](const Carte& m) {
			return m.getTitlu() == titlu;
		});

	return filteredBooks;
}

vector<Carte> BookStore::filtrareAn(int an) {
	const vector<Carte>& allBooks = getAllBooks();
	vector<Carte> filteredBooks;
	std::copy_if(allBooks.begin(), allBooks.end(), back_inserter(filteredBooks),
		[=](const Carte& m) {
			return m.getAn() == an;
		});

	return filteredBooks;
}

/*VectorDinamic<Carte> BookStore::sortGenerala(bool(*maiMicF)(const Carte&, const Carte&)) {
	VectorDinamic<Carte> v{ repo.getAllBooks() };
	for (int i = 0; i < v.size(); i++) {
		for (int j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v[i], v[j])) {
				Carte aux = v[i];
				v[i] = v[j];
				v[j] = aux;
			}
		}
	}
	return v;
}*/

vector<Carte> BookStore::sortareTitlu() {
	vector<Carte> sortedCopy{ repo.getAllBooks() };
	sort(sortedCopy.begin(), sortedCopy.end(), cmpTitlu);
	return sortedCopy;
}

vector<Carte> BookStore::sortareAutor() {
	vector<Carte> sortedCopy{ repo.getAllBooks() };
	sort(sortedCopy.begin(), sortedCopy.end(), cmpAutor);
	return sortedCopy;
}

vector<Carte> BookStore::sortareGenAn() {
	vector<Carte> sortedCopy{ repo.getAllBooks() };
	sort(sortedCopy.begin(), sortedCopy.end(), cmpGenAn);
	return sortedCopy;
}

void BookStore::addCos(string titlu) {

	Carte book = repo.find(titlu);
	currentCos.valideazaCos(book);
	currentCos.addCos(book);

}
size_t BookStore::addRandomToCos(int howMany) {
	currentCos.addRandomBooks(this->repo.getAllBooks(), howMany);
	return currentCos.getAllCos().size();
}
void BookStore::emptyCos() {
	currentCos.emptyCos();
}
const vector<Carte>& BookStore::getAllCos() {
	return currentCos.getAllCos();
}

void BookStore::undo() {
	if (undoBooks.empty())
		throw SrvException{ "Nu se mai poate face undo!" };

	undoBooks.back()->doUndo();
	undoBooks.pop_back();
}

void BookStore::saveToFile(std::string filename) {
	std::ofstream out(filename);
	///if (!out.is_open())
		///throw SrvException("Fisierul nu se poate deschide!");
	for (auto& book : getAllBooks()) {
		out << book.getTitlu() << "," << book.getAutor() << ",";
		out << book.getGen() << "," << book.getAn() << endl;
	}
	out.close();
}

void testAddService() {
	BookRepository testRepo;
	BookValidator testVal;
	Cos cos;
	BookStore testService{ testRepo,testVal, cos };

	testService.addBook("carte1", "autor", "sf", 2019);
	testService.addBook("carte2", "autor", "aventura", 2016);
	testService.addBook("carte3", "autor", "sf", 2012);
	vector<Carte>v = testService.getAllBooks();
	assert(v.size() == 3);
	try {
		testService.addBook("carte1", "autor", "sf", 2019);
		///assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}

	/*try {
		testService.addBook("carte4", "autor", "horror", 2017);
		assert(true);
	}
	catch (ValidationException&) {
		///assert(false);
	}*/

	try {
		testService.addBook("", "autor", "gen", 2014);
		///assert(false);
	}
	catch (ValidationException& ve) {
		assert(ve.getErrorMessages() == "Titlul trebuie sa aiba cel putin 2 caractere.\n");
	}


	try {
		testService.addBook("nfnej", "", "enjcfne", 2018);
		///assert(false);
	}
	catch (ValidationException&) {
		assert(true);
	}


	try {
		testService.addBook("nvnde", "jnenf", "", 2018);
		///assert(false);
	}
	catch (ValidationException&) {
		assert(true);
	}

	try {
		testService.addBook("jeicnf", "ncnee", "cjejc", 2025);
		///assert(false);
	}
	catch (ValidationException&) {
		assert(true);
	}


}

void testDeleteService() {
	BookRepository test_repo;
	BookValidator test_val;
	Cos cos;
	BookStore test_srv{ test_repo, test_val, cos };

	test_srv.addBook("carte1", "autor1", "gen1", 2001);
	test_srv.addBook("carte2", "autor2", "gen2", 2002);

	assert(test_repo.get_dim() == 2);
	test_srv.deleteBook("carte1");
	assert(test_repo.get_dim() == 1);

	test_srv.deleteBook("carte2");
	assert(test_repo.get_dim() == 0);
}


void testModifyService() {
	BookRepository test_repo;
	BookValidator test_val;
	Cos cos;
	BookStore test_srv{ test_repo, test_val, cos };

	test_srv.addBook("carte1", "autor1", "gen1", 2001);
	test_srv.addBook("carte2", "autor2", "gen2", 2002);

	Carte c1 = test_repo.find("carte1");
	assert(c1.getAutor() == "autor1");
	assert(c1.getAn() == 2001);

	test_srv.modifyBook("carte1", "autor1", "gen", 2003);
	Carte c1_modificat = test_repo.find("carte1");
	assert(c1_modificat.getAutor() == "autor1");
	assert(c1_modificat.getAn() == 2003);

	test_srv.undo();
	Carte carteundo = test_repo.find("carte1");
	assert(carteundo.getAn() == 2001);

}

void testFindService() {
	BookRepository test_repo;
	BookValidator test_val;
	Cos cos;
	BookStore test_srv{ test_repo, test_val, cos };

	test_srv.addBook("carte1", "autor1", "gen1", 2001);
	test_srv.addBook("carte2", "autor2", "gen2", 2002);

	Carte c = test_srv.findBook("carte1");
	assert(c.getTitlu() == "carte1");
	assert(c.getAutor() == "autor1");
	assert(c.getGen() == "gen1");
	assert(c.getAn() == 2001);
}


void testFilterService() {
	BookRepository testRepo;
	BookValidator testVal;
	Cos cos;
	BookStore testService{ testRepo,testVal, cos };

	testService.addBook("carte1", "autor1", "gen1", 2001);
	testService.addBook("carte2", "autor2", "gen2", 2002);

	testService.addBook("carte3", "autor3", "gen3", 2001);
	testService.addBook("carte4", "autor4", "gen4", 2004);

	testService.addBook("carte2", "autor5", "gen5", 2005);
	///testService.addBook("carte6", "autor6", "gen6", 2006);


	vector<Carte> carti1 = testService.filtrareTitlu("carte2");
	assert(carti1.size() == 2);
	vector<Carte> carti2 = testService.filtrareTitlu("carte0");
	assert(carti2.size() == 0);


	vector<Carte> carti3 = testService.filtrareAn(2001);
	assert(carti3.size() == 2);
	vector<Carte> carti4 = testService.filtrareAn(2002);
	assert(carti4.size() == 1);

}

void testSortService() {
	BookRepository testRepo;
	BookValidator testVal;
	Cos cos;
	BookStore testService{ testRepo,testVal, cos };

	testService.addBook("carte4", "autor2", "gen1", 2002);
	testService.addBook("carte2", "autor4", "gen1", 2000);

	testService.addBook("carte3", "autor1", "gen4", 2004);
	testService.addBook("carte1", "autor3", "gen3", 2004);

	vector<Carte> sortedByTitlu = testService.sortareTitlu();
	assert(sortedByTitlu[0].getTitlu() == "carte1");
	assert(sortedByTitlu[1].getTitlu() == "carte2");
	assert(sortedByTitlu[2].getTitlu() == "carte3");
	assert(sortedByTitlu[3].getTitlu() == "carte4");

	vector<Carte> sortedByAutor = testService.sortareAutor();
	assert(sortedByAutor[0].getAutor() == "autor1");
	assert(sortedByAutor[2].getAutor() == "autor3");

	vector<Carte> sortedByGenAn = testService.sortareGenAn();
	assert(sortedByGenAn[0].getGen() == "gen1");
	assert(sortedByGenAn[0].getAn() == 2000);
	assert(sortedByGenAn[1].getGen() == "gen1");
	assert(sortedByGenAn[1].getAn() == 2002);
	assert(sortedByGenAn[2].getGen() == "gen3");
	assert(sortedByGenAn[2].getAn() == 2004);
	assert(sortedByGenAn[3].getGen() == "gen4");
	assert(sortedByGenAn[3].getAn() == 2004);
}

void testCos() {
	BookRepository testRepo;
	BookValidator testVal;
	Cos cos;
	BookStore testService{ testRepo, testVal, cos };

	testService.addBook("carte1", "autor1", "sf", 2019);
	testService.addBook("carte2", "autor2", "aventura", 2016);
	testService.addBook("carte3", "autor2", "sf", 2012);
	testService.addBook("carte4", "autor1", "sf", 2011);
	testService.addBook("carte5", "autor3", "aventura", 2016);
	testService.addBook("carte6", "autor3", "sf", 2012);

	testService.addRandomToCos(4);
	assert(testService.getAllCos().size() == 4);
	testService.emptyCos();
	assert(testService.getAllCos().size() == 0);

	testService.addRandomToCos(20);
	//putem adauga doar 6 (fara sa se repete)
	assert(testService.getAllCos().size() == 6);

	testService.emptyCos();
	testService.addCos("carte6");
	assert(testService.getAllCos().size() == 1);

	/*try {
		testService.addCos("Carte10");
		assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}*/


}

void testService() {
	testAddService();
	testDeleteService();
	testModifyService();
	testFindService();
	testFilterService();
	testSortService();
	testCos();
}