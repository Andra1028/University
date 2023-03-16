;1. Se da un arbore. Sa se afiseze calea de la radacina pana la un nod x dat.

(defun apare (l x)
(cond 
    ((null l) nil)
    ((equal (car l) x) t)
    ((listp (car l)) (or (apare (car l) x) (apare (cdr l) x)))
    (t (apare (cdr l) x))
)
)

(defun cale (l x)
    (cond
        ((null l) nil)
        ((equal (car l) x) (list x))
        ((apare (cadr l) x) (append (list (car l)) (cale (cadr l) x)))
        ((apare (caddr l) x) (append (list (car l)) (cale (caddr l) x)))
    )
)

;2. Sa se tipareasca lista nodurilor de pe nivelul k dintr-un arbore.

(defun nivel_k (l k c)
(cond 
    ((null l) nil)
    ((= c k) (list (car l)))
    (t (append (nivel_k (cadr l) k (+ 1 c)) (nivel_k (caddr l) k (+ 1 c))))
)
)


;3.Se da un arbore. Sa se precizeze numarul de niveluri din arbore.

(defun maxim (a b)
(cond
    ((> a b) a)
    ((<= a b) b)
)
)

(defun nr_niveluri (l)
(cond
    ((null l) 0)
    (t (maxim (+ 1 (nr_niveluri (cadr l))) (+ 1 (nr_niveluri (caddr l)))))
)
)

;5.Sa se intoarca adancimea la care se afla un nod intr-un arbore.

(defun adancime (l n)
    (cond
        ((null l) nil)
        ((equal (car l) n) 0)
        ((apare (cadr l) n) (+ 1 (adancime (cadr l) n)))
        ((apare (caddr l) n) (+ 1 (adancime (caddr l) n)))
    )
)

;6.  Sa se construiasca lista nodurilor unui arbore de tipul (1) parcurs in inordine.

(defun inordine (l)
(cond
    ((null l) nil)
    ((null (cadr l)) (list (car l)))
    (t (append (inordine (cadr l)) (list (car l)) (inordine (caddr l))))
)
)

;11.  Se da un arbore de tipul (2). Sa se afiseze nivelul (si lista corespunza- toare a nodurilor) avand
; numar maxim de noduri. Nivelul rad. se considera 0.

(defun lungime(l)
    (cond
        ((null l) 0)
        (t (+ 1 (lungime (cdr l))))
    )
)

;incep cu i de la 0
(defun nivel_maxim(l i)
    (cond
        ;daca am trecut deja de nivelul maxim
        ((= (lungime (nivel_k l i 0)) 0) 0)

        ((> (lungime (nivel_k l i 0)) (lungime (nivel_k l (nivel_maxim l (+ i 1)) 0))) i)
        (t (nivel_maxim l (+ i 1)))
    )
)

(defun noduri_nivel_maxim(l)
    (cons (list (nivel_maxim l 0)) (list (nivel_k l (nivel_maxim l 0) 0)))
)


;12 Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in preordine.


(defun preordine (l)
(cond
    ((null l) nil)
    (t (append (list (car l)) (preordine (cadr l)) (preordine (caddr l))))
)
)

;14 Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in postordine.

(defun postordine (l)
(cond
    ((null l) nil)
    (t (append (postordine (cadr l)) (postordine (caddr l)) (list (car l))))
)
)

;16  Sa se decida daca un arbore de tipul (2) este echilibrat (diferenta dintreadancimile celor 2 subarbori
; nu este mai mare decat 1).

(defun dif (a b)
(cond
    ((> a b) (- a b))
    ((<= a b) (- b a))
)
)


(defun adancime_arb (l)
    (cond
        ((null l) 0)
        (t (maxim (+ 1 (adancime_arb (cadr l))) (+ 1 (adancime_arb (caddr l)))))
    )
)

(defun echilibrat (l)
    (cond
        ((<= (dif (adancime_arb (cadr l)) (adancime_arb (caddr l))) 1) t)
    )
)
