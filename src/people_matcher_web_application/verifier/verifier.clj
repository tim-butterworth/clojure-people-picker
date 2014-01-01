(ns people-matcher-web-application.verifier.verifier)
(defn verify [cookies]
  (println cookies)
  (println (keys cookies))
  (if (not (= nil (cookies "member_session")))
    true
    false))
(defn user-created [r]
  (println (clojure.string/join #" " ["user-created? " r]))
  (if (= nil (. r get "err"))
    true
    false))
(defn user-exists [r]
  (if (= 0 (count r))
    false
    true))
(defn print-return [v]
  (println v)
  v)
(defn safe-get [mp keys]
  (reduce
   (fn [r k] (if (not (= nil r))
               (r k)
               nil))
   mp
   keys))
(defn valid-cookie [c]
  (println "cookie:")
  (println c)
  (if (= "true" (safe-get c ["loggedin" :value]))
    true
    false))
