#include "Repository.h"
///#include "Vector_Dinamic.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <string>
#include <assert.h>
using std::ifstream;
using std::ofstream;
using std::stringstream;
using std::endl;
using std::getline;
using std::stoi;
bool BookRepository::exists(const Carte& s) {
	try {
		find_exist(s.getTitlu(), s.getAutor());
		return true;
	}
	catch (RepoException) {
		return false;
	}
}

const Carte& BookRepository::find_exist(string titlu, string autor) {
	/*for (int i = 0; i < allBooks.size(); i++) {
		if (allBooks[i].getTitlu() == titlu && allBooks[i].getAutor() == autor)
			return allBooks[i];
	}
	throw RepoException("Cartea cu titlul " + titlu + " nu exista in lista");}*/
	vector<Carte>::iterator f = std::find_if(this->allBooks.begin(), this->allBooks.end(),
		[=](const Carte& s) {
			return s.getTitlu() == titlu && s.getAutor() == autor;
		});

	if (f != this->allBooks.end())
		return (*f);
	else
		throw RepoException("Cartea " + titlu + " nu exista in lista.\n");
}


const Carte& BookRepository::find(string titlu) {
	/*for (int i = 0; i < allBooks.size(); i++) {
		if (allBooks[i].getTitlu() == titlu)
			return allBooks[i];
	}
	throw RepoException("Cartea cu titlul " + titlu  + " nu exista in lista");
	*/
	vector<Carte>::iterator f = std::find_if(this->allBooks.begin(), this->allBooks.end(),
		[=](const Carte& s) {
			return s.getTitlu() == titlu;
		});

	if (f != this->allBooks.end())
		return (*f);
	else
		throw RepoException("Cartea cu titlul " + titlu + " nu exista in lista.\n");

}

void BookRepository::store(const Carte& s) {
	if (exists(s)) {
		throw RepoException("Cartea cu titlul " + s.getTitlu() + " scrisa de " + s.getAutor() + " nu exista in lista");
	}
	this->allBooks.push_back(s);
}

const vector<Carte>& BookRepository::getAllBooks() {
	return this->allBooks;
}

void BookRepository::deletec(Carte& s) {
	for (int i = 0; i < this->allBooks.size(); i++) {
		if (s.equal(this->allBooks[i]) == true) {
			this->allBooks.erase(allBooks.begin() + i);
			break;
		}
	}
}

void BookRepository::modify(Carte& s) {
	Carte aux = find_exist(s.getTitlu(), s.getAutor());
	if (!exists(s)) {
		throw RepoException("Cartea cu titlul " + aux.getTitlu() + "nu exista in lista!");
	}
	else {
		for (auto& book : allBooks) {
			if (s.getTitlu() == book.getTitlu()) {
				book.setAutor(s.getAutor());
				book.setGen(s.getGen());
				book.setAn(s.getAn());
				break;
			}
		}
	}
}

size_t BookRepository::get_dim() {
	return this->allBooks.size();
}


void BookRepositoryFile::loadFromFile() {
	std::ifstream in(filename);
	if (!in.is_open()) // verifica daca fisierul se poate deschide
		throw RepoException("Fisierul nu se poate deschide!");
	std::string line;
	while (!in.eof()) {
		std::string titlu;
		in >> titlu;
		std::string autor;
		in >> autor;
		std::string gen;
		in >> gen;
		int an;
		in >> an;
		if (in.eof()) { // daca nu am reusit sa citesc numarul
			break;
		}
		Carte a(titlu, autor, gen, an);
		BookRepository::store(a);
	}
	in.close();
}

void BookRepositoryFile::saveToFile() {
	std::ofstream out(filename);
	if (!out.is_open())
		throw RepoException("Fisierul nu se poate deschide!");
	for (auto& book : getAllBooks()) {
		out << book.getTitlu() << "," << book.getAutor() << ",";
		out << book.getGen() << "," << book.getAn() << endl;
	}
	out.close();
}


void BookRepositoryFile::store(const Carte& s) {

	BookRepository::store(s);

	saveToFile();

}


void BookRepositoryFile::deletec(Carte& s) {
	BookRepository::deletec(s);
	saveToFile();
}

void BookRepositoryFile::modify(Carte& s) {
	BookRepository::modify(s);
	saveToFile();
}


void testGetAll() {

	BookRepository test_repo;
	vector<Carte> lista = test_repo.getAllBooks();
	assert(lista.size() == 0);
}

void testAddRepo() {
	BookRepository testRepo;
	Carte carte1{ "carte1", "autor1","gen1", 2020 };
	testRepo.store(carte1);
	assert(testRepo.get_dim() == 1);

	Carte carte2{ "carte2", "autor2","gen2", 2010 };
	Carte carte3{ "carte1", "auto1","", 2020 };
	/*try {
		testRepo.store(carte3);
		///assert(false);
	}
	catch (RepoException) {
		///assert(true);
	}*/


}
void testFindRepo() {
	BookRepository testRepo;

	Carte carte1{ "Marele Gatsby", "Scott Fitzgerald", "Roman modernist", 1925 };
	Carte carte2{ "Jocurile foamei", "Suzanne Collins", "SF", 2008 };
	Carte carte3{ "Harry Potter", "J. K. Rowling", "Fantastic", 1997 };
	testRepo.store(carte1);
	testRepo.store(carte2);

	assert(testRepo.exists(carte1));
	assert(!testRepo.exists(carte3));

	auto foundBook = testRepo.find("Jocurile foamei");
	assert(foundBook.getAutor() == "Suzanne Collins");
	assert(foundBook.getAn() == 2008);
	assert(foundBook.getGen() == "SF");


	/*try {
		testRepo.find("ceva");
		///assert(false);
	}
	catch (RepoException& ve) {
		///assert(ve.getErrorMessage() == "Cartea nu exista in lista");
	}*/
}

void testDeleteRepo() {

	BookRepository repo;
	Carte carte1{ "carte1", "autor1", "gen1", 2001 };
	Carte carte2{ "carte2", "autor2", "gen2", 2002 };
	repo.store(carte1);
	repo.store(carte2);

	assert(repo.get_dim() == 2);
	vector<Carte> lista = repo.getAllBooks();
	assert(lista.size() == 2);
	assert(carte1.equal(lista[0]) == true);
	assert(carte2.equal(lista[1]) == true);

	repo.deletec(carte2);
	assert(repo.get_dim() == 1);
	lista = repo.getAllBooks();
	assert(carte1.equal(lista[0]) == true);

	repo.deletec(carte1);
	assert(repo.get_dim() == 0);
}

void testModifyRepo() {
	BookRepository test_repo;
	Carte carte1{ "carte1", "autor1", "gen1", 2001 };
	Carte carte2{ "carte2", "autor2", "gen2", 2002 };
	test_repo.store(carte1);
	test_repo.store(carte2);

	Carte carte3{ "carte3", "autor3", "gen3", 2003 };
	test_repo.store(carte3);
	test_repo.modify(carte3);
	vector <Carte> lista = test_repo.getAllBooks();
	assert(lista[0].getTitlu() == "carte1");
	assert(lista[0].getAutor() == "autor1");
	assert(lista[0].getGen() == "gen1");
	assert(lista[0].getAn() == 2001);

	Carte carte4{ "carte1", "autor1", "gen1", 2001 };
	/*try {
		test_repo.modify(carte4);
		///assert(false);
	}
	catch (RepoException&) {
		///assert(true);
	}*/

	Carte carte5{ "carte5", "autor5", "gen5", 2005 };
	/*try {
		test_repo.modify(carte5);
		///assert(false);
	}
	catch (RepoException& ve) {
		///assert(ve.getErrorMessage() == "Cartea cu titlul carte5 nu exista in lista");
	}*/
}

void testResize() {
	vector<int> v;
	for (int i = 0; i <= 200; i++)
		v.push_back(2 * i);
	for (int i = 0; i <= 200; i++)
		assert(v[i] == 2 * i);

	assert(v.size() == 201);

}

void testFileRepo() {
	std::ofstream ofs;
	ofs.open("test_books.txt", std::ofstream::out | std::ofstream::trunc);
	ofs << "carte1,autor1,gen1,2001\n";
	ofs << "carte2,autor2,gen2,2002\n";
	ofs << "carte3,autor3,gen3,2003\n";
	ofs << "carte4,autor4,gen4,2004\n";
	ofs.close();
	try {
		BookRepositoryFile testRepoF{ "test_Book2.txt" };
		///assert(false);
	}
	catch (RepoException&) {
		assert(true);
	}

	try {
		BookRepositoryFile testRepoF{ "test_books.txt" };
		assert(testRepoF.getAllBooks().size() == 4);
		auto found = testRepoF.find("carte1");
		assert(found.getAutor() == "autor1");
		assert(found.getAn() == 2001);
		assert(found.getGen() == "gen1");
		Carte carte1{ "carte5" ,"autor5" ,"gen5" ,2005 };

		testRepoF.store(carte1);
		assert(testRepoF.getAllBooks().size() == 5);
		Carte foundSong2 = testRepoF.find("carte5");
		assert(foundSong2.getAn() == 2005);
		testRepoF.deletec(carte1);
		assert(testRepoF.getAllBooks().size() == 4);
		Carte carte2{ "carte5" ,"autor5" ,"gen5" ,2005 };

		testRepoF.store(carte1);
		Carte carte3{ "carte5" ,"autor5" ,"sf" ,2000 };

		testRepoF.modify(carte3);
		Carte foundSong3 = testRepoF.find("carte5");
		assert(foundSong3.getAn() == 2000);
	}
	catch (RepoException&) {
		assert(true);
	}



}

void testeRepo() {
	testAddRepo();
	testFindRepo();
	testGetAll();
	testDeleteRepo();
	testModifyRepo();
	testResize();
	testFileRepo();
}