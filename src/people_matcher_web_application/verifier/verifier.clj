(ns people-matcher-web-application.verifier.verifier)
(defn verify [cookies]
  (println cookies)
  (println (keys cookies))
  (if (not (= nil (cookies "member_session")))
    true
    false))
(defn user-created [r]
  (println (clojure.string/join #" " ["user-created? " r]))
  (println (= nil (. r get "err")))
  (if (= nil (. r get "err"))
    true
    false))
(defn user-exists [r]
  (if (= 0 (count r))
    false
    true))
(defn safe-get [mp keys default]
  (let [result
       (reduce
        (fn [r k] (if (not (= nil r))
                    (r k)
                    nil))
        mp
        keys)]
    (if (= nil result)
      default
      result)))
(defn valid-cookie [cookie]
  (if
      (>
       (. Long valueOf (safe-get cookie ["people_picker_session" :value] 0))
       (. System currentTimeMillis))
    true
    false))
(defn empty-str [str]
  (or
   (= nil str)
   (= "" (. str trim))))
(defn param-validator-builder [param-mp]
  (fn [params]
    (assoc params :error-message
     (clojure.string/join " "
                          (filter
                           (fn [n] (not (= nil n)))
                           (map
                            (fn [n] (if (empty-str (params n))
                                      (clojure.string/join " " [n "was empty"])
                                      nil))
                            (keys param-mp)))))))
(defn param-validator-evaluator [params]
  (if (empty-str (params :error-message))
    true
    false))
