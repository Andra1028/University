(defun cauta (a e)
    (cond
        ((NULL a) nil)
        ((and (atom (car a)) (equal (car a) e)) T)
        ((listp (car a)) (OR (cauta (car a) e) (cauta (cdr a) e)) )
        (t (cauta (cdr a) e))
    )
)

(defun cale (a e)
    (cond
        ((and (atom a) (not (equal a e))) NIL)
        ((and (atom a) (equal a e)) e)
        ((AND (listp a) (cauta a e))
        (append (list(car a)) (mapcan #'(lambda (x) (cale x e) )a )) 
 )
 )
)



