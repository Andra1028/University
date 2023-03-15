#pragma once
#include "Repository.h"

class CarteUndo {
public:
	virtual void doUndo() = 0;

	virtual ~CarteUndo() = default;
};

class UndoAdd : public CarteUndo {
	Carte carte;
	BookRepository& repo;
public:
	UndoAdd(BookRepository& repo, const Carte& c) : repo{ repo }, carte{ c }{}

	void doUndo() override {
		repo.deletec(carte);
	}

};

class UndoDelete : public CarteUndo {
	Carte carte;
	BookRepository& repo;
public:
	UndoDelete(BookRepository& repo, const Carte& c) : repo{ repo }, carte{ c }{};

	void doUndo() override {
		repo.store(carte);
	}
};

class UndoModify : public CarteUndo {
	Carte c1, c2;
	BookRepository& repo;

public:
	UndoModify(BookRepository& repo, const Carte& noua, const Carte& veche) :repo{ repo }, c1{ noua }, c2{ veche }{};

	void doUndo() override {
		repo.deletec(c1);
		repo.store(c2);
	}
};

