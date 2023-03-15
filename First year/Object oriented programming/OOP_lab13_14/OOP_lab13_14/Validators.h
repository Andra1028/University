#pragma once

#include "Carte.h"
#include <vector>
///#include "Vector_Dinamic.h"
#include <string>
using std::string;
using std::vector;
class ValidationException {
	vector<string> errorMsg;
public:
	ValidationException(vector<string> errorMessages) :errorMsg{ errorMessages } {};

	string getErrorMessages() {
		string fullMsg = "";
		for (const string e : errorMsg) {
			fullMsg += e + "\n";
		}
		return fullMsg;
	}
};

class BookValidator {

public:
	void valideaza(const Carte& m) {
		vector<string> errors;
		if (m.getTitlu().length() < 2)
			errors.push_back("Titlul trebuie sa aiba cel putin 2 caractere.");
		if (m.getAutor().length() < 2)
			errors.push_back("Autorul trebuie sa aiba cel putin 2 caractere.");
		if (m.getGen().length() < 2)
			errors.push_back("Genul trebuie sa aiba cel putin 2 caractere.");
		if (m.getAn() > 2022)
			errors.push_back("Anul trebuie sa fie mai mic de 2023");
		if (errors.size() > 0)
			throw ValidationException(errors);
	}
};
