#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QWindow>
#include <QPushButton>
#include <QListWidget>
#include <QHBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <QDebug>
#include <QPainter>
#include <cstdlib>
#include <set>
#include <string>
#include <vector>
#include "Service.h"
#include "Observer.h"

using std::vector;
using std::string;
using std::set;
using std::to_string;

class ConsoleGUI : public QWidget {
private:
	QVBoxLayout* btn_layout;
	BookStore& srv;

	//add
	//QGroupBox* groupBoxAdd = new QGroupBox(tr("Adaugare"));

	QLabel* lblTitlu = new QLabel("Titlu:");
	QLabel* lblAutor = new QLabel("Autor:");
	QLabel* lblGen = new QLabel("Gen:");
	QLabel* lblAn = new QLabel("An:");

	QLineEdit* editTitlu;
	QLineEdit* editAutor;
	QLineEdit* editGen;
	QLineEdit* editAn;

	QPushButton* btnAddBook;

	//dell
	QPushButton* btnDellBook;

	//modify
	QPushButton* btnModifyBook;

	//find
	QPushButton* btnFindBook;

	//filter
	QGroupBox* groupBoxFilter = new QGroupBox(tr("Filtrare"));

	QLabel* lblFilterTitlu = new QLabel{ "Titlul dupa care se filtreaza:" };
	QLineEdit* editFilterTitlu;
	QLabel* lblFilterAn = new QLabel{ "Anul dupa care se filtreaza:" };
	QLineEdit* editFilterAn;
	QPushButton* btnFilterTitlu;
	QPushButton* btnFilterAn;

	//sort
	QGroupBox* groupBoxSort = new QGroupBox(tr("Tip sortare"));

	QRadioButton* radioSortTitlu = new QRadioButton(QString::fromStdString("Titlu"));
	QRadioButton* radioSortAutor = new QRadioButton(QString::fromStdString("Autor"));
	QRadioButton* radioSortGenAn = new QRadioButton(QString::fromStdString("Gen+An"));
	QPushButton* btnSortBooks;

	QPushButton* btnReloadData;

	QTableWidget* tableBooks;

	//undo
	QPushButton* btnUndo;

	//tip
	QPushButton* btnTip;

	void initializeGUIComponents();

	void connectSignalsSlots();

	void reloadBookList(vector<Carte> carti);

public:
	ConsoleGUI(BookStore& srv) : srv{ srv } {
		initializeGUIComponents();
		connectSignalsSlots();
		reloadBookList(srv.getAllBooks());
	}

	void gui_AddBook();
	void gui_DellBook();
	void gui_ModifyBook();
	void gui_undo();
	void gui_addGen();
};


class ConsoleGUIProgram :public QWidget {
private:
	BookStore& srv;
	QHBoxLayout* lyMain;
	QListWidget* bookList;

	//add
	QLabel* lblTitluC = new QLabel("Titlu:");
	QLineEdit* editTitluC;
	QPushButton* btnAddCos;

	//add random
	QLabel* lblNr = new QLabel("Numar:");
	QLineEdit* editNr;
	QPushButton* btnAddRandom;

	//goleste program
	QPushButton* btnGoleste;

	//export
	QLabel* lblFile = new QLabel("Fisier:");
	QLineEdit* editFile;
	QPushButton* btnExport;

	QPushButton* btnLabelWindow;
	QPushButton* btnTableWindow;
	QPushButton* btnDrawWindow;


	QTableWidget* tableBooks;

	QWidget* widgetDynamic;
	QVBoxLayout* lyBtnDynamic;


	void initializeGUIProgComponents();
	void connectSignalsSlotsProg();
	void reloadBookListFromCos(vector<Carte> carti);


	set<string> getGenres(const vector<Carte>& carti);
	void reloadDynamicButtons();

	void gui_AddCos();

public:
	ConsoleGUIProgram(BookStore& srv) : srv{ srv } {
		initializeGUIProgComponents();
		connectSignalsSlotsProg();
		reloadBookListFromCos(srv.getAllCos());
	}

	void gui_AddCosRandom();
	void gui_Export();
};

class BookCounter : public QWidget, public Observer {
private:
	QLineEdit* textbox;
	Cos& cos;

public:
	BookCounter(Cos& cos) : cos{ cos } {
		textbox = new QLineEdit;
		QHBoxLayout* layout = new QHBoxLayout;
		setLayout(layout);
		layout->addWidget(textbox);
		cos.addObserver(this);
		update();
	}
	void update() override {
		textbox->setText(std::to_string(cos.getAllCos().size()).c_str());
	}
	~BookCounter() {
		cos.removeObserver(this);
	}
};

class BookDraw : public QWidget, public Observer {
private:
	Cos& cos;
protected:
	void paintEvent(QPaintEvent* ev) override {
		int x = 10, y = 10, w = 10;
		QPainter g{ this };
		g.setPen(Qt::blue);
		for (const auto& book : cos.getAllCos())
		{
			g.drawRect(x, y, w, book.getAn() / 50);
			x += 40;
		}
	}
public:
	BookDraw(Cos& cos) : cos{ cos } {
		update();
	}
	void update() override {
		this->repaint();
	}
};

class CosGUITable :public QWidget, public Observer {
private:
	Cos& p;
	QTableWidget* table;
	QPushButton* btnEmpty;
	void initGUI() {
		QHBoxLayout* ly = new QHBoxLayout{};
		this->setLayout(ly);
		table = new QTableWidget{};
		btnEmpty = new QPushButton{ "Goleste Cos" };
		ly->addWidget(table);
		ly->addWidget(btnEmpty);
		setAttribute(Qt::WA_DeleteOnClose);
		p.addObserver(this);
	};
	void reloadTableData(const vector<Carte>& books) {
		/*this->table->setColumnCount(4);
		this->table->setRowCount(static_cast<int>(books.size()));
		for (int i = 0; i < books.size(); i++) {
			table->setItem(i, 0, new QTableWidgetItem(QString::fromStdString(books[i].getTitlu())));
			table->setItem(i, 1, new QTableWidgetItem(QString::fromStdString(books[i].getAutor())));
			table->setItem(i, 2, new QTableWidgetItem(QString::fromStdString(books[i].getGen())));
			table->setItem(i, 3, new QTableWidgetItem(QString::number(books[i].getAn())));
		}*/

		this->table->clearContents();
		this->table->setRowCount(books.size());
		this->table->setColumnCount(4);

		int lineNumber = 0;
		for (auto& activity : books) {
			this->table->setItem(lineNumber, 0, new QTableWidgetItem(QString::fromStdString(activity.getTitlu())));
			this->table->setItem(lineNumber, 1, new QTableWidgetItem(QString::fromStdString(activity.getAutor())));
			this->table->setItem(lineNumber, 2, new QTableWidgetItem(QString::fromStdString(activity.getGen())));
			this->table->setItem(lineNumber, 3, new QTableWidgetItem(QString::number(activity.getAn())));
			lineNumber++;
		}

	};

	void connectSignalsSlots() {
		QObject::connect(btnEmpty, &QPushButton::clicked, [&]() {
			p.emptyCos();
			});
	}


public:
	CosGUITable(Cos& cos) :p{ cos } {
		p.addObserver(this);
		initGUI();
		connectSignalsSlots();
	};

	void update() override {
		this->reloadTableData(p.getAllCos());
	}
	~CosGUITable() {
		p.removeObserver(this);
	}

};
