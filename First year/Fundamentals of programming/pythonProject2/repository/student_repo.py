from domain.entities import Student


class InMemoryRepositoryStudent:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de studenti (i.e. sa ofere un depozit persistent pentru obiecte
        de tip student)

    """

    def __init__(self):
        # student - multimea de studenti pe care o gestionam
        self.__studenti = []

    def find(self, id):
        """
        Cauta studentul cu id dat
        :param id: id dat
        :type id: int
        :return: student cu id dat, None daca nu exista
        :rtype: Student
        """
        for stud in self.__studenti:
            if str(stud.getIDStudent()) == str(id):
                return stud
        return None

    def find_name(self, nume):
        """

        :param nume: Nume dat
        :return: Student cu nume dat, None daca nu exista
        """
        for stud in self.__studenti:
            if stud.getNumeStudent() == nume:
                return stud
        return None

    def store(self, student):
        """
        Adauga un student in lista
        :param student: studentul care se adauga
        :type student: Student
        :return: -; lista de studenti se modifica prin adaugarea studentului dat
        :rtype:
        """
        if self.find(student.getIDStudent()) is not None:
            raise ValueError('Exista deja student cu acest id.')
        self.__studenti.append(student)

    def size(self):
        """
        Returneaza numarul de studenti din multime
        :return: numar studenti existenti
        :rtype:int
        """
        return len(self.__studenti)

    def search_id(self, id):
        stud = self.find(id)
        if stud is None:
            raise ValueError('Nu exista student cu acest id')
        return stud

    def search_name(self, nume):
        stud = self.find_name(nume)
        if stud is None:
            raise ValueError('Nu exista student cu acest nume')
        return stud

    def delete(self, id):
        """
        Sterge un student din lista dupa id
        :return:
        """
        stud = self.find(id)
        if stud is None:
            raise ValueError("Nu exista student cu acest id")
        self.__studenti.remove(stud)
        return stud

    def delete_name(self, nume):
        """
        Sterge un student din lista dupa nume
        :param student: studentul care trebuie eleiminat
        :return:
        """
        stud = self.find_name(nume)
        if stud is None:
            raise ValueError("Nu exista student cu acest nume")
        self.__studenti.remove(stud)
        return stud

    def delete_all(self):
        """
        sterge toti studentii din lista
        :return:
        """
        self.__studenti.clear()

    def update(self, id, updated):
        """
        Modifica studentul dupa id
        :param id: Id student de modifcat
        :param updated: Studentul modificat
        :return:
        """
        stud = self.find(id)
        if stud is None:
            raise ValueError("Nu exista student cu acest id")
        stud.setNumeStudent(updated.getNumeStudent())
        return stud

    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii existenti
        :rtype: list of objects de tip Student
        """
        return self.__studenti


def test_store():
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)

    assert (test_repo.size() == 1)
    stud2 = Student(1, 'Maria')
    test_repo.store(stud2)
    assert (test_repo.size() == 1)


def test_find():
    test_repo = InMemoryRepositoryStudent
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)
    stud = test_repo.find(1)
    assert (stud.getNumeStudent() == 'Ana')
    stud = test_repo.find(7)
    assert (stud is None)


def test_delete_id():
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)
    stud2 = Student(2, 'Maria')
    test_repo.store(stud2)
    test_repo.delete(1)
    assert (test_repo.size() == 1)
    test_repo.delete(4)
    assert (test_repo.size() == 1)


def test_delete_name():
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)
    stud2 = Student(2, 'Maria')
    test_repo.store(stud2)
    test_repo.delete_name('Ana')
    assert (test_repo.size() == 1)
    test_repo.delete('djkshhc')
    assert (test_repo.size() == 1)


def test_search_id():
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)
    stud = test_repo.search_id(1)
    assert (stud.getNumeStudent() == 'Ana')
    stud = test_repo.search_id(10)
    assert (stud is None)


def test_update():
    test_repo = InMemoryRepositoryStudent()
    stud1 = Student(1, 'Ana')
    test_repo.store(stud1)
    updated = Student(1, 'Maria')
    stud = test_repo.update(1, updated)
    assert (stud.getNumeStudent() == 'Maria')


test_find()


class StudentFileRepo:
    def __init__(self, filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca datele din fisier
        :return: lista cu studentii din fisier
        """
        global stud_id
        try:
            f = open(self.__filename, 'r')
        except IOError:
            raise ValueError('eroare')

        studenti = []
        lines = f.readlines()
        n = 1
        for line in lines:
            if n % 2 == 1:
                line = int(line)
                stud_id = line
            else:
                stud_name = line
                a = Student(stud_id, stud_name)
                studenti.append(a)
            n = n + 1
        f.close()
        return studenti

    def __save_to_file(self, studenti_list):
        """
        Salveaza in fisier studentii
        :param studenti_list: lista de studenti
        :return: -
        :rtype: -
        """
        with open(self.__filename, 'w') as f:
            for student in studenti_list:
                student_string = str(student.getIDStudent()) + '\n' + str(student.getNumeStudent()) + '\n'
                f.write(student_string)

    def store(self, student):
        """
        Adauga studentul in lista
        :param student: student de adaugat
        :return: -; lista de studenti se modifica prin adaugarea studentului
        :rtype: -; studentul este adaugat
        """
        all_stud = self.__load_from_file()
        stud = self.find(student.getIDStudent())
        if stud is not None:
            raise ValueError("Exista deja un student cu accest id")
        all_stud.append(student)
        self.__save_to_file(all_stud)

    def find(self, id):
        """
        Cauta studentul cu id dat
        :param id: id dat
        :type id: int
        :return: student cu id dat, None daca nu exista
        :rtype: Student
        """
        for stud in self.__load_from_file():
            if str(stud.getIDStudent()) == str(id):
                return stud
        return None

    def find_name(self, nume):
        """

        :param nume: Nume dat
        :return: Student cu nume dat, None daca nu exista
        """
        for stud in self.__load_from_file():
            if stud.getNumeStudent() == nume:
                return stud
        return None

    def search_id(self, id):
        stud = self.find(id)
        if stud is None:
            raise ValueError('Nu exista student cu acest id')
        return stud

    def search_name(self, nume):
        stud = self.find_name(nume)
        if stud is None:
            raise ValueError('Nu exista student cu acest nume')
        return stud

    def __find_index(self, all_students, id):
        """
        Gaseste pozitia in lista all_students a studentului cu id id
        :param all_studenti: lista de studenti
        :param id: id-ul dat
        :return: pozitia Studentului cu id id in lista data, -1 daca nu exista
        """
        index = -1
        for i in range(len(all_students)):
            if all_students[i].getIDStudent() == id:
                index = i
                break
        return index

    def delete(self, id):
        """
        Sterge student dupa id
        :param id: id-ul dat
        :type id: str
        :return: studentul sters
        :rtype: Student
        """
        all_students = self.__load_from_file()
        index = self.__find_index(all_students, id)
        if index == -1:
            raise ValueError('Studentul nu exista')

        deleted_student = all_students.pop(index)
        self.__save_to_file(all_students)
        return deleted_student

    def update(self, id, modified_student):
        """
        Modifica datele studentului cu id dat
        :param id: id dat
        :type id: str
        :param modified_client: studentul cu datele noi
        :type modified_client: Student
        :return: student modificat
        :rtype: Student
        """
        all_students = self.__load_from_file()
        index = self.__find_index(all_students, id)
        if index == -1:
            raise ValueError('Studentul nu exista')

        all_students[index] = modified_student
        self.__save_to_file(all_students)

        return modified_student

    def get_all_students(self):
        """
        Returneaza o lista cu toti studentii existenti
        :rtype: list of objects de tip Student
        """
        return self.__load_from_file()
