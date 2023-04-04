#pragma once
#include "Repository.h"
#include "Validators.h"
#include "Cos.h"
#include "Undo.h"
#include <functional>
#include <vector>
using std::function;
using std::vector;

class SrvException {
private:
	std::string errors;
public:
	SrvException(std::string) :errors{ errors } {};
	std::string get_errors() {
		return this->errors;
	}
};

class BookStore {
private:
	BookRepository& repo;
	BookValidator& val;

	Cos currentCos;

	vector<unique_ptr<CarteUndo>> undoBooks;

	///vector<Carte> filter(function<bool(const Carte&)> fct);

	///vector<Carte> sortGenerala(bool(*maiMicF)(const Carte&, const Carte&));

public:
	BookStore(BookRepository& bookRepo, BookValidator& val, Cos& cos) :repo{ bookRepo }, val{ val }, currentCos{ cos }{};

	BookStore(const BookStore& ot) = delete;

	void addBook(std::string titlu, std::string autor, std::string gen, int an);


	void deleteBook(std::string titlu);

	void modifyBook(std::string titlu, std::string autor, std::string gen, int an);

	Carte findBook(std::string titlu);

	vector<Carte> filtrareTitlu(std::string gen);

	vector<Carte> filtrareAn(int an);

	vector<Carte> sortareTitlu();

	vector<Carte> sortareAutor();

	vector<Carte> sortareGenAn();

	const vector<Carte>& getAllBooks() {
		return this->repo.getAllBooks();
	}

	void addCos(string titlu);

	size_t addRandomToCos(int howMany);

	void emptyCos();

	const vector<Carte>& getAllCos();

	void undo();
	

	void saveToFile(std::string filename);
};
void testService();