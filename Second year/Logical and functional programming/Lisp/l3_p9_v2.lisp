(defun liniarizeaza(l)
    (cond  
        ((atom l) (list l))
        (t (apply 'append(mapcar #'liniarizeaza l)))
    )
)


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

(defun inlocuire2 (a e l)
  (cond
    ((and (atom a) (= a e))
     (cond
      ((atom l) (list l))
      (t (apply 'append(mapcar #'(lambda (x) (inlocuire a e x))l )))))
    ((atom a) a)
    (T (mapcan #'(lambda (x) (inlocuire x e l)) a))))


(defun main (a e l)
(inlocuire a e (liniarizeaza l))
)