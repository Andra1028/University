

;liniar (l :lista)
;testeaza daca o lista e liniara
(defun liniar (l)
    (cond 
        ((null l) t)
        ((listp (car l)) nil)
        (t (liniar (cdr l)))
    )
)


;lungime (l :lista liniara)
;calculeaza lungime unei liste liniare
(defun lungime (l)
    (cond
        ((null l) 0)
        (t (+ 1 (lungime (cdr l))))
    )
)


;sterge (l :lista neliniara)
; sterge toate sublistele liniare de lungime para
(defun sterge (l)
    (cond 
        ((null l) nil)
        ((and (listp (car l)) (liniar (car l)) (= ( mod (lungime (car l)) 2) 0)) (sterge (cdr l)))
        ((atom (car l)) (append (list (car l)) (sterge (cdr l))))
        (t (append (list (sterge (car l))) (sterge (cdr l))))
    )
)