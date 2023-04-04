import unittest

from domain.entities import Student
from domain.validators import StudentValidator

class TestCaseStudDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = StudentValidator()

    def test_create_student(self):
        stud1 = Student(1, 'Ana')
        self.assertEqual(stud1.getNumeStudent(), 'Ana')
        self.assertEqual(stud1.getIDStudent(), 1)

        stud1.setNumeStudent('Ana Maria')

        self.assertEqual(stud1.getNumeStudent(), 'Ana Maria')

    def test_equals_student(self):
        stud1 = Student(1, 'Ana')
        stud2 = Student(1, 'Ama')

        self.assertEqual(stud1, stud2)

        stud3 = Student(2, 'Ana')
        self.assertNotEqual(stud1, stud3)


