;pun niv=0 initial ca lista mare sa fie pe nivelul 1
(defun suma_niv_impare(l niv)
    (cond
        ((AND (numberp l) (equal 1 (mod niv 2))) l)
        ((atom l) 0)
        (t (apply '+ (mapcar #'(lambda (x)
                                    (suma_niv_impare x (+ niv 1))
                                ) l
                    )
            )
        )
    )
)

(defun verificare(l)
    (cond
        ((equal (mod (suma_niv_impare l 0) 2) 0) t)
        (t nil)
    )
)

(defun main(l)
    (cond
        ((atom l) 0)
        ((verificare l) (+ 1 (apply '+ (mapcar #'main l))))
        (t  (apply '+ (mapcar #'main l)))
    )
)