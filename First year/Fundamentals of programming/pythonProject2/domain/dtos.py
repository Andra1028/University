class DisciplineStudentGrade:
    def __init__(self, disciplina, student, nota):
        self.__disciplina = disciplina
        self.__student = student
        self.__nota = nota

    def getNumeDisciplina(self):
        return self.__disciplina

    def getNumeStudent(self):
        return self.__student

    def getNota(self):
        return self.__nota

    def setNumeDisciplina(self, value):
        self.__disciplina = value

    def setNumeStudent(self, value):
        self.__student = value

    def setNota(self, value):
        self.__nota = value


class StudentGradeInfo:
    def __init__(self, student, nota_initiala):
        self.__student = student
        self.__cate_note = 1
        self.__total_note = nota_initiala
        self.__avg_note = 0

    def getNumeStudent(self):
        return self.__student

    def getCateNote(self):
        return self.__cate_note

    def getMediaNote(self):
        return self.__avg_note

    def increaseCateNote(self):
        self.__cate_note += 1

    def addToNote(self, value):
        self.__total_note += value

    def compute_means(self):
        self.__avg_note = self.__total_note/self.__cate_note


class DisciplineGradeInfo:
    def __init__(self, disciplina):
        self.__disciplina = disciplina
        self.__cate_note = 1

    def getNumeDisciplina(self):
        return self.__disciplina

    def getCateNote(self):
        return self.__cate_note

    def increaseCateNote(self):
        self.__cate_note += 1
