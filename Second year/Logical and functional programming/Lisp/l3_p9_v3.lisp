;Definiti o functie care substituie un element E prin elementele unei liste L1 la toate nivelurile unei liste date L.

;liniarireza (l: lista neliniara)
;functie care transforma o lista neliniara in lista liniara
(defun liniarizeaza(l)
    (cond  
        ((atom l) (list l))
        (t (apply 'append(mapcar #'liniarizeaza l)))
    )
)


;substituie (l: lista neliniara, e:element, l1:lista liniara)
;inlocuieste aparitiile elementului e in lista l cu toate elementele din l1
(defun substituie(l e l1)
    (cond
        ((and (atom l) (equal l e)) l1)
        ((atom l) (list l))
        (T (list (apply #'append(mapcar #'(lambda (ls) (substituie ls e l1)) l))))
    )
)

;substituie_stat (l:lista neliniara, e:element , l1:lista neliniara)
;apeleaza functia substituie folosind liniarizarea lui l1
(defun substituie_start(l e l1)
    (car (substituie l e (liniarizeaza l1)))
)
