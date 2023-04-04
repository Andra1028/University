from domain.entities import Student
from domain.validators import StudentValidator
from repository.student_repo import InMemoryRepositoryStudent


class StudentService:
    """
        GRASP Controller
        Responsabil de efectuarea operatiilor cerute de utilizator
        """
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de studenti
        :type repo: InMemoryRepository
        :param validator: validator pentru verificarea studentilor
        :type validator: StudentValidator
        """
        self.__repo = repo
        self.__validator = validator


    def add_student(self, IDStudent, nume):
        """
        Adauga studentul
        :param IDStudent: ID student
        :type IDStudent: int
        :param nume: numele studentului
        :type nume: str
        :return: obiectul de tip Student creat
        :rtype:-; studentul s-a adaugat in lista
        :raises: ValueError daca Studentul are date invalide
        """
        s = Student(IDStudent, nume)

        self.__validator.validate(s)
        self.__repo.store(s)
        return s


    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii
        :return: lista de studenti
        :rtype: list of objects de tip Student
        """
        return self.__repo.get_all_students()

    def delete_student(self, id):
        return self.__repo.delete(id)

    def delete_name_student(self, nume):
        return self.__repo.delete_name(nume)

    def search_id_student(self,id):
        return self.__repo.search_id(id)

    def search_name_student(self, nume):
        return self.__repo.search_name(nume)

    def delete_all_students(self):
        return self.__repo.delete_all()

    def update_student(self, id, nume):
        """
        Modifica studentul cu id-ul dat
        :param id: Id student care se modifica
        :param nume: Nume nou student
        :return:
        """
        stud = Student(id, nume)
        self.__validator.validate(stud)
        return self.__repo.update(id, stud)


def test_add():
    repo = InMemoryRepositoryStudent()
    validator = StudentValidator()
    test_srv = StudentService(repo, validator)

    added_student = test_srv.add_student(1, 'Popescu Ana')
    assert (added_student.getIDStudent() == 1)
    assert (added_student.getNumeStudent() == 'Popescu Ana')

    assert (len(test_srv.get_all_students()) == 1)

    try:
        added_student = test_srv.add_student(1, 'Pop Ana')
        assert False
    except ValueError:
        assert True

