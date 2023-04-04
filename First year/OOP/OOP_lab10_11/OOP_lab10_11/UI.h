#pragma once
#include "Service.h"
class ConsoleUI {
private:
	BookStore& srv;
public:
	ConsoleUI(BookStore& srv) :srv{ srv } {};
	ConsoleUI(const ConsoleUI& ot) = delete;
	void printMenu();
	void uiAdd();
	void printAllBooks(const vector<Carte>& carti);
	void uiDelete();
	void uiModify();
	void uiFind();
	void uiFilter();
	void uiSort();
	void printMenuCos();
	void uiExport();
	void uiUndo();
	void uiCos();
	void uiAddCos();
	void uiAddRandomCos();
	void uiEmptyCos();
	void run();
};
