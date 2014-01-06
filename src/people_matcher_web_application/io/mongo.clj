(ns people-matcher-web-application.io.mongo
  (:require [people-matcher-web-application.dao.mongo-dao :as dao]))

(def salt "this is my cool new salt")
(defn encrypt-hash [un pw]
  (let [hashed (clojure.string/join #"" [salt un pw])]
    (println hashed)
    hashed))
(defn mongo-user-action [data action]
  (println data action)
  (dao/doAction data action :users))
(defn save-new-user [un pw]
  (let
      [hashed (encrypt-hash un pw)
       data {"username" un "userhash" hashed}]
      (mongo-user-action data :insert)))
(defn lookup-user [un pw]
  (let
      [hashed (encrypt-hash un pw)
       data {"username" un "userhash" hashed}]
      (mongo-user-action data :find)))
(defn new-user [params]
  (let [username (params "name") password (params "password")]
      (save-new-user username password)))
(defn check-user [params]
  (let [username (params "name") password (params "password")]
      (lookup-user username password)))
