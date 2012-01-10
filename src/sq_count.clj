(ns sq_count)

;;; you can use the function "count" to obtain the number
;;; of element in a sequence. Implement your own version of
;;; count

(defn my-count [coll]
  (reduce + (for [x coll] 1)))