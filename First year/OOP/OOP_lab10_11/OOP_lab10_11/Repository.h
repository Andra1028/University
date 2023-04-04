#pragma once
#include "Carte.h"
///#include "Vector_Dinamic.h"
#include <vector>
using std::vector;
using std::string;
using std::unique_ptr;
/*
Clasa de exceptii specifice Repo
*/
class RepoException {
private:
	string errorMsg;
public:
	RepoException(string errorMsg) :errorMsg{ errorMsg } {};
	string getErrorMessage() {
		return this->errorMsg;
	}
};

class BookRepository {
private:
	vector<Carte> allBooks;
public:

	BookRepository() {
		vector<Carte> lst;
		allBooks = lst;
	}
	BookRepository(const BookRepository& ot) = delete;
	virtual void store(const Carte& s);
	virtual void deletec(Carte& s);
	virtual const vector<Carte>& getAllBooks();
	virtual void modify(Carte& s);
	const Carte& find(string titlu);
	const Carte& find_exist(string titlu, string autor);
	size_t get_dim();
	bool exists(const Carte& s);

};

class BookRepositoryFile : public BookRepository {
private:
	std::string filename;
	/*
	Incarca datele din fisier
	*/
	void loadFromFile();
	/*
	* Salveaza datele din fisier
	* Format: titlu,artist,gen,an\n
	*/
	void saveToFile();
public:
	BookRepositoryFile(string fname) :BookRepository(), filename{ fname } {
		loadFromFile();
	};
	void store(const Carte& s) override;
	void deletec(Carte& s) override;
	void modify(Carte& s) override;
};

void testeRepo();
void testResize();