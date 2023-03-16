(defun nivel_k (a k nivel)
(cond
((AND (atom a) (equal k nivel) )1)
((AND (atom a) (not(equal k nivel)) )0)
(T
(apply #'+
(
    mapcar(
        lambda(x)(nivel_k x k (+ 1 nivel))
    )
    a
)
)
)
)
)

(defun main (a k)
(nivel_k a k -1)
)