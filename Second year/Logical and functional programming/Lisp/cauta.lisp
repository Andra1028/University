(defun cauta (a x niv)
    (cond
        ((and (atom a) (equal a x) (= (mod niv 2) 0)) (list T))
        ((atom a) nil)
        (t (mapcan #'(lambda (y) (cauta y x (+ 1 niv)))a))
    )
)

(defun main (a x)
(cauta a x -1)
)