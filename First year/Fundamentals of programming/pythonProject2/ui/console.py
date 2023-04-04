from termcolor import colored
import random, string


def random_name(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(length))


def random_id(length):
    number = '0123456789'
    id = ''
    for i in range(0, length):
        id += random.choice(number)
    return id


class Console:
    def __init__(self, srv_student, srv_disciplina, srv_nota):
        """
        Initializeaza consola
        :param srv_student:
        :param srvz_disciplina:
        :param srv_nota:
        """
        self.__srv_student = srv_student
        self.__srv_disciplina = srv_disciplina
        self.__srv_nota = srv_nota


    def __print_all_students(self):
        """
        Afiseaza toti studentii

        """
        student_list = self.__srv_student.get_all_students()
        if len(student_list) == 0:
            print('Nu exista studenti in lista.')
        else:
            print('Lista de studenti este:')
            for student in student_list:
                print(
                    'ID: ', colored(str(student.getIDStudent()), 'cyan'), ' - Nume: ',
                    colored(student.getNumeStudent(), 'cyan'))

    def __add_student(self):
        """
        Adauga un student cu datele citite de la tastatura
        """
        id = input("ID student:")
        nume = input("Nume student:")

        try:
            added_student = self.__srv_student.add_student(id, nume)
            print('Id: ' + str(added_student.getIDStudent()) + ' - Nume: ' +
                  added_student.getNumeStudent() + '  a fost adaugat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __add_student_random(self):
        """
        Adauga x  studenti cu datele generate random
        """
        x = int(input('Introduceti numar: '))
        while x > 0:
            id = random_id(3)
            nume = random_name(10)

            try:
                added_student = self.__srv_student.add_student(id, nume)
                print('Id: ' + str(added_student.getIDStudent()) + ' - Nume: ' +
                      added_student.getNumeStudent() + '  a fost adaugat cu succes.')
            except ValueError as ve:
                print(colored(str(ve), 'red'))
            x = x-1

    def __delete_student(self):
        id = int(input("ID student:"))
        try:
            self.__srv_student.delete_student(id)
            print('stergerea s-a efectuat cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_name_student(self):
        nume = input("Nume student:")
        try:
            self.__srv_student.delete_name_student(nume)
            print('stergerea s-a efectuat cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_all_student(self):
        self.__srv_student.delete_all_students()
        print("Toti studentii au fost stersi")

    def __search_id_student(self):
        id = input('Id: ')
        id = int(id)
        try:
            print(self.__srv_student.search_id_student(id))
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __search_name_student(self):
        nume = input('Nume: ')
        try:
            print(self.__srv_student.search_name_student(nume))
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __modify_student(self):
        id = input("ID student de modificat:")
        nume = input("Nume nou student: ")
        try:
            self.__srv_student.update_student(id, nume)
            print('Studentul a fost modificat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __print_all_disciplines(self):
        """
        Afiseaza toate disciplinele

        """
        discipline_list = self.__srv_disciplina.get_all_disciplines()
        if len(discipline_list) == 0:
            print('Nu exista discipline in lista.')
        else:
            print('Lista de discipline este:')
            for disciplina in discipline_list:
                print(
                    'ID: ', colored(str(disciplina.getIDDisciplina()), 'cyan'), ' - Nume: ',
                    colored(disciplina.getNumeDisciplina(), 'cyan'), ' - Profesor',
                    colored(disciplina.getProfesor(), 'cyan'))

    def __add_discipline(self):
        """
        Adauga o disciplina cu datele citite de la tastatura
        """
        id = input("ID disciplina:")
        nume = input("Nume disciplina:")
        profesor = input('Nume profesor:')
        id = int(id)

        try:
            added_discipline = self.__srv_disciplina.add_discipline(id, nume, profesor)
            print('Id: ' + str(added_discipline.getIDDisciplina()) + ' - Nume: ' +
                  added_discipline.getNumeDisciplina() + ' - Profesor: ' +
                  added_discipline.getProfesor() + '  a fost adaugat cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_discipline(self):
        id = input("ID disciplina:")
        try:
            self.__srv_disciplina.delete_discipline(id)
            print('stergerea s-a efectuat cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_name_discipline(self):
        nume = input("Nume disciplina:")
        try:
            self.__srv_disciplina.delete_name_discipline(nume)
            print('stergerea s-a efectuat cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_teacher_discipline(self):
        prof = input(" Nume profesor:")
        try:
            self.__srv_disciplina.delete_teacher_discipline(prof)
            print('stergerea s-a efectuat cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __delete_all_disciplina(self):
        self.__srv_disciplina.delete_all_students()
        print("Toate disciplinele au fost sterse")

    def __modify_discipline(self):
        id = input("ID disciplina de modificat:")
        nume = input("Nume nou disciplina: ")
        profesor = input("Profesor nou: ")
        try:
            self.__srv_disciplina.update_discipline(id, nume, profesor)
            print('Disciplina a fost modificata cu succes.')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __search_id_disciplina(self):
        id = int(input('Id: '))
        try:
            print(self.__srv_disciplina.search_id_discipline(id))
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __search_name_disciplina(self):
        nume = input('Nume: ')
        try:
            print(self.__srv_disciplina.search_name_discipline(nume))
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __search_teacher_disciplina(self):
        prof = input('Nume profesor: ')
        try:
            print(self.__srv_disciplina.search_teacher_discipline(prof))
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __print_grades(self, grades_list):
        """
        Afiseaza o lista de nota

        """

        if len(grades_list) == 0:
            print('Nu exista note in lista.')
        else:
            print('Lista de note este:')
            for grade in grades_list:
                print('Student: ', colored(str(grade.getStudent().getNumeStudent()), 'cyan'), '; ',
                      'Disciplina: ', colored(str(grade.getDisciplina().getNumeDiscipline()), 'cyan'), ';', 'Nota: ',
                      colored(str(grade.getNota()), 'cyan'))

    def __add_grade(self):
        """
        Adauga o nota cu datele citite de la tastatura
        """
        id_student = input("ID student:")
        id_disciplina = int(input("ID disciplina:"))
        nota = float(input('Nota:'))

        try:
            added_grade = self.__srv_nota.create_grade(id_student, id_disciplina, nota)
            print('Nota ', added_grade, 'a fost adaugata cu succes')
        except ValueError as ve:
            print(colored(str(ve), 'red'))

    def __get_list_for_discipline(self):
        """
        afiseaza studentii si notele lor la disciplina data, ordonati dupa student, note
        """
        disciplina_id = input('ID disiciplina:')
        try:
            students_grades = self.__srv_nota.list_students(disciplina_id)
            for student_grade in students_grades:
                print('Disciplina:', colored(student_grade.getNumeDisciplina(), 'cyan'), '; Students:',
                      colored(student_grade.getNumeStudent(), 'magenta'), '; Nota:',
                      colored(str(student_grade.getNota()), 'blue'))

        except ValueError as e:
            print(colored(str(e), 'red'))

    def __create_students_raport(self):
        """
        Primi 20% din studenți ordonat dupa media notelor la toate disciplinele (nume și notă)
        """
        list_students_infos = self.__srv_nota.get_student_info()
        for student in list_students_infos:
            print('Student:', colored(student.getNumeStudent(), 'cyan'), 'Media notelor:',
                  colored(str(student.getMediaNote()), 'magenta'))

    def __create_discipline_raport(self):
        """
        Primele 3 discipline cu cele mai multe note (se va afișa nume_disciplină, număr_note).
        """
        list_dis_infos = self.__srv_nota.get_discipline_info()
        for dis in list_dis_infos:
            print('Disciplina:', colored(dis.getNumeDisciplina(), 'cyan'), 'Numar note:',
                  colored(str(dis.getCateNote()), 'magenta'))

    def student_ui(self):
        while True:
            print(
                'Comenzi disponibile: add_student, random_x, delete_id_student, delete_name_student, search_id_student, search_name_student, delete_all_student, show_all_student, modify_student, add_disciplina,  delete_id_disciplina, delete_name_disciplina, delete_teacher_disciplina, delete_all_disciplina, search_id_disciplina, search_name_disciplina, search_teacher_disciplina, modify_disciplina, show all_disciplina, assing_nota, show_all_nota, exit')
            cmd = input('Comanda este:')
            cmd = cmd.lower().strip()
            if cmd == 'add_student':
                self.__add_student()
            elif cmd == 'show_all_student':
                self.__print_all_students()
            elif cmd == 'delete_id_student':
                self.__delete_student()
            elif cmd == 'delete_name_student':
                self.__delete_name_student()
            elif cmd == 'modify_student':
                self.__modify_student()
            elif cmd == 'delete_all_student':
                self.__delete_all_student()
            elif cmd == 'search_id_student':
                self.__search_id_student()
            elif cmd == 'search_name_student':
                self.__search_name_student()
            elif cmd == 'random_x':
                self.__add_student_random()
            elif cmd == 'add_disciplina':
                self.__add_discipline()
            elif cmd == 'show_all_disciplina':
                self.__print_all_disciplines()
            elif cmd == 'delete_id_disciplina':
                self.__delete_discipline()
            elif cmd == 'delete_name_disciplina':
                self.__delete_name_discipline()
            elif cmd == 'delete_teacher_disciplina':
                self.__delete_teacher_discipline()
            elif cmd == 'modify_disciplina':
                self.__modify_discipline()
            elif cmd == 'delete_all_disciplina':
                self.__delete_all_disciplina()
            elif cmd == 'search_id_disciplina':
                self.__search_id_disciplina()
            elif cmd == 'search_name_disciplina':
                self.__search_name_disciplina()
            elif cmd == 'search_teacher_disciplina':
                self.__search_teacher_disciplina()
            elif cmd == 'assign_nota':
                self.__add_grade()
            elif cmd == 'show_all_nota':
                self.__print_grades(self.__srv_nota.get_all())
            elif cmd == 'lista_disciplina':
                self.__get_list_for_discipline()
            elif cmd == 'medii':
                self.__create_students_raport()
            elif cmd == "cate_note":
                self.__create_discipline_raport()
            elif cmd == 'exit':
                return False
            else:
                print(colored('Comanda invalida.', 'red'))
