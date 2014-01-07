(ns people-matcher-web-application.router.router-util)
(defn empty-str [str]
  (or (= nil str) (= "" str)))
(defn print-return [v]
  (println v)
  v)
(defn extend-lst [lst n]
  (let [xtndr (if (= "*" (last lst)) "*" nil)]
    (loop [c (- n (count lst)) result lst]
      (if (> c 0)
        (recur (dec c) (conj result xtndr))
        result))))
(defn check-str [str token indexfn]
  (if
      (not (empty-str str))
    (= (indexfn str)  (. str indexOf token))
    false))
(defn begins-with [str token]
  (check-str
   str
   token
   (fn [n] 0)))
(defn ends-with [str token]
  (check-str
   str
   token
   (fn [n] (dec (count n)))))
(defn reduce-boolean-lst [lst]
  (reduce
   (fn [result a] (if result a result))
   true
   lst))
(defn vec-compare [crrnt in]
  (reduce-boolean-lst
   (map
    (fn [a b]
      (or
       (begins-with a ":") ;indicates a paramete
       (= a "*") ;indicates a wildcard for the rest of the uri
       (= a b))) crrnt in)))
(defn uri-to-vec [uri]
  (vec
   (filter (fn [n] (not= "" n))
           (clojure.string/split uri #"\/"))))
(defn match-uris [a incoming]
  (let [in (uri-to-vec incoming)
        crrnt (extend-lst (uri-to-vec a) (count in))]
    (if (>= (count in) (count crrnt))
      (vec-compare crrnt in)
      false)))
(defn find-match-in-list [lst uri method]
;  (println uri)
  (reduce
   (fn [result n]
     (if (= nil result)
       (if (match-uris (n 0) uri)
         ((n 1) method)
         nil)
       result))
   nil
   lst))
(defn result-or-default [default result-fn]
  (if (= nil result-fn)
    default
    result-fn))
(defn find-match [lst default uri method]
  (result-or-default default (find-match-in-list lst uri method)))
