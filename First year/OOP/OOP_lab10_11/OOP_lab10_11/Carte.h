#pragma once
#include <string>
#include <iostream>
using std::string;
using std::cout;
using std::endl;
class Carte {
private:
	string titlu;
	string autor;
	string gen;
	int an;
public:
	Carte()=delete;
	Carte(string titlu, string autor, string gen, int an) :titlu{ titlu }, autor{ autor }, gen{ gen }, an{ an }{};
	Carte(const Carte& ot) :titlu{ ot.titlu }, autor{ ot.autor }, gen{ ot.gen }, an{ ot.an }{}
	string getTitlu() const;
	string getAutor() const;
	string getGen() const;
	int getAn() const;

	void setTitlu(string titluNou);
	void setAutor(string autorNou);
	void setGen(string genNou);
	void setAn(int anNou);

	bool equal(Carte c);
};

bool cmpTitlu(const Carte& c1, const Carte& c2);
bool cmpAutor(const Carte& c1, const Carte& c2);
bool cmpGenAn(const Carte& c1, const Carte& c2);

void testeDomain();