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
