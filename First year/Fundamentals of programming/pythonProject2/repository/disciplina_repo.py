from domain.entities import Disciplina


class InMemoryRepositoryDiscipline:
    """
        Clasa creata cu responsabilitatea de a gestiona
        multimea de discipline (i.e. sa ofere un depozit persistent pentru obiecte
        de tip disciplina)

    """
    def __init__(self):
        # discipline - multimea de discipline pe care o gestionam
        self.__discipline = []


    def find(self, id):
        """
        Cauta disciplina cu id dat
        :param id: id dat
        :type id: integer
        :return: disciplina cu id dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__discipline:
            if str(dis.getIDDisciplina()) == str(id):
                return dis
        return None

    def find_name(self, nume):
        """
        Cauta disciplina cu numele dat
        :param nume: nume dat
        :type nume: str
        :return: disciplina cu nume dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__discipline:
            if dis.getNumeDisciplina() == nume:
                return dis
        return None

    def find_teacher(self, prof):
        """
        Cauta disciplina cu profesorul dat
        :param prof: profesorul dat
        :type prof: str
        :return: disciplina cu profesorul dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__discipline:
            if dis.getProfesor() == prof:
                return dis
        return None

    def store(self, disciplina):
        """
        Adauga o disciplina in lista
        :param disciplina: disciplina care se adauga
        :type disciplina: Disciplina
        :return: -; lista de discipline se modifica prin adaugarea disciplinei date
        :rtype:
        """
        dis = self.find(disciplina.getIDDisciplina())
        if dis is not None:
            raise ValueError ("Exista deja o disciplina cu accest id")
        self.__discipline.append(disciplina)

    def size(self):
        """
        Returneaza numarul de discipline din multime
        :return: numar discipline existente
        :rtype:int
        """
        return len(self.__discipline)

    def search_id(self, id):
        dis = self.find(id)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest id')
        return dis

    def search_name(self, nume):
        dis = self.find_name(nume)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest nume')
        return dis

    def search_teacher(self, prof):
        dis = self.find_teacher(prof)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest profesor')
        return dis

    def delete(self, id):
        dis = self.find(id)
        if dis is None:
            raise ValueError("Nu exista disciplina cu accest id")
        self.__discipline.remove(dis)

    def delete_name(self, nume):
        dis = self.find_name(nume)
        if dis is None:
            raise ValueError("Nu exista disciplina cu accest nume")
        self.__discipline.remove(dis)

    def delete_teacher(self, prof):
        dis = self.find_teacher(prof)
        if dis is None:
            raise ValueError("Nu exista disciplina cu accest profesor")
        self.__discipline.remove(dis)

    def get_all_disciplines(self):
        """
        Returneaza o lista cu toate disciplinele existente
        :rtype: list of objects de tip Disciplina
        """
        return self.__discipline

    def delete_all(self):
        """
        sterge toate disciplinele din lista
        :return:
        """
        self.__discipline.clear()

    def update(self, id, updated):
        """
        Modifica disciplina dupa id
        :param id: Id disciplina de modifcat
        :param updated: Disciplina modificata
        :return:
        """
        dis = self.find(id)
        if dis is None:
            raise ValueError ("Nu exista disciplina cu acest id")
        dis.setNumeDisciplina(updated.getNumeDisciplina())
        dis.setProfesor(updated.getProfesor())
        return dis

def test_store():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)

    assert (test_repo.size() == 1)
    dis2 = Disciplina(1, 'biologie', 'Popescu')
    test_repo.store(dis2)
    assert (test_repo.size() == 1)

def test_find():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)
    dis = test_repo.find(1)
    assert(dis.getNumeDisciplina() == 'matematica')
    stud = test_repo.find(7)
    assert(stud is None)


def test_delete_id():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)
    dis2 = Disciplina(2, 'biologie', 'Popescu')
    test_repo.store(dis2)
    test_repo.delete(1)
    assert(test_repo.size() == 1)
    test_repo.delete(4)
    assert(test_repo.size() == 1)

def test_delete_name():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)
    dis2 = Disciplina(1, 'biologie', 'Popescu')
    test_repo.store(dis2)
    test_repo.delete_name('biologie')
    assert(test_repo.size() == 1)
    test_repo.delete('djkshhc')
    assert(test_repo.size() == 1)

def test_search_id():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)
    dis = test_repo.search_id(1)
    assert(dis.getNumeDisciplina() == 'matematica')
    stud = test_repo.search_id(10)
    assert (stud is None)


def test_update():
    test_repo = InMemoryRepositoryDiscipline()
    dis1 = Disciplina(1, 'matematica', 'Ciupala')
    test_repo.store(dis1)
    updated = Disciplina(1, 'biologie', 'Popescu')
    stud = test_repo.update(1,updated)
    assert(stud.getNumeDisciplina() == 'biologie')
    assert (stud.getProfesor() == 'Popescu')


class DisciplinaFileRepo:
    def __init__(self, filename):
        self.__filename = filename

    def __load_from_file(self):
        """
        Incarca datele din fisier
        :return: lista cu disciplinele din fisier
        """

        global dis_id, dis_name
        try:
            f = open(self.__filename, 'r')
        except IOError:
            raise ValueError('eroare')

        discipline = []
        lines = f.readlines()
        n = 1
        for line in lines:
            if n%3 == 0:
                dis_id = line
                dis_id = int(dis_id)
            elif n%3 == 1:
                dis_name = line
            else:
                dis_prof = line
                a = Disciplina(dis_id, dis_name, dis_prof)
                discipline.append(a)
        f.close()
        return discipline

    def __save_to_file(self, disciplina_list):
        """
        Salveaza in fisier disciplinele
        :param disciplina_list: lista de discipline
        :return: -
        :rtype: -
        """
        with open(self.__filename, 'w') as f:
            for disciplina in disciplina_list:
                disciplina_string = str(disciplina.getIDDisciplina()) + '\n' + str(disciplina.getNumeDisciplina()) + '\n' + str(
                    disciplina.getProfesor()) + '\n'
                f.write(disciplina_string)

    def store(self, disciplina):
        """
        Adauga disciplina in lista
        :param disciplina: disciplina de adaugat
        :return: -; lista de discipline se modifica prin adaugarea disciplinei
        :rtype: -; disciplina este adaugata
        """
        all_dis = self.__load_from_file()
        dis = self.find(disciplina.getIDDisciplina())
        if dis is not None:
            raise ValueError("Exista deja o disciplina cu accest id")
        all_dis.append(disciplina)
        self.__save_to_file(all_dis)

    def find(self, id):
        """
        Cauta disciplina cu id dat
        :param id: id dat
        :type id: str
        :return: disciplina cu id dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__load_from_file():
            if str(dis.getIDDisciplina()) == str(id):
                return dis
        return None

    def find_name(self, nume):
        """
        Cauta disciplina cu numele dat
        :param nume: nume dat
        :type nume: str
        :return: disciplina cu nume dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__load_from_file():
            if dis.getNumeDisciplina() == nume:
                return dis
        return None

    def find_teacher(self, prof):
        """
        Cauta disciplina cu profesorul dat
        :param prof: profesorul dat
        :type prof: str
        :return: disciplina cu profesorul dat, None daca nu exista
        :rtype: Disciplina
        """
        for dis in self.__load_from_file():
            if dis.getProfesor() == prof:
                return dis
        return None

    def search_id(self, id):
        dis = self.find(id)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest id')
        return dis

    def search_name(self, nume):
        dis = self.find_name(nume)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest nume')
        return dis

    def search_teacher(self, prof):
        dis = self.find_teacher(prof)
        if dis is None:
            raise ValueError('Nu exista disciplina cu acest profesor')
        return dis

    def __find_index(self, all_discipline, id):
        """
        Gaseste pozitia in lista all_discipline a disciplinei cu id id
        :param all_studenti: lista de discipline
        :param id: id-ul dat
        :return: pozitia disciplinei cu id id in lista data, -1 daca nu exista
        """
        index = -1
        for i in range(len(all_discipline)):
            if all_discipline[i].getIDDisciplina() == id:
                index = i
                break
        return index

    def delete(self, id):
        """
        Sterge disciplina dupa id
        :param id: id-ul dat
        :type id: str
        :return: disciplina sters
        :rtype: Disciplina
        """
        all_discipline = self.__load_from_file()
        index = self.__find_index(all_discipline, id)
        if index == -1:
            raise ValueError('Disciplina nu exista')

        deleted_dis = all_discipline.pop(index)
        self.__save_to_file(all_discipline)
        return deleted_dis

    def update(self, id, modified_dis):
        """
        Modifica datele disciplinei cu id dat
        :param id: id dat
        :type id: str
        :param modified_client: disciplina cu datele noi
        :type modified_client: Disciplina
        :return: disciplina modificat
        :rtype: Disciplina
        """
        all_dis = self.__load_from_file()
        index = self.__find_index(all_dis, id)
        if index == -1:
            raise ValueError('Disciplina nu exista')

        all_dis[index] = modified_dis
        self.__save_to_file(all_dis)

        return modified_dis

    def get_all_disciplines(self):
        """
        Returneaza o lista cu toate disciplinele existente
        :rtype: list of objects de tip Disciplina
        """
        return self.__load_from_file()

