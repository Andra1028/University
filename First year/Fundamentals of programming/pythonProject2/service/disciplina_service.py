from domain.entities import Disciplina
from domain.validators import DisciplineValidator
from repository.disciplina_repo import InMemoryRepositoryDiscipline


class DisciplineService:
    """
        GRASP Controller
        Responsabil de efectuarea operatiilor cerute de utilizator
        """
    def __init__(self, repo, validator):
        """
        Initializeaza service
        :param repo: obiect de tip repo care ne ajuta sa gestionam multimea de discipline
        :type repo: InMemoryRepository
        :param validator: validator pentru verificarea disciplinelor
        :type validator: DisciplineValidator
        """
        self.__repo = repo
        self.__validator = validator

    def add_discipline(self, IDDisciplina, nume, profesor):
        """
        Adauga studentul
        :param IDDiscplina: Id disciplina
        :type IDDisciplina: int
        :param nume: numele disciplinei
        :type nume: str
        :param profesor: numele profesorului
        :type profesor: str
        :return: obiectul de tip Disciplina creat
        :rtype:-; Disciplina s-a adaugat in lista
        :raises: ValueError daca Disciplina are date invalide
        """
        s = Disciplina(IDDisciplina, nume, profesor)

        self.__validator.validate(s)
        self.__repo.store(s)
        return s


    def get_all_disciplines(self):
        """
        Returneaza o lista cu toate disciplinele
        :return: lista de discipline
        :rtype: list of objects de tip Disciplina
        """
        return self.__repo.get_all_disciplines()

    def delete_discipline(self, id):
        return self.__repo.delete(id)

    def delete_name_discipline(self, nume):
        return self.__repo.delete_name(nume)

    def delete_teacher_discipline(self, prof):
        return self.__repo.delete_teacher(prof)

    def delete_all_disciplines(self):
        return self.__repo.delete_all()

    def update_discipline(self, id, nume,profesor):
        """
        Modifica disciplina cu id-ul dat
        :param id: Id disciplina care se modifica
        :param nume: Nume nou disciplina
        :param profesor: Profesor nou
        :return:
        """
        dis = Disciplina(id, nume, profesor)
        self.__validator.validate(dis)
        return self.__repo.update(id, dis)

    def search_id_discipline(self, id):
        return self.__repo.search_id(id)

    def search_name_discipline(self, nume):
        return self.__repo.search_name(nume)

    def search_teacher_discipline(self, prof):
        return self.__repo.search_teacher(prof)


def test_add_disciplina():
    repo = InMemoryRepositoryDiscipline()
    validator = DisciplineValidator()
    test_srv = DisciplineService(repo, validator)

    added_discipline = test_srv.add_discipline(1, 'matematica' , 'Ciupala Gabriel')
    assert (added_discipline.getIDDisciplina() == 1)
    assert (added_discipline.getNumeDisciplina() == 'matematica')
    assert (added_discipline.getProfesor() == 'Ciupala Gabriel')

    assert (len(test_srv.get_all_disciplines()) == 1)

    try:
        added_discipline = test_srv.add_discipline(1, 'geografie', 'Marinescu Ion')
        assert False
    except ValueError:
        assert True