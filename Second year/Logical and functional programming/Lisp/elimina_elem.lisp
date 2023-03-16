(defun elimina (l e)
    (cond
        ((and (atom l) (equal l e)) nil)
        ((atom l) (list l))
        (t 
           (list (apply #'append(mapcar #'(lambda (x) (elimina x e)) l)))
        )
    )
)

(defun main ( l e)
(car (elimina l e))
)

