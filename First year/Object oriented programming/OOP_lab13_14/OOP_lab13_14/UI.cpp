#pragma once
#include "UI.h"
///#include "Vector_Dinamic.h"
#include <iostream>
using namespace std;

void ConsoleUI::printMenu() {
	cout << "Comenzi disponibile:" << endl;
	cout << "1. Adauga carte" << endl;
	cout << "2. Modifica carte" << endl;
	cout << "3. Sterge carte" << endl;
	cout << "4. Cauta carte" << endl;
	cout << "5. Filtrare carti" << endl;
	cout << "6. Sortare carti" << endl;
	cout << "7. Afisare carti" << endl;
	cout << "8. Undo" << endl;
	cout << "9. Cos Meniu" << endl;
	cout << "10. Exit" << endl;
}

void ConsoleUI::uiAdd() {
	string titlu, autor, gen;
	int an;
	cout << "Titlul cartii este:";
	getline(cin >> ws, titlu);

	cout << "Autorul cartii este:";
	getline(cin >> ws, autor);
	cout << "Genul cartii este:";
	getline(cin >> ws, gen);

	cout << "Anul aparitiei este:";
	cin >> an;
	try {
		srv.addBook(titlu, autor, gen, an);
	}
	catch (RepoException& re) {
		cout << re.getErrorMessage();
	}
	catch (ValidationException& ve) {
		cout << "Cartea nu este valida!" << endl;
		cout << ve.getErrorMessages();
	}

}

void ConsoleUI::uiDelete() {
	string titlu;
	cout << "Introduceti titlul cartii:";
	getline(cin >> ws, titlu);
	try {
		srv.deleteBook(titlu);
		cout << "Cartea a fost stearsa!" << endl;
	}
	catch (RepoException& re) {
		cout << "Cartea cu acest titlu nu se gaseste in lista!" << endl;
		cout << re.getErrorMessage();
	}
}

void ConsoleUI::uiModify() {
	string titlu, autor, gen;
	int an;

	cout << "Introduceti titlul: ";
	getline(cin >> ws, titlu);
	cout << "Introduceti autorul: ";
	getline(cin >> ws, autor);
	cout << "Introduceti genul: ";
	getline(cin >> ws, gen);
	cout << "Introduceti anul: ";
	cin >> an;

	try {
		srv.modifyBook(titlu, autor, gen, an);
		cout << "Cartea a fost modificata!" << endl;
	}
	catch (RepoException& re) {
		cout << "Cartea cu acest titlu nu se gaseste in lista!" << endl;
		cout << re.getErrorMessage();
	}
}

void ConsoleUI::uiFind() {
	string titlu;
	cout << "Introduceti titlul: ";
	getline(cin >> ws, titlu);
	try {
		Carte carte = srv.findBook(titlu);
		cout << "Cartea cautata este: ";
		cout << "Titlu: " << carte.getTitlu() << " | Autor: " << carte.getAutor() << " | Gen: " << carte.getGen() << " | An: " << carte.getAn() << endl;
	}
	catch (RepoException& re) {
		cout << "Activitatea cu acest titlu nu se gaseste in lista!" << endl;
		cout << re.getErrorMessage();
	}
}

void ConsoleUI::uiFilter() {
	cout << "Introduceti criteriu dupa care doriti sa filtrati(titlu/an):";
	string criteriu;
	cin >> criteriu;

	if (criteriu == "titlu") {
		cout << "Titlul pentru care sa se afiseze cartile: ";
		string titlu;
		cin >> titlu;
		if (srv.filtrareTitlu(titlu).size() > 0)
			printAllBooks(srv.filtrareTitlu(titlu));
		else cout << "Nu se gasesc carti cu acest titlu!" << endl;
	}
	else if (criteriu == "an") {
		cout << "Anul pentru care sa se afiseze cartile: ";
		int an;
		cin >> an;
		if (srv.filtrareAn(an).size() > 0)
			printAllBooks(srv.filtrareAn(an));
		else cout << "Nu se gasesc carti din acest an!" << endl;
	}
	else cout << "Nu exista acest criteriu." << endl;
}

void ConsoleUI::uiSort() {
	cout << "Introduceti criteriu dupa care doriti sa sortati(titlu/autor/gen+an):";
	string criteriu;
	cin >> criteriu;

	if (criteriu == "titlu")
		printAllBooks(srv.sortareTitlu());
	else if (criteriu == "autor")
		printAllBooks(srv.sortareAutor());
	else if (criteriu == "gen+an")
		printAllBooks(srv.sortareGenAn());
	else cout << "Nu se poate sorta dupa acest criteriu." << endl;
}

void ConsoleUI::printAllBooks(const vector<Carte>& allBooks) {
	///const vector<Carte>& allBooks = srv.getAllBooks();
	if (allBooks.size() == 0)
		cout << "Nu exista carti." << endl;
	else
		for (const auto& book : allBooks) {
			cout << "Titlu: " << book.getTitlu() << " | Autor: " << book.getAutor() << " | Gen: " << book.getGen() << " | An: " << book.getAn() << endl;
		}
}

void ConsoleUI::printMenuCos() {
	cout << "MENIU COS" << endl;
	cout << "0. Export" << endl;
	cout << "1. Adauga carte in cos" << endl;
	cout << "2. Adauga carti random in cos" << endl;
	cout << "3. Goleste cosul" << endl;
	cout << "4. Afiseaza cosul curent" << endl;
	cout << "5. Inapoi la meniul principal" << endl;
}

void ConsoleUI::uiExport() {
	cout << "Introduceti numele fisierului:";
	string fileName;
	cin >> fileName;
	BookRepositoryFile repo{ fileName };


	for (auto& book : repo.getAllBooks())
		repo.store(book);
}

void ConsoleUI::uiAddCos() {
	string titlu;
	cout << "Titlul cartii este:";
	getline(cin >> ws, titlu);

	try {
		this->srv.addCos(titlu);
		cout << "Carte s-a adaugat cu succes la cos." << endl;
	}
	catch (RepoException& re) {
		cout << re.getErrorMessage();
	}
	///catch (ValidationException& ve) {
		///cout << "Cartea nu este valida!" << endl;
		///cout << ve.getErrorMessages();
	///}
}
void ConsoleUI::uiAddRandomCos() {
	int howManyToAdd;
	cout << "Cate carti sa se adauge in cos?";
	cin >> howManyToAdd;


	try {
		size_t howManyAdded = this->srv.addRandomToCos(howManyToAdd);
		cout << "S-au adaugat " << howManyAdded << " carti in cos." << endl;
	}
	catch (RepoException& re) {
		cout << re.getErrorMessage();
	}
}


void ConsoleUI::uiEmptyCos() {
	this->srv.emptyCos();
	cout << "S-au sters toate cartile din cosul curent." << endl;
}

void ConsoleUI::uiUndo() {
	try {
		srv.undo();
		cout << "Undo realizat cu succes!" << endl;
	}
	catch (SrvException) {
		cout << "Nu se mai poate face undo!" << endl;
	}
}



void ConsoleUI::uiCos() {
	int cmd;
	int runningCos = 1;
	while (runningCos) {
		printMenuCos();
		cout << "Comanda este:";
		cin >> cmd;
		switch (cmd)
		{
		case 0:
			uiExport();
			break;
		case 1:
			uiAddCos();
			break;
		case 2:
			uiAddRandomCos();
			break;
		case 3:
			uiEmptyCos();
			break;

		case 4:
			printAllBooks(srv.getAllCos());
			break;
		case 5:
			runningCos = 0;
			break;
		default:
			break;
		}

	}
}


void ConsoleUI::run() {
	int running = 1;
	int cmd;
	while (running) {
		printMenu();
		cout << "Comanda este:";
		cin >> cmd;
		switch (cmd)
		{
		case 1:
			uiAdd();
			break;
		case 2:
			uiModify();
			break;
		case 3:
			uiDelete();
			break;
		case 4:
			uiFind();
			break;
		case 5:
			uiFilter();
			break;
		case 6:
			uiSort();
			break;
		case 7:
			printAllBooks(srv.getAllBooks());
			break;
		case 8:
			uiUndo();
			break;
		case 9:
			uiCos();
			break;
		case 10:
			running = 0;
			break;
		default:
			break;
		}

	}
}