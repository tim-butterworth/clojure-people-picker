(ns people-matcher-web-application.io.mongo
  (:require [people-matcher-web-application.dao.mongo-dao :as dao]))

(def salt "this is my cool new salt")
(defn encrypt-hash [un pw]
  (let [hashed (clojure.string/join #"" [salt un pw])]
    (println hashed)
    hashed))
(defn new-user [params]
  (println params)
  (println "making a new user")
  (let [username (params "name") password (params "password")]
    (if (and
         (not (= nil name))
         (not (= nil password)))
      (let
          [num-found
           (count
            (dao/doAction
             {"userhash" (encrypt-hash username password)}
             :find
             :users))]
        (if (= 0 num-found)
          (dao/doAction
           {"userhash" (encrypt-hash username password)}
           :insert
           :users)))
                                        ;else do nothing
      )))
