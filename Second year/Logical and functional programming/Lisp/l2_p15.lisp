;15. Sa se construiasca lista nodurilor unui arbore de tipul (1) parcurs in postordine.

;defun parcurg_st arb:lista nv:intreg nm:intreg
;arb: lista pentru arbore de tipul 1
;nv: numarul varfurilor
;nm: umarul muchiilor
;parcurge subarborele stang

(defun parcurg_st (arb nv nm)
(cond
((null arb ) nil)
((= nv (+ 1 nm)) nil)
(t (cons (car arb) (cons (cadr arb) (parcurg_st (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))))
)
)

(defun stang (arb)
(parcurg_st(cddr arb) 0 0)
)


;defun parcurg_dr arb:lista nv:intreg nm:intreg
;arb: lista pentru arbore de tipul 1
;nv: numarul varfurilor
;nm: umarul muchiilor
;parcurge subarborele drept

(defun parcurg_dr (arb nv nm)
(cond
((null arb) nil)
((= nv (+ 1 nm)) arb)
(t (parcurg_dr (cddr arb) (+ 1 nv) (+ (cadr arb) nm)))
)
)

(defun drept (arb)
(parcurg_dr(cddr arb) 0 0)
)

;defun postordine arb: lista
;arb: lista arborelui de tipul 1
;parcurge subarborele stang, subarborele drept si apoi radacina
(defun postordine(arb)
(cond
( (null arb) nil)
(t (append(append(postordine(stang arb)) (postordine(drept arb))) (list(car arb))))
)
)