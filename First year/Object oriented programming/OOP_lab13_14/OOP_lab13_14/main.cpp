#include "OOP_lab13_14.h"
#include "UI.h"
#include "GUI.h"
#include <iostream>
#include <filesystem>

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include <QtWidgets/QApplication>

using std::cout;
using std::endl;





void test_all() {
	testeDomain();
	///cout << "Au trecut toate testele din domain." << endl;
	testeRepo();
	///cout << "Au trecut toate testele din repo." << endl;
	testService();
	///cout << "Au trecut toate testele din service." << endl;
	teste_cos();
	///cout << "Au trecut toate testele din cos." << endl;
}


int main(int argc, char* argv[])
{
	QApplication a(argc, argv);
	BookRepositoryFile repo{ "books.txt" };
	//ActivitateRepository repo;
	BookValidator val;
	Cos cos;
	BookStore srv{ repo, val,cos };
	ConsoleGUI gui{ srv };
	gui.show();

	ConsoleGUIProgram guiProgram{ srv };
	guiProgram.show();

	return a.exec();
} 
