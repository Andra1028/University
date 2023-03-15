
class Student:
    def __init__(self, IDStudent, nume):
        """
        Creeaza un nou student cu campurile IDStudent si nume
        :param IDStudent: ID studentului
        :type IDStudent: integer
        :param nume: numele studentului
        :type nume: str
        """
        self.__IDStudent = IDStudent
        self.__numeStudent = nume

    def getIDStudent(self):
        return self.__IDStudent

    def getNumeStudent(self):
        return self.__numeStudent

    def setIDStudent(self, value):
        self.__IDStudent = value

    def setNumeStudent(self, value):
        self.__numeStudent = value


    def __eq__(self, other):
        """
        Verifica egalitatea intre studentul curent si studentul other
        :param other:
        :type other: Student
        :return: True daca studentii sunt egali (=au acelasi id), False altfel
        :rtype: bool
        """
        if self.__IDStudent == other.__IDStudent:
            return True
        return False

    def __str__(self):
        return "ID Student: " + str(self.__IDStudent) + '; Nume: ' + self.__numeStudent


class Disciplina:
    def __init__(self, IDDisciplina, nume, profesor):
        """
        Creeaza o noua disciplina cu campurile IDDisciplina, numa, profesor
        :param IDDisciplina: ID disciplina
        :type IDStudent: integer
        :param nume: numele disciplinei
        :type nume: str
        :param profesor: numele profesorului
        :type profesor: str
        """
        self.__IDDisciplina = IDDisciplina
        self.__numeDisciplina = nume
        self.__profesor = profesor

    def getIDDisciplina(self):
        return self.__IDDisciplina

    def getNumeDisciplina(self):
        return self.__numeDisciplina

    def getProfesor(self):
        return self.__profesor

    def setIDDisciplina(self, value):
        self.__IDDisciplina = value

    def setNumeDisciplina(self, value):
        self.__numeDisciplina = value

    def setProfesor(self, value):
        self.__profesor = value




    def __str__(self):
        return "ID Disciplina: " + str(
            self.__IDDisciplina) + '; Nume: ' + self.__numeDisciplina + '; Profesor: ' + self.__profesor


class Nota:
    def __init__(self, student, disciplina, nota):
        """

        :param student: student
        :param disciplina: disciplina
        :param nota: nota pentru student la disciplina introdusa
        """
        self.__disciplina = disciplina
        self.__student = student
        self.__nota = nota

    def getStudent(self):
        return self.__student

    def getDisciplina(self):
        return self.__disciplina

    def getNota(self):
        return self.__nota

    def setStudent(self, value):
        self.__student = value

    def setDisciplina(self, value):
        self.__disciplina = value

    def setNota(self, value):
        self.__nota = value

    def __str__(self):
        return 'Student: ' + str(self.__student.getNumeStudent()) + '; '  \
               'Disciplina: ' + str(self.__disciplina.getNumeDisciplina()) + '; Nota: ' + str(
            self.__nota)


def test_create_student():
    stud1 = Student(1, 'Popescu Ana')
    assert (stud1.getIDStudent() == 1)
    assert (stud1.getNumeStudent() == 'Popescu Ana')

    stud1.setNumeStudent('Popescu Ana Maria')
    stud1.setIDStudent(1)

    assert (stud1.getNumeStudent() == 'Popescu Ana Maria')
    assert (stud1.getIDStudent() == 1)




def test_create_disciplina():
    dis1= Disciplina(1, 'matematica', 'Ciupala')
    assert(dis1.getIDDisciplina() == 1)
    assert(dis1.getNumeDisciplina() == 'matematica')
    assert(dis1.getProfesor() == 'Ciupala')
    dis1.setProfesor('Ciupala Gabriel')
    dis1.setNumeDisciplina('matematica')
    dis1.setIDDisciplina(1)
    assert (dis1.getIDDisciplina() == 1)
    assert (dis1.getNumeDisciplina() == 'matematica')
    assert (dis1.getProfesor() == 'Ciupala Gabriel')


def test_create_grade():
    stud = Student(1, 'Popescu Maria')
    dis = Disciplina(1, 'matematica', 'Marinescu Ion')
    grade = Nota(stud, dis, 7.8)
    assert(grade.getStudent() == stud)
    assert(grade.getDisciplina() == dis)
    assert(grade.getNota() == 7.8)


test_create_student()
test_create_disciplina()
test_create_grade()


