from domain.entities import Nota
from domain.validators import StudentValidator
from domain.validators import GradeValidator
from repository.disciplina_repo import InMemoryRepositoryDiscipline
from repository.nota_repo import InMemoryRepositoryGrade
from repository.student_repo import InMemoryRepositoryStudent
from domain.dtos import DisciplineStudentGrade
from domain.dtos import StudentGradeInfo
from domain.dtos import DisciplineGradeInfo
from service.student_service import StudentService
from domain.validators import GradeValidator


class GradeService:
    def __init__(self, grade_repo, grade_val, student_repo, disciplina_repo):
        self.__grade_repo = grade_repo
        self.__grade_val = grade_val
        self.__student_repo = student_repo
        self.__disciplina_repo = disciplina_repo

    def create_grade(self, student_id, disciplina_id, nota):
        """
        Creeaza o nota
        :param student_id: Id student de adaugat
        :param disciplina_id: Id disciplina de adaugat
        :param nota: nota
        :return: nota creata cu datele date
        """
        student = self.__student_repo.find(student_id)
        if student is None:
            raise ValueError('Nu exista student cu id-ul dat')

        disciplina = self.__disciplina_repo.find(disciplina_id)

        if disciplina is None:
            raise ValueError('Nu exista disciplina cu id-ul dat')

        grade = Nota(student, disciplina, nota)
        self.__grade_val.validate(grade)
        self.__grade_repo.store(grade)
        return grade

    def get_all(self):
        return self.__grade_repo.get_all()

    def cmp(self, a, b):
        if a.getStudent().getNumeStudent() < b.getStudent().getNumeStudent():
            return False
        elif a.getStudent().getNumeStudent() == b.getStudent().getNumeStudent():
            if a.getNota < b.getNota:
                return False
        return True

    def cmpGS(self, a, b):
        if a.getMediaNote() < b.getMediaNote():
            return False
        return True

    def partition(self, l, left, right, reverse = False ):
        """
        Split the values:
        smaller pivot greater
        return pivot position
        post: left we have < pivot
        right we have > pivot
        """
        pivot = l[left]
        i = left
        j = right
        while i != j:
            while l[j] >= pivot and i < j:
                j = j - 1
        l[i] = l[j]
        while l[i] <= pivot and i < j:
            i = i + 1
        l[j] = l[i]
        l[i] = pivot
        return i

    def quickSort(self, l, left, right, *, key = lambda x: x, reverse = False):
        # partition the list
        pos = self.partition(l, left, right, reverse)
        # order the left part
        if left < pos - 1:
            self.quickSort(l, left, pos - 1)
        # order the right part
        if pos + 1 < right:
            self.quickSort(l, pos + 1, right)

    def gnomeSort(self, arr, n, *, key = lambda x: x, reverse = False):
        index = 0
        while index < n:
            if index == 0:
                index = index + 1
            if arr[index] <= arr[index - 1]:
                index = index + 1
            else:
                arr[index], arr[index - 1] = arr[index - 1], arr[index]
                index = index - 1

        return arr

    def list_students(self, id_disciplina):
        disciplina = self.__disciplina_repo.find(id_disciplina)

        if disciplina is None:
            raise ValueError('Nu exista aceasta disciplina')
        all_grades = self.__grade_repo.get_all()
        disciplina_note = []

        for grade in all_grades:
            if grade.getDisciplina().getIDDisciplina() == id_disciplina:
                discipline_student_grade = DisciplineStudentGrade(grade.getDisciplina().getNumeDisciplina(),
                                                                  grade.getStudent().getNumeStudent(), grade.getNota())
                disciplina_note.append(discipline_student_grade)

        n = len(disciplina_note) - 1
        self.quickSort(disciplina_note, 1, n, key=lambda x: (x.getStudent().getNumeStudent(), x.getNota()),reverse=False)

        return disciplina_note

    def get_student_info(self):
        """
        Primi 20% din studenți ordonat dupa media notelor la toate disciplinele (nume și notă)
        :return: lista de obiecte DTO care contin informatia necesara
        :rtype: list of StudentGradeInfo objects
        """
        all_grades = self.__grade_repo.get_all()

        students_grades = {}
        cati_studenti = 0

        for grade in all_grades:
            if grade.getStudent().getIDStudent() not in students_grades:
                dto = StudentGradeInfo(grade.getStudent().getNumeStudent(), grade.getNota())
                students_grades[grade.getStudent().getIDStudent()] = dto
                cati_studenti += 1
            else:
                students_grades[grade.getStudent().getIDStudent()].increaseCateNote()
                students_grades[grade.getStudent().getIDStudent()].addToNote(grade.getNota())

        for stud_id, stud_nota in students_grades.items():
            stud_nota.compute_means()

        n = int(20 / 100 * cati_studenti)
        new_list = list(students_grades.values())
        cati_studenti = len(new_list)
        new_list = self.gnomeSort(new_list, cati_studenti, key=lambda x: x.getMediaNote(), reverse=False)
        new_list = new_list[:n]
        return new_list

    def get_discipline_info(self):
        """
        Primele3disciplinecucelemaimultenote(sevaafișanume_disciplină,număr_note).
        :return: list of DisciplineGradeInfo objects
        """
        all_grades = self.__grade_repo.get_all()

        grades = {}

        for grade in all_grades:
            if grade.getDisciplina().getIDDisciplina() not in grades:
                dto = DisciplineGradeInfo(grade.getDisciplina().getNumeDisciplina())
                grades[grade.getDisciplina().getIDDisciplina()] = dto
            else:
                grades[grade.getDisciplina().getIDDisciplina()].increaseCateNote()

        n = 3
        new_list = list(grades.values())
        new_list = sorted(new_list, key=lambda x: x.getCateNote(), reverse=True)
        new_list = new_list[:n]
        return new_list

def test_gnome():
    repo_grade = InMemoryRepositoryGrade()
    grade_val = GradeValidator()
    repo_stud = InMemoryRepositoryStudent()
    repo_dis = InMemoryRepositoryDiscipline()
    test_srv = GradeService(repo_grade, grade_val, repo_stud, repo_dis)
    l = [2, 5, 7, 9]
    n = 4
    new_list = test_srv.gnomeSort(l, n, reverse=False)
    print(new_list)

def test_quick():
    repo_grade = InMemoryRepositoryGrade()
    grade_val = GradeValidator()
    repo_stud = InMemoryRepositoryStudent()
    repo_dis = InMemoryRepositoryDiscipline()
    test_srv = GradeService(repo_grade, grade_val, repo_stud, repo_dis)
    l = [2, 5, 7, 9]
    n = 4
    test_srv.quickSort(l, 0, n-1, reverse=False)
    print(l)

test_gnome()
print('\n')
test_quick()