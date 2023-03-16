;(defun inlocuire(a niv)
;    (cond
;        ((AND (atom a) (= (mod niv 2) 0) (numberp a)) (list (+ a 1)))
;        ((atom a) (list a))
;        (T 
;            (list (apply #'append(mapcar #'(lambda (x) (inlocuire a (+ 1 niv)))a )) )
;        )
;    )
;)

;(defun main(a)
 ;  (car (inlocuire a 0))
;)

(DEFUN noduriNumNivPare(arb nivel)
    (COND
    ((and (atom arb) (= (mod nivel 2) 0) (numberp arb)) (list (+ arb 1)))
    ((atom arb) (list arb))
    (t (list (apply #'append(mapcar #'(lambda (a) (noduriNumNivPare a (+ 1 nivel))) arb))))
    )
)

(defun main_noduriNumNivPare(arb)
    (car (noduriNumNivPare arb 0))
)