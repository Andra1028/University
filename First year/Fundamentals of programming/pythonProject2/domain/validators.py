from domain.entities import Student
from domain.entities import Disciplina
from domain.entities import Nota

class StudentValidator:
    def validate(self, student):
        errors = []
        id = str(student.getIDStudent())
        if not id.isdigit():
            errors.append('ID student trebuie sa fie numar')
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)


class DisciplineValidator:
    def validate(self, disciplina):
        errors = []
        id = str(disciplina.getIDDisciplina())
        if not id.isdigit():
            errors.append('ID disciplina trebuie sa fie numar')
        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class GradeValidator:
    def validate(self, grade):
        errors = []
        if grade.getNota() < 0 or grade.getNota() > 10:
            errors.append('Notele pot fi intre 1 si 10')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

def test_validate_student():
    test_validator = StudentValidator()
    stud1 = Student(1, 'Popescu Ana')
    test_validator.validate(stud1)

def test_validate_discipline():
    test_validator = DisciplineValidator()
    dis1 = Disciplina(1, 'matematica', 'Popescu Ana')
    test_validator.validate(dis1)

def test_validate_nota():
    test_validator = GradeValidator()
    stud1 = Student(1, 'Popescu Ana')
    dis1 = Disciplina(1, 'matematica', 'Popescu Ana')
    nota = Nota(stud1, dis1, 8)
    test_validator.validate(nota)


test_validate_student()
test_validate_discipline()
test_validate_nota()

