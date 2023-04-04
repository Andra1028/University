import unittest

from repository.student_repo import InMemoryRepositoryStudent
from domain.entities import Student

class TestCaseStudRepoInMemory(unittest.TestCase):
    def setUp(self) -> None:
        self.__repo = InMemoryRepositoryStudent()
        s1 = Student(1, 'Ana')
        s2 = Student(2, 'Maria')
        s3 = Student(3, 'Paula')
        s4 = Student(4, 'Bogdan')
        s5 = Student(5, 'Ana')
        s6 = Student(6, 'Maria')
        self.__repo.store(s1)
        self.__repo.store(s2)
        self.__repo.store(s3)
        self.__repo.store(s4)
        self.__repo.store(s5)
        self.__repo.store(s6)

     def test_find(self):
         stud = self.__repo.find('1')
         self.assertEqual(stud.getNumeStudent(), 'Ana')
         stud = self.__repo.find('7')
         self.assertEqual(stud, None)

    def test_delete_id(self):
        self.__repo.delete(1)
        self.assertEqual(self.__repo.size(), 5)
        self.__repo.delete(9)
        self.assertEqual(self.__repo.size(), 5)

    def test_delete_name(self):
        self.__repo.delete_name('Ana')
        self.assertEqual (self.__repo.size(), 4)
        self.__repo.delete_name('djkshhc')
        self.assertEqual (self.__repo.size(), 4)

    def test_search_id(self):
        stud = self.__repo.search_id(3)
        self.assertEqual (stud.getNumeStudent(), 'Paula')
        stud = self.__repo.search_id(10)
        self.assertEqual (stud, None)

    def test_update(self):
        updated = Student(6, 'Mariana')
        stud = self.__repo.update(6, updated)
        self.assertEqual (stud.getNumeStudent(), 'Mariana')