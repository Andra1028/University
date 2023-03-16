(defun elimina (a niv)
    (cond
        ((AND (atom a) (not(equal (mod niv 2) 0))) (list a))
        ((AND (atom a) (not(numberp a))(equal (mod niv 2) 0)) NIL)
        ((AND (atom a) (numberp a)) (list a))
        (T (
                (list 
                    (apply #'append
                        ( 
                            mapcar(
                                     lambda(x)
                                     (
                                         elimina x (+ 1 niv)
                                     ) 
                                )a
                        )
                    )   
                )
            )
        )
    )
)

(defun main(a)
(car(elimina a 0))
)