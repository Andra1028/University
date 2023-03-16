(defun substituie(l niv k e)
 (cond
 
 (
 (AND (atom l) (not (equal niv k)))
 l
 )
 (
 (AND (atom l) (equal niv k))
 e
 )
 (
 T
 (mapcar #'(lambda (x) 
 (substituie x (+ niv 1) k e)
 )
 l
 )
 )
 )
)
; main(l, k) = substituie(l,0,k)
; aceasta este functia main
; main(l:list, k:integer)
(defun main(l k e)
 (substituie l -1 k e)
)
