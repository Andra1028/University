#include "GUI.h"

void ConsoleGUI::initializeGUIComponents() {
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);


	//COMPONENTA LEFT
	QWidget* left = new QWidget;
	QVBoxLayout* lyLeft = new QVBoxLayout;
	left->setLayout(lyLeft);

	//adaugare
	QWidget* form = new QWidget;
	QFormLayout* lyForm = new QFormLayout;
	form->setLayout(lyForm);
	this->editTitlu = new QLineEdit;
	this->editAutor = new QLineEdit;
	this->editGen = new QLineEdit;
	this->editAn = new QLineEdit;

	lyForm->addRow(lblTitlu, editTitlu);
	lyForm->addRow(lblAutor, editAutor);
	lyForm->addRow(lblGen, editGen);
	lyForm->addRow(lblAn, editAn);
	btnAddBook = new QPushButton("Adauga carte");

	lyLeft->addWidget(form);
	lyLeft->addWidget(btnAddBook);

	//stergere
	btnDellBook = new QPushButton("Sterge carte");
	lyLeft->addWidget(btnDellBook);

	//modificare
	btnModifyBook = new QPushButton("Modifica carte");
	lyLeft->addWidget(btnModifyBook);

	//cautare
	btnFindBook = new QPushButton("Cauta carte");
	lyLeft->addWidget(btnFindBook);

	//undo
	btnUndo = new QPushButton("Undo");
	lyLeft->addWidget(btnUndo);

	//filtrare
	QWidget* formFilter = new QWidget;
	QFormLayout* lyFormFilter = new QFormLayout;
	formFilter->setLayout(lyFormFilter);
	editFilterTitlu = new QLineEdit;
	lyFormFilter->addRow(lblFilterTitlu, editFilterTitlu);
	btnFilterTitlu = new QPushButton("Filtreaza dupa titlu");
	lyFormFilter->addWidget(btnFilterTitlu);

	editFilterAn = new QLineEdit;
	lyFormFilter->addRow(lblFilterAn, editFilterAn);
	btnFilterAn = new QPushButton("Filtreaza dupa an");
	lyFormFilter->addWidget(btnFilterAn);

	lyLeft->addWidget(formFilter);


	//sortare
	QVBoxLayout* lyRadioBox = new QVBoxLayout;
	this->groupBoxSort->setLayout(lyRadioBox);
	lyRadioBox->addWidget(radioSortTitlu);
	lyRadioBox->addWidget(radioSortAutor);
	lyRadioBox->addWidget(radioSortGenAn);

	btnSortBooks = new QPushButton("Sorteaza carti");
	lyRadioBox->addWidget(btnSortBooks);
	lyRadioBox->addWidget(groupBoxSort);

	lyLeft->addWidget(groupBoxSort); //adaugam in partea stanga.

	//reload data
	btnReloadData = new QPushButton("Reload data");
	lyLeft->addWidget(btnReloadData);


	//COMPONENTA RIGHT
	QWidget* right = new QWidget;
	QVBoxLayout* lyRight = new QVBoxLayout;
	right->setLayout(lyRight);

	int noLines = 10;
	int noColums = 4;
	this->tableBooks = new QTableWidget(noLines, noColums);

	//setez header-ul
	QStringList tblHeaderList;
	tblHeaderList << "Titlu" << "Autor" << "Gen" << "An";
	this->tableBooks->setHorizontalHeaderLabels(tblHeaderList);

	//obtiune pentru a redimensiona celulele din tabel in functie de continut
	this->tableBooks->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

	lyRight->addWidget(tableBooks);


	lyMain->addWidget(left);
	lyMain->addWidget(right);

	//tip
	QWidget* btn_widget = new QWidget;
	btn_layout = new QVBoxLayout;
	btn_widget->setLayout(btn_layout);
	lyMain->addWidget(btn_widget);
	gui_addGen();

}

void ConsoleGUI::reloadBookList(vector<Carte> carti) {
	this->tableBooks->clearContents();
	this->tableBooks->setRowCount(carti.size());

	int lineNumber = 0;
	for (auto& activity : carti) {
		this->tableBooks->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.getTitlu())));
		this->tableBooks->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.getAutor())));
		this->tableBooks->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.getGen())));
		this->tableBooks->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.getAn())));
		lineNumber++;
	}
}

void ConsoleGUI::connectSignalsSlots() {
	QObject::connect(btnAddBook, &QPushButton::clicked, this, &ConsoleGUI::gui_AddBook);

	QObject::connect(btnDellBook, &QPushButton::clicked, this, &ConsoleGUI::gui_DellBook);

	QObject::connect(btnModifyBook, &QPushButton::clicked, this, &ConsoleGUI::gui_ModifyBook);

	QObject::connect(btnUndo, &QPushButton::clicked, this, &ConsoleGUI::gui_undo);

	QObject::connect(btnFindBook, &QPushButton::clicked, [&]() {
		string find = this->editTitlu->text().toStdString();
		Carte carte = srv.findBook(find);
		vector<Carte> foundBook;
		foundBook.push_back(carte);
		reloadBookList(foundBook);
		editTitlu->clear();
		});

	QObject::connect(btnFilterTitlu, &QPushButton::clicked, [&]() {
		string filterC = this->editFilterTitlu->text().toStdString();
		reloadBookList(srv.filtrareTitlu(filterC));
		editFilterTitlu->clear();
		});

	QObject::connect(btnFilterAn, &QPushButton::clicked, [&]() {
		int filterC = this->editFilterAn->text().toInt();
		reloadBookList(srv.filtrareAn(filterC));
		editFilterAn->clear();
		});

	QObject::connect(btnSortBooks, &QPushButton::clicked, [&]() {
		if (radioSortTitlu->isChecked())
			reloadBookList(srv.sortareTitlu());
		else if (radioSortAutor->isChecked())
			reloadBookList(srv.sortareAutor());
		else if (radioSortGenAn->isChecked())
			reloadBookList(srv.sortareGenAn());
		});

	QObject::connect(btnReloadData, &QPushButton::clicked, [&]() {
		reloadBookList(srv.getAllBooks());
		});

}

void ConsoleGUI::gui_AddBook() {
	try {
		string titlu = editTitlu->text().toStdString();
		string descriere = editAutor->text().toStdString();
		string tip = editGen->text().toStdString();
		double durata = editAn->text().toInt();

		editTitlu->clear();
		editAutor->clear();
		editGen->clear();
		editAn->clear();

		srv.addBook(titlu, descriere, tip, durata);
		reloadBookList(this->srv.getAllBooks());

		QMessageBox::information(this, "Info", QString::fromStdString("Carte adaugata cu succes!"));
		gui_addGen();
	}
	catch (RepoException& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
	}
	catch (ValidationException& ve) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Cartea este invalida!"));
		QMessageBox::warning(this, "Warning", QString::fromStdString(ve.getErrorMessages()));
	}
}

void ConsoleGUI::gui_DellBook() {
	try {
		//QMessageBox::information(this, "Info", QString::fromStdString("Introduceti titul activitatii pe care doriti sa o stergeti!"));
		string titlu = editTitlu->text().toStdString();

		editTitlu->clear();

		srv.deleteBook(titlu);
		reloadBookList(srv.getAllBooks());

		QMessageBox::information(this, "Info", QString::fromStdString("Cartea stearsa cu succes!"));
		gui_addGen();
	}
	catch (RepoException& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Activitatea cu acest titlu nu se gaseste in lista!"));
		QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
	}

}

void ConsoleGUI::gui_ModifyBook() {
	try {
		string titlu = editTitlu->text().toStdString();
		string descriere = editAutor->text().toStdString();
		string tip = editGen->text().toStdString();
		double durata = editAn->text().toInt();

		editTitlu->clear();
		editAutor->clear();
		editGen->clear();
		editAn->clear();

		srv.modifyBook(titlu, descriere, tip, durata);
		reloadBookList(srv.getAllBooks());

		QMessageBox::information(this, "Info", QString::fromStdString("Cartea modificata cu succes!"));
		gui_addGen();
	}
	catch (RepoException& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Cartea cu acest titlu nu se gaseste in lista!"));
		QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
	}
}

void ConsoleGUI::gui_undo() {
	try {
		srv.undo();
		reloadBookList(srv.getAllBooks());
		QMessageBox::information(this, "Info", QString::fromStdString("Undo realizat cu succes!"));
		gui_addGen();
	}
	catch (std::exception()) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Nu se mai poate face undo!"));
	}
}

void ConsoleGUI::gui_addGen() {
	vector<Carte> carti = srv.getAllBooks();
	vector<std::pair<string, int>> tipuri;
	for (const auto& a : carti) {
		bool ok = false;
		int i = 0;
		for (const auto& t : tipuri) {
			if (t.first == a.getGen()) {
				ok = true;
				break;
			}
		}
		if (ok == true)
			tipuri[i].second++;
		else tipuri.emplace_back(a.getGen(), 1);
	}
	QLayoutItem* item;
	while ((item = btn_layout->takeAt(0)) != NULL)
	{
		delete item->widget();
		delete item;
	}


	for (const auto& t : tipuri) {
		const string& tip = t.first;
		const int& nr = t.second;
		auto item = new QPushButton(QString::fromStdString(tip));

		QObject::connect(item, &QPushButton::clicked, [nr] {
			string n = std::to_string(nr);
			auto* lbl = new QLabel(QString::fromStdString(n));
			lbl->show();
			//QMessageBox::information(this, "Info", QString::fromStdString(n));
			});
		btn_layout->addWidget(item);
	}
}


//PROGRAM*********************************************************************************

void ConsoleGUIProgram::initializeGUIProgComponents() {
	QHBoxLayout* lyMain = new QHBoxLayout;
	this->setLayout(lyMain);


	//COMPONENTA LEFT
	QWidget* left = new QWidget;
	QVBoxLayout* lyLeft = new QVBoxLayout;
	left->setLayout(lyLeft);

	//adaugare
	QWidget* form = new QWidget;
	QFormLayout* lyForm = new QFormLayout;
	form->setLayout(lyForm);
	this->editTitluC = new QLineEdit;

	lyForm->addRow(lblTitluC, editTitluC);
	btnAddCos = new QPushButton("Adauga carte in cos");
	lyForm->addWidget(btnAddCos);

	lyLeft->addWidget(form);

	//adaugare random
	QWidget* formAddR = new QWidget;
	QFormLayout* lyFormAddR = new QFormLayout;
	formAddR->setLayout(lyFormAddR);
	this->editNr = new QLineEdit;

	lyFormAddR->addRow(lblNr, editNr);
	btnAddRandom = new QPushButton("Adauga carti random in program");
	lyFormAddR->addWidget(btnAddRandom);

	lyLeft->addWidget(formAddR);

	//export
	QWidget* formEx = new QWidget;
	QFormLayout* lyFormEx = new QFormLayout;
	formEx->setLayout(lyFormEx);
	this->editFile = new QLineEdit;

	lyFormEx->addRow(lblFile, editFile);
	btnExport = new QPushButton("Export");
	lyFormEx->addWidget(btnExport);

	lyLeft->addWidget(formEx);

	//goleste program
	btnGoleste = new QPushButton("Goleste lista carti!");
	lyLeft->addWidget(btnGoleste);

	btnLabelWindow = new QPushButton{ "Fereastra label" };
	btnLabelWindow->setStyleSheet("background-color: cyan");

	btnTableWindow = new QPushButton{ "Fereastra tabel cos" };
	btnTableWindow->setStyleSheet("background-color: magenta");

	btnDrawWindow = new QPushButton{ "Fereastra desen" };
	btnDrawWindow->setStyleSheet("background-color: yellow");

	lyLeft->addWidget(btnLabelWindow);
	lyLeft->addWidget(btnTableWindow);
	lyLeft->addWidget(btnDrawWindow);

	//COMPONENTA RIGHT
	QWidget* right = new QWidget;
	QVBoxLayout* lyRight = new QVBoxLayout;
	right->setLayout(lyRight);

	widgetDynamic = new QWidget{};
	lyBtnDynamic = new QVBoxLayout{};
	widgetDynamic->setLayout(lyBtnDynamic);
	reloadDynamicButtons();

	int noLines = 10;
	int noColums = 4;
	this->tableBooks = new QTableWidget(noLines, noColums);

	//setez header-ul
	QStringList tblHeaderList;
	tblHeaderList << "Titlu" << "Autor" << "Gen" << "An";
	this->tableBooks->setHorizontalHeaderLabels(tblHeaderList);

	//obtiune pentru a redimensiona celulele din tabel in functie de continut
	this->tableBooks->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

	lyRight->addWidget(tableBooks);

	lyMain->addWidget(left);
	lyMain->addWidget(right);
	lyMain->addWidget(widgetDynamic);
}

void ConsoleGUIProgram::reloadBookListFromCos(vector<Carte> carti) {
	this->tableBooks->clearContents();
	this->tableBooks->setRowCount(carti.size());

	int lineNumber = 0;
	for (auto& activity : carti) {
		this->tableBooks->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.getTitlu())));
		this->tableBooks->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.getAutor())));
		this->tableBooks->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.getGen())));
		this->tableBooks->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.getAn())));
		lineNumber++;
	}
}

set<string> ConsoleGUIProgram::getGenres(const vector<Carte>& carti) {
	set<string> genres;

	for (const auto& s : carti) {
		genres.insert(s.getGen());
	}
	return genres;
}

int howManyWithGenre(const vector<Carte>& books, string gen) {
	int noSongs = count_if(books.begin(), books.end(), [&](Carte s) {
		return s.getGen() == gen; });
	return noSongs;
}
void clearLayout(QLayout* layout) {
	if (layout == NULL)
		return;
	QLayoutItem* item;
	while ((item = layout->takeAt(0))) {
		if (item->layout()) {
			clearLayout(item->layout());
			delete item->layout();
		}
		if (item->widget()) {
			delete item->widget();
		}
		delete item;
	}
}

void ConsoleGUIProgram::reloadDynamicButtons() {
	clearLayout(this->lyBtnDynamic);
	const vector<Carte>& Cosbooks = this->srv.getAllCos();
	set<string> genres = this->getGenres(Cosbooks);

	for (string genre : genres) {
		QPushButton* btn = new QPushButton{ QString::fromStdString(genre) };
		lyBtnDynamic->addWidget(btn);
		int howMany = howManyWithGenre(Cosbooks, genre);
		QObject::connect(btn, &QPushButton::clicked, [=]() {
			QMessageBox::information(this, "Info", QString::fromStdString("Carti cu genul " + genre + " : " + to_string(howMany)));

			});
	}

}

void ConsoleGUIProgram::connectSignalsSlotsProg() {
	QObject::connect(btnAddCos, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_AddCos);
	QObject::connect(btnAddRandom, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_AddCosRandom);

	QObject::connect(btnGoleste, &QPushButton::clicked, [&]() {
		srv.emptyCos();
		QMessageBox::information(this, "Info", QString::fromStdString("Lista de carti din cos a fost golita!"));
		reloadBookListFromCos(srv.getAllCos());
		});

	QObject::connect(btnExport, &QPushButton::clicked, this, &ConsoleGUIProgram::gui_Export);

	QObject::connect(btnLabelWindow, &QPushButton::clicked, [&]() {
		auto songcounter = new BookCounter(srv.getCos());
		songcounter->show();
		});

	QObject::connect(btnTableWindow, &QPushButton::clicked, this, [&]() {
		auto tableWindow = new CosGUITable(srv.getCos());
		tableWindow->show();
		});

	QObject::connect(btnDrawWindow, &QPushButton::clicked, [&]() {
		auto bookdraw = new BookDraw(srv.getCos());
		bookdraw->show();
		});

	QObject::connect(bookList, &QListWidget::itemSelectionChanged, [&]() {
		auto selItms = bookList->selectedItems();
		for (auto item : selItms) {
			qDebug() << item->text();
			item->setBackground(Qt::green); // sets green background
		}
		});

}

void ConsoleGUIProgram::gui_AddCos() {
	try {
		string titlu = editTitluC->text().toStdString();

		editTitluC->clear();

		srv.addCos(titlu);
		reloadBookListFromCos(this->srv.getAllCos());

		QMessageBox::information(this, "Info", QString::fromStdString("Carte adaugata cu succes in program!"));
	}
	catch (RepoException& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
	}
	catch (std::exception) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Cartea se afla deja in cos!"));
	}
}

void ConsoleGUIProgram::gui_AddCosRandom() {
	try {
		string n = editNr->text().toStdString();
		int nr;

		editNr->clear();

		nr = stoi(n);
		srv.addRandomToCos(nr);
		reloadBookListFromCos(this->srv.getAllCos());

		QMessageBox::information(this, "Info", QString::fromStdString("Carti adaugate random cu succes in cos!"));
	}
	catch (RepoException& re) {
		QMessageBox::warning(this, "Warning", QString::fromStdString(re.getErrorMessage()));
	}
}

void ConsoleGUIProgram::gui_Export() {
	try {
		string fileName = editFile->text().toStdString();

		editFile->clear();

		srv.saveToFile(fileName);
		reloadBookListFromCos(this->srv.getAllCos());

		QMessageBox::information(this, "Info", QString::fromStdString("Cosul a fost adaugat in fisier!"));
	}
	catch (RepoException&) {
		QMessageBox::warning(this, "Warning", QString::fromStdString("Fisierul nu se poate deschide"));
	}
}