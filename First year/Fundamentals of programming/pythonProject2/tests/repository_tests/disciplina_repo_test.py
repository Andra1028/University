import unittest

from repository.disciplina_repo import InMemoryRepositoryDiscipline
from domain.entities import Disciplina

class TestCaseDisRepoInMemory(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = InMemoryRepositoryDiscipline()
        d1 = Disciplina(1, 'Matematica', 'Popescu')
        d2 = Disciplina(2, 'Biologie', 'Marinescu')
        d3 = Disciplina(3, 'Fizica', 'Balaban')
        d4 = Disciplina(4, 'Latina', 'Costache')
        d5 = Disciplina(5, 'Engleza', 'Banu')
        d6 = Disciplina(6, 'Informatica', 'Munteanu')
        self.__repo.store(d1)
        self.__repo.store(d2)
        self.__repo.store(d3)
        self.__repo.store(d4)
        self.__repo.store(d5)
        self.__repo.store(d6)

    def test_find(self):
         dis = self.__repo.find('1')
         self.assertEqual(dis.getNumeDisciplina(), 'Matematica')
         stud = self.__repo.find('7')
         self.assertEqual(stud, None)


     def test_delete_id(self):
         self.__repo.delete(1)
         self.assertEqual (self.__repo.size(), 5)
         self.__repo.delete(10)
         self.assertEqual (self.__repo.size(), 5)


    def test_delete_name(self):
        self.__repo.delete_name('Biologie')
        self.assertEqual (self.__repo.size(), 4)
        self.__repo.delete_name('djkshhc')
        self.assertEqual (self.__repo.size(), 4)


    def test_search_id(self):
        dis = self.__repo.search_id(5)
        self.assertEqual (dis.getNumeDisciplina(), 'Engleza')
        stud = self.__repo.search_id(10)
        self.assertEqual (stud, None)


     def test_update(self):
        updated = Disciplina(5, 'Franceza', 'Banu')
        stud = self.__repo.update(1, updated)
        self.assertEqual (stud.getNumeDisciplina(), 'Franceza')
        self.assertEqual (stud.getProfesor(), 'Banu')
