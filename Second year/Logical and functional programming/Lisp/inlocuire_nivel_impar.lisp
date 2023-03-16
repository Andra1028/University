(defun inlocuire (l k niv)
    (cond
        ((and (atom l) (numberp l) (> l k) (= (mod niv 2) 1)) (+ 1 l))
        ((atom l) l)
        (t 
        (
            mapcar #'(lambda (x) (inlocuire x k (+ 1 niv)))l
        )
        )
    )
)

(defun main (l k)
(inlocuire l k 0)
)