#pragma once
#include "Carte.h"
#include "Observer.h"
#include <vector>
#include <algorithm>
#include <random>    // std::default_random_engine
#include <chrono>    // std::chrono::system_clock

using std::vector;
class Cos : public Observable {
private:
	vector<Carte> CosCarti;

public:
	Cos() = default;

	void addCos(const Carte& s);

	void emptyCos();

	void addRandomBooks(vector<Carte> originalBooks, int howMany);

	const vector<Carte>& getAllCos();

	void valideazaCos(const Carte& c);
};

void teste_cos();
