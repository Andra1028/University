(defun elimina (l)
    (cond
        ((and (atom l) (numberp l) (= (mod l 3) 0)) nil)
        ((atom l) (list l))
        (T
            (list (apply #'append(mapcar #'(lambda (x) (elimina x))l )))
        )
    )
)