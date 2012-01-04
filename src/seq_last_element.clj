(ns seq-last-element)

;;; you can use the function "last" to obtain the last
;;; element of a sequence. Implement your own version of last.

(defn last-1 
  "This versions uses the reverse and the first function"  
  [coll]
  (first (reverse coll)))

(defn last-2 
  "This version uses recursion to reach the last element"
  [coll]
  (if (next coll)
    (recur (next coll) )
    (first coll)))
