from domain.validators import StudentValidator
from domain.validators import DisciplineValidator
from domain.validators import GradeValidator
from repository.student_repo import InMemoryRepositoryStudent
from repository.student_repo import StudentFileRepo
from repository.disciplina_repo import InMemoryRepositoryDiscipline
from repository.disciplina_repo import DisciplinaFileRepo
from repository.nota_repo import InMemoryRepositoryGrade
from service.student_service import StudentService
from service.disciplina_service import DisciplineService
from service.nota_service import GradeService
from ui.console import Console
from termcolor import colored

reposfile = StudentFileRepo('data/student.txt')
repos = InMemoryRepositoryStudent()
vals = StudentValidator()
srvs = StudentService(repos, vals)
srvsfile = StudentService(reposfile, vals)
repodfile = DisciplinaFileRepo('data/disciplina.txt')
repod = InMemoryRepositoryDiscipline()
vald = DisciplineValidator()
srvd = DisciplineService(repod, vald)
srvdfile = DisciplineService(repodfile,vald)
repon = InMemoryRepositoryGrade()
valn = GradeValidator()
srvn = GradeService(repon, valn, repos, repod)
uis = Console(srvs, srvd, srvn)
uisfile = Console(srvsfile, srvdfile, srvn)


def main_options():
    print("alegeti optiunea de salvare a datelor introduse")
    print("1. memorie")
    print("2. fisier")
    cmd = input("optiunea aleasa este:")
    if cmd == '1':
        uis.student_ui()
    elif cmd == '2':
        uisfile.student_ui()
    elif cmd == 'exit':
        return False
    else:
        print("comanda invalida")


main_options()

