(ns people-matcher-web-application.io.mongo
  (:require [people-matcher-web-application.dao.mongo-dao :as dao]))

(def salt "this is my cool new salt")
(defn encrypt-hash [un pw]
  (let [hashed (clojure.string/join #"" [salt un pw])]
    (println hashed)
    hashed))
(defn empty-str [s]
  (if (or (= nil s) (= "" s))
    true
    false))
(defn mongo-user-action [data action]
  (println data action)
  (dao/doAction data action :users))
(defn printup [n]
  (println (clojure.string/join #"" ["printup:" n]))
  n)
(defn to-result [n]
  (if (empty-str n)
    "success"
    (printup n)))
(defn save-new-user [un pw]
  (let
      [hashed (encrypt-hash un pw)
       data {"userhash" hashed}
       num-found (count (mongo-user-action data :find))]
    (if (= 0 num-found)
      (mongo-user-action data :insert)
      "user already exists")))
(defn new-user [params]
  (let [username (params "name") password (params "password")]
    (if (and
         (not (empty-str name))
         (not (empty-str password)))
      (to-result (save-new-user username password))
      "username or password are missing")))
