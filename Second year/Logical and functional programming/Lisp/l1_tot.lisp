;1
;a) Sa se insereze intr-o lista liniara un atom a dat dupa al 2-lea, al 4-lea, al 6-lea,....element

(defun inserare (l e c)
    (cond
        ((null l) nil)
        ((= (mod c 2) 0) (cons (car l) (cons e (inserare (cdr l) e (+ 1 c)))))
        (t (cons (car l) (inserare (cdr l) e (+ 1 c))))
    )
)

;b) Definiti o functie care obtine dintr-o lista data lista tuturor atomilor care apar, pe orice nivel, 
;dar in ordine inversa. De exemplu: (((A B) C) (D E)) --> (E D C B A)

(defun inversare (l)
    (cond 
        ((null l) nil)
        ((atom (car l)) (append (inversare (cdr l)) (list (car l))))
        (t (append (inversare (cdr l)) (inversare (car l))))
    )
)

;c) Definiti o functie care intoarce cel mai mare divizor comun al numerelor dintr-o lista neliniara.

(defun cmmdc (a b)
    (cond 
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((= b 0) a)
        ((= a 0) b)
        ((= a b) a)
        ((> a b) (cmmdc b (- a b)))
        ((< a b) (cmmdc a (- b a)))
    )
)

(defun cmmdcL(l)
    (cond
        ((null l) nil)
        ((atom (car l)) (cmmdc (car l) (cmmdcL (cdr l))))
        (t (cmmdc (cmmdcL (car l)) (cmmdcL (cdr l))))
    )
)

;d) Sa se scrie o functie care determina numarul de aparitii ale unui atom dat intr-o lista neliniara.

(defun aparitii (l e)
    (cond 
        ((null l) 0)
        ((and (atom (car l)) (equal e (car l))) (+ 1 (aparitii (cdr l) e )))
        ((atom (car l)) (aparitii (cdr l) e))
        (t (+ (aparitii (car l) e) (aparitii (cdr l) e)))
    )
)

;2
;a)Definiti o functie care selecteaza al n-lea element al unei liste, sau NIL, daca nu exista.

(defun pozitie (l c n)
    (cond 
        ((null l) nil)
        ((= c n) (car l))
        (t (pozitie (cdr l) (+ 1 c) n))
    )
)

;b)Sa se construiasca o functie care verifica daca un atom e membru al unei liste nu neaparat liniara.


(defun exista (l e)
    (cond 
        ((null l) nil)
        ((and (atom (car l)) (equal (car l) e)) t)
        ((atom (car l)) (exista (cdr l) e))
        (t (or (exista (car l) e) (exista (cdr l) e)))
    )
)

;c)Sa se construiasca lista tuturor sublistelor unei liste. Prin sublista se intelege fie lista insasi, 
;fie un element de pe orice nivel, care este lista. Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 
;( (1 2 (3 (4 5) (6 7)) 8 (9 10)) (3 (4 5) (6 7)) (4 5) (6 7) (9 10) ).

(defun construire (l)
    (cond 
        
        ( (null l) () )
        ( (listp (car l)) (append (list (car l)) (construire (car l)) (construire (cdr l))) )
        ( T (construire (cdr l)) )
    
    )
)

(defun main_const (l)
    (append (list l) (construire l))
)

;d)Sa se scrie o functie care transforma o lista liniara intr-o multime.


(defun multime(l)
    (cond 
    ((null l) nil)
    ((> (aparitii l (car l)) 1) (multime (cdr l)))
    (t (append (list (car l)) (multime (cdr l))))
    )
)

;3
;a)Definiti o functie care intoarce produsul a doi vectori.

(defun produs_vectori (a b)
    (cond
        ((null a) 0)
        (t (+ (* (car a) (car b)) (produs_vectori (cdr a) (cdr b))))
    )
)

;b) Sa se construiasca o functie care intoarce adancimea unei liste.

(defun maxim(a b)
  (cond
    ((> a b) a)
    (t b)
  )
)

(defun adancime (l a)
    (cond 
        ((null l) a)
        ((listp (car l)) (maxim (adancime (car l) (+ 1 a)) (adancime (cdr l) a)))
        (t (adancime (cdr l) a))
    )
)

;c)Definiti o functie care sorteaza fara pastrarea dublurilor o lista liniara.

(defun minim (l m)
    (cond 
        ((null l) m)
        ((< (car l) m) (minim (cdr l) (car l)))
        (t (minim (cdr l) m))    
    )
)

(defun elimina (l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) (elimina (cdr l) e))
        (t (append (list (car l)) (elimina (cdr l) e)))
    )
)

(defun sortare (l)
(cond 
    ((null l) nil)
    (t (append (list (minim l 99999)) (sortare (elimina l (minim l 99999)))))
)
)

;d)Sa se scrie o functie care intoarce intersectia a doua multimi.

(defun exista_liniar (l e)
(cond 
    ((null l) nil)
    ((equal (car l) e) t)
    (t (exista_liniar (cdr l) e))
)
)

(defun intersectie (a b)
(cond 
    ((null a) nil)
    ((exista b (car a)) (append (list (car a)) (intersectie (cdr a) b)))
    (t (intersectie (cdr a) b))
)
)

;4
;a)Definiti o functie care intoarce suma a doi vectori.

(defun adunare (a b)
(cond 
    ((null a) nil)
    (t (append (list (+ (car a) (car b))) (adunare (cdr a) (cdr b))))
)
)

;b) Definiti o functie care obtine dintr-o lista data lista tuturor atomilorcare apar, pe orice nivel, 
;dar in aceeasi ordine. De exemplu: (((A B) C) (D E)) --> (A B C D E)


(defun liniar (l)
    (cond
        ((null l) nil)
        ((atom (car l)) (append (list (car l)) (liniar (cdr l))))
        (t (append (liniar (car l)) (liniar (cdr l))))
    )
)

;c)Sa se scrie o functie care plecand de la o lista data ca argument, inverseaza numai secventele continue de atomi. 
;Exemplu: (a b c (d (e f) g h i)) ==> (c b a (d (f e) i h g))

(defun inversare_secvente (l aux)
    (cond
       ((null l) aux)

    ;daca e lista adaug la final de aux, am terminat cu actuala inversare si lipesc car l inversat si cdr l inversat (appenduite)
    ((listp (car l)) (append aux (append (list (inversare_secvente (car l) nil)) (inversare_secvente (cdr l) nil))))

    ;adaug practic la inceput de aux -> 1 2 3 va fi 3 2 1 ca adaug la inceput de fiecare data
    (t (inversare_secvente (cdr l) (append (list (car l)) aux)))
    )
)

;d)Sa se construiasca o functie care intoarce maximul atomilor numerici dintr-o lista, de la nivelul superficial.

(defun maxim(a b)
    (cond
        ((not (numberp a)) b)
        ((not (numberp b)) a)
        ((> a b) a)
        (t b)
    )
)

(defun maxim_superficial (l)
(cond 
    ((null l) 0)
    ((numberp (car l)) (maxim (car l) (maxim_superficial (cdr l))))
    (t (maxim_superficial (cdr l)))
)
)

;5
;a)Definiti o functie care interclaseaza cu pastrarea dublurilor doua liste liniare sortate.

(defun interclasare (a b)
(cond 
    ((and (null a) (null b)) nil)
    ((null a) b)
    ((null b) a)
    ((< (car a) (car b)) (append (list (car a)) (interclasare (cdr a) b)))
    ((> (car a) (car b)) (append (list (car b)) (interclasare a (cdr b))))
    ((= (car a) (car b)) (append (list (car a)) (list (car b)) (interclasare (cdr a) (cdr b))))
)
)

;b)Definiti o functie care substituie un element E prin elementele unei liste L1 la toate nivelurile unei liste date L.

(defun substituie (l e l1)
    (cond 
        ((null l) nil)
        ((and (atom (car l)) (equal (car l) e)) (append (liniar l1) (substituie (cdr l) e l1)))
        ((atom (car l)) (append (list (car l)) (substituie (cdr l) e l1)))
        (t (cons (substituie (car l) e l1) (substituie (cdr l) e l1)))
    )
)

;c)Definiti o functie care determina suma a doua numere in reprezentare de lista si calculeaza numarul zecimal 
;corespunzator sumei.

(defun invers (l)
(cond
    ((null l) nil)
    (t (append (invers (cdr l)) (list (car l))))
)
)

(defun sum (a b)
(cond 
    ((and (null a) (null b)) nil)
    ((null a) b)
    ((null b) a)
    (t (append (list (+ (car a) (car b))) (sum (cdr a) (cdr b))))
)
)

(defun corect (l aux)
(cond
    ((and (null l) (> aux 0)) (list aux))
    ((null l) nil)
    ((> (car l) 9) (append (list (+ aux (- (car l) 10))) (corect (cdr l) 1)))
    (t (append (list (+ aux (car l))) (corect (cdr l) 0)))
)
)

(defun suma (a b)
     (invers (corect (sum (invers a) (invers b)) 0))
)

;d)Definiti o functie care intoarce cel mai mare divizor comun al numerelor dintr-o lista liniara

(defun cmmdc_liniar (l)
(cond 
    ((null l) nil)
    (t (cmmdc (car l) (cmmdc_liniar (cdr l))))
)
)

;6
;a) Sa se scrie de doua ori elementul de pe pozitia a n-a a unei liste liniare. De exemplu, pentru
; (10 20 30 40 50) si n=3 se va produce (10 20 30 30 40 50).

(defun douaori (l n c)
    (cond 
        ((null l) nil)
        ((= n c) (append (list (car l)) (list (car l)) (douaori (cdr l) n (+ 1 c))))
        (t (append (list (car l)) (douaori (cdr l) n (+ 1 c))))
    )
)

;b) Sa se scrie o functie care realizeaza o lista de asociere cu cele doua liste pe care le primeste. 
;De ex: (A B C) (X Y Z) --> ((A.X) (B.Y) (C.Z)).

(defun asociere (a b)
(cond 
    ((null a) nil)
    (t (append (list (cons (car a) (car b))) (asociere (cdr a) (cdr b))))
)
)

;c) Sa se determine numarul tuturor sublistelor unei liste date, pe orice  nivel. Prin sublista se intelege fie
;lista insasi, fie un element de pe orice nivel, care este lista. Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 
;5 (lista insasi, (3 ...), (4 5), (6 7), (9 10)).

(defun nr_sublista (l)
(cond 
    ((null l) 1)
    ((listp (car l)) (+ (nr_sublista (car l)) (nr_sublista (cdr l))))
    (t (nr_sublista (cdr l)))
)
)

;d)Sa se construiasca o functie care intoarce numarul atomilor dintr-o lista, de la nivel superficial.
  ;--asta e prea simpla


;7
;a)Sa se scrie o functie care testeaza daca o lista este liniara. - tot simpla


;b)Definiti o functie care substituie prima aparitie a unui element intr-o lista data.

(defun inlocuieste (l e)
    (cond
        ((null l) nil)
        ((atom (car l)) (append (list e) (cdr l)))
        (t (append (list (car l))  (inlocuieste (cdr l) e)))
    )
)

;c) Sa se inlocuiasca fiecare sublista a unei liste cu ultimul ei element.Prin sublista se intelege element de 
;pe primul nivel, care este lista. Exemplu: (a (b c) (d (e (f)))) ==> (a c (e (f))) ==> (a c (f)) ==> (a c f)
;(a (b c) (d ((e) f))) ==> (a c ((e) f)) ==> (a c f)

(defun inv (l)
    (cond 
        ((null l) nil)
        ((atom (car l)) (append (inv (cdr l)) (list (car l))))
        (t (append (inv (cdr l))(list (inv (car l)))))
    )
)


(defun primul (l)
    (cond
        ((null l) nil)
        ((atom (car l)) (car l))
        (t (primul (car l)))
    )
)

(defun ia_primul_sub (l)
(cond
    ((null l) nil)
    ((atom (car l)) (append (list (car l)) (ia_primul_sub (cdr l))))
    (t (append (list (primul (car l))) (ia_primul_sub (cdr l))))
)
)

(defun ultim (l)
(inv (ia_primul_sub (inv l)))
)

;d) Definiti o functie care interclaseaza fara pastrarea dublurilor doua liste liniare sortate. -usor


;8
;a)Sa se elimine elementul de pe pozitia a n-a a unei liste liniare. -usor
;b)Definiti o functie care determina succesorul unui numar reprezentat cifracu cifra intr-o lista.
; De ex: (1 9 3 5 9 9) --> (1 9 3 6 0 0) - poti folosi suma a doua numere e mai sus
;c)Sa se construiasca multimea atomilor unei liste.Exemplu: (1 (2 (1 3 (2 4) 3) 1) (1 4)) ==> (1 2 3 4)
;  -functia de liniarizare si functia de multime
;d)Sa se scrie o functie care testeaza daca o lista liniara este o multime. - cu nr_aparitii

;9
;a)Sa se scrie o functie care intoarce diferenta a doua multimi.
   ;-- doua multimi a si b: daca nr de aparitii ale elem din b in a e 0 atunci se pune in lista
;b)Definiti o functie care inverseaza o lista impreuna cu toate sublistele sale de pe orice nivel.
   ;--inv de la prob 7c
;c)Dandu-se o lista, sa se construiasca lista primelor elemente ale tuturor elementelor lista ce au un numar
; impar de elemente la nivel superficial. Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10 11)) => (1 3 9).
;   --ca la 7 c doar ca fara inversare
;d)Sa se construiasca o functie care intoarce suma atomilor numerici dintr-o lista, de la nivelul superficial. - usor

;10
;a)Sa se construiasca o functie care intoarce produsul atomilor numerici dintr-o lista, de la nivelul superficial. -usor
;b)Sa se scrie o functie care, primind o lista, intoarce multimea tuturor perechilor din lista. 
;De exemplu: (a b c d) --> ((a b) (a c) (a d)(b c) (b d) (c d))

(defun formare (l a)
(cond 
    ((null l) nil)
    (t  (append (list (append (list a) (list (car l)))) (formare (cdr l) a) ))
)
)

(defun perechi (l)
(cond 
    ((null l) nil)
    (t (append (formare (cdr l) (car l)) (perechi (cdr l))))
)
)

;c)Sa se determine rezultatul unei expresii aritmetice memorate in preordine pe o stiva. Exemple:
;(+ 1 3) ==> 4 (1 + 3)
;(+ * 2 4 3) ==> 11 ((2 * 4) + 3)
;(+ * 2 4 - 5 * 2 2) ==> 9 ((2 * 4) + (5 - (2 * 2)

(defun fa_operatie(semn nr1 nr2)
    (cond
        ((equal semn '+) (+ nr1 nr2))
        ((equal semn '*) (* nr1 nr2))
        ((equal semn '-) (- nr1 nr2))
    )
)

(defun operatie_mica(l)
    (cond
        ((equal (cdr l) nil) (car l))
        ((AND (not (numberp (car l))) (numberp (cadr l)) (numberp (caddr l)) ) (  append  (list (fa_operatie (car l) (cadr l) (caddr l)))  (cdddr l)  ))
        (t (append (list (car l)) (operatie_mica (cdr l)) ))
    )
)

(defun expresie_preordine(l)
    (cond
        ((null (cdr l)) (car l))
        (t (expresie_preordine (operatie_mica l) ))
    )
)

;d)Definiti o functie care, dintr-o lista de atomi, produce o lista de  perechi (atom n), unde atom apare in 
;lista initiala de n ori. De ex: (A B A B A C A) --> ((A 4) (B 2) (C 1)).
;  -fac cu numarul de aparitii o pereche si sterg din restul listei elemetul curent

;11
;a)Sa se determine cel mai mic multiplu comun al valorilor numerice dintr-o lista neliniara.
;-- folosim cmmdc pe lista neliniara , calculam produsul pe lista neliniara si impartim prod la cmmdc
;b)Sa se scrie o functie care sa testeze daca o lista liniara formata din numere intregi are aspect de 
;"munte"(o secvență se spune ca are aspect de "munte" daca elementele cresc pana la un moment dat, apoi descresc.
; De ex. 10 18 29 17 11 10).
;1 creste , -1 descreste 
;v maxim
(defun verif (l aux v)
    (cond 
        ((null (cdr l)) t)
        ((and (< (car l) (cadr l)) (= aux 1)) (verif (cdr l) 1 v))
        ((and (> (car l) (cadr l)) (not (= v (car l))) (= aux -1)) (verif (cdr l) -1 v))    
        ((and (> (car l) (cadr l)) (= v (car l)) (= aux 1)) (verif (cdr l) -1 v))
    )
)

(defun munte (l)
(verif l 1 (maxim_superficial l))
)

;c)Sa se elimine toate aparitiile elementului numeric maxim dintr-o lista neliniara.
; -- facem functie de max lista neliniara, folosim functia de elimina

;d)Sa se construiasca o functie care intoarce produsul atomilor numerici pari dintr-o lista, de la orice nivel.
;formam o lista liniara cu atomii pari di facem produsul

;12
;a)Definiti o functie care intoarce produsul scalar a doi vectori. -am mai facut
;b)Sa se construiasca o functie care intoarce maximul atomilor numerici dintr-o lista, de la orice nivel. -usor
;c)Sa se scrie o functie care intoarce lista permutarilor unei liste date.

;inseram pe poz n un elem
(defun varianta (l)
(append (cdr l) (list (car l)))
)

(defun lungime (l)
(cond 
    ((null l) 0)
    (t (+ 1 (lungime (cdr l))))
)
)

(defun permutari (l len)
(cond 
    ((< len (lungime l))(cons (varianta l) (permutari (varianta l) (+ len 1))))
)
)

;d)Sa se scrie o functie care intoarce T daca o lista are numar par de elemente pe primul nivel si NIL in 
;caz contrar, fara sa se numere elementele listei.

(defun lungime_para (l r)
(cond 
    ((null l) r)
    ((equal r t)(lungime_para (cdr l) nil))
    (t (lungime_para (cdr l) t))
)
)

(defun apelare (l)
(lungime_para l t)
)

;13
;a) Sa se intercaleze un element pe pozitia a n-a a unei liste liniare. -usor
;b)Sa se construiasca o functie care intoarce suma atomilor numerici dintr-o lista, de la orice nivel. -usor
;c)Sa se scrie o functie care intoarce multimea tuturor sublistelor unei liste date. Ex: Ptr. 
;lista ((1 2 3) ((4 5) 6)) => ((1 2 3) (4 5) ((4 5) 6)) - am mai facut
;d)Sa se scrie o functie care testeaza egalitatea a doua multimi, fara sa se faca apel la diferenta a doua multimi.
;-fac numarul de aparitii ale elem din a in b, daca da 0 opresc

;14
;a)Dandu-se o lista liniare, se cere sa se elimine elementele din N in N. 

(defun elimina_n (l n c)
(cond 
    ((null l) nil)
    ((= (mod c n) 0) (elimina_n (cdr l) n (+ 1 c)))
    (t (append (list (car l)) (elimina_n (cdr l) n (+ 1 c))))
)
)

;b)Sa se scrie o functie care sa testeze daca o lista liniara formata din numere intregi are aspect de 
;"vale"(o secvență se spune ca are aspect de "vale" daca elementele descresc pana la un moment dat, apoi
; cresc. De ex. 10 8 6 17 19 20).
; -- ca la munte da pe invers conditiile

;c)Sa se construiasca o functie care intoarce minimul atomilor numerici dintr-o lista, de la orice nivel. - usor

;d)Sa se scrie o functie care sterge dintr-o lista liniara toate aparitiile elementului maxim numeric.
; --usor, folosim functia de elimina cu parametru functia maxim

;15
;a) Sa se scrie o functie care intoarce reuniunea a doua multimi.
;-daca nr de aparitii ale unui elem din a in b e >= 1 adaugam in lista o singura data si eliminam din b, daca e 0 adaugam 
;in lista si adaugam ce a mai ramas  din b
;b)Sa se construiasca o functie care intoarce produsul atomilor numerici dintr-o lista, de la orice nivel. -usor
;c)Definiti o functie care sorteaza cu pastrarea dublurilor o lista liniara.
; -- facem o functie care elimina numai prima aparitie a unui elem si o punem in sortare

(defun elimin_prima (l e)
    (cond 
        ((null l) nil)
        ((= (car l) e) (cdr l))
        (t (append (list (car l)) (elimin_prima (cdr l) e)))
    )
)

;d)Definiti o functie care construiește o listă cu pozițiile elementului minim dintr-o listă liniară numerică.

(defun poz_elem (l e c)
    (cond
        ((null l) nil)
        ((equal (car l) e) (append (list c) (poz_elem (cdr l) e (+ 1 c))))
        (t (poz_elem (cdr l) e (+ 1 c)))
    )
)

(defun poz_min (l)
    (poz_elem l (minim l 9999) 1)
)