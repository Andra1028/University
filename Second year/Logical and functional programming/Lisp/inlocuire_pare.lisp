(defun inlocuire (a)
    (cond
        ((and (atom a) (numberp a) (= (mod a 2) 0)) (+ a 1))
        ((atom a) a)
        (T
           (mapcar #'
                (
                    lambda (x) (inlocuire x)
                )a
            )
        )
    )
)