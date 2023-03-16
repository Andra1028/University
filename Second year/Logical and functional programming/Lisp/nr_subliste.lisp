(defun subliste (a nr)
    (cond
        ((and (atom  a) (numberp  a) (= (mod  a 2) 1))  1)
        ((atom a) 0)
        (t 
            (apply #'+(mapcar #'(lambda (x) (subliste x nr))a ))
        )
    )
)

(defun main (a)
    (subliste a 0)
)