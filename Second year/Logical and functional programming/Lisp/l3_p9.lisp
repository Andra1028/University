(defun inlocuire (a e l)
    (cond
        ((and (atom a) (= a e)) l)
        ((atom a) a)
        (T
            (mapcar #'
                (
                    lambda (x) (inlocuire x e l)
                )a
            )
        )
    )
)