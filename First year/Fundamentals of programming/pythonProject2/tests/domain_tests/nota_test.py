import unittest

from domain.entities import Student
from domain.entities import Disciplina
from domain.entities import Nota
from domain.validators import GradeValidator


class TestCaseNotaDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = GradeValidator()

    def test_create_grade(self):
        stud = Student(1, 'Popescu Maria')
        dis = Disciplina(1, 'matematica', 'Marinescu Ion')
        grade = Nota(stud, dis, 7.8)
        self.assertEqual(grade.getStudent(),stud)
        self.assertEqual(grade.getDisciplina(), dis)
        self.assertEqual(grade.getNota(), 7.8)


