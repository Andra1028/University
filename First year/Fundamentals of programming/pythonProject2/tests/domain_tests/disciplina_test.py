import unittest

from domain.entities import Disciplina
from domain.validators import DisciplineValidator

class TestCaseDisDomain(unittest.TestCase):
    def setUp(self) -> None:
        self.__validator = DisciplineValidator()

    def test_create_disciplina(self):
        dis1 = Disciplina(1, 'Biologie', 'Popescu M.')
        self.assertEqual(dis1.getNumeDisciplina(), 'Biologie')
        self.assertEqual(dis1.getIDDisciplina(), 1)
        self.assertEqual(dis1.getProfesor(), 'Popescu M.')

        dis1.setNumeDisciplina('Biologie vegetala')
        dis1.setProfesor('Popescu Maria')

        self.assertEqual(dis1.getNumeDisciplina(), 'Biologie vegetala')
        self.assertEqual(dis1.getProfesor(), 'Popescu Maria')

    def test_equals_disciplina(self):
        dis1 = Disciplina(1, 'Matematica', 'Costica')
        dis2 = Disciplina(1, 'Matematica', 'Marcel')

        self.assertEqual(dis1, dis2)

        dis3 = Disciplina(2, 'Matematica', 'Costica')
        self.assertNotEqual(dis1, dis3)


