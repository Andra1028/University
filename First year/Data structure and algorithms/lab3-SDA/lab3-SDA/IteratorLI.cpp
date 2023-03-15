#include "IteratorLI.h"
#include "LI.h"
#include <exception>

IteratorLI::IteratorLI(const LI& li) : lista(li) {
    /* de adaugat */
    curent = li.prim;
}

void IteratorLI::prim() {
    /* de adaugat */
    curent = lista.prim;
}

void IteratorLI::urmator() {
    /* de adaugat */
   if (curent == nullptr)
       throw std::exception();
   else  if (curent->urm != nullptr)
       curent = curent->urmator();
}

void IteratorLI::anterior()
{
   if (curent == nullptr)
       throw std::exception();
   else if(curent->ant != nullptr)
       curent = curent->anterior();
}

bool IteratorLI::valid() const {
    /* de adaugat */      
    return curent != nullptr;
}

TElem IteratorLI::element() const {
    /* de adaugat */
   if (curent == nullptr)
       throw std::exception();
    else 
       return curent->element();
}
