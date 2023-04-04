from domain.entities import Nota
from domain.entities import Student
from domain.entities import Disciplina


class InMemoryRepositoryGrade:
    def __init__(self):
        self.__nota = []
        self.__new_list = []

    def store(self, nota):
        self.__nota.append(nota)

    def get_all(self):
        return self.__nota


def test_store():
    stud1 = Student(1, 'Popescu Maria')
    dis1 = Disciplina(1, 'matematica', 'Popescu Ana')

    grade1 = Nota(stud1, dis1, 3.8)

    test_repo = InMemoryRepositoryGrade()
    test_repo.store(grade1)

    assert (len(test_repo.get_all()) == 1)

    grade2 = Nota(stud1, dis1, 9.2)

    test_repo.store(grade2)

    assert (len(test_repo.get_all()) == 2)


test_store()


