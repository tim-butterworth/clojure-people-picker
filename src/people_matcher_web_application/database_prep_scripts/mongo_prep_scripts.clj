(ns people-matcher-web-application.database-prep-scripts.mongo-prep-scripts
  (:require [people-matcher-web-application.dao.mongo-dao :as dao]))
(def unique-index-map
  {:users ["username"]
   :people ["userhash" "firstname" "lastname" "mi" "personid"]
   :restrictions ["userhash" "personid" "restrictedperson"]})
(defn to-index-map [props]
  (reduce
   (fn [accum n]
     (assoc accum n 1))
   {}
   props))
(defn unique-index [props cllctn]
  (.
   cllctn
   ensureIndex
   (dao/populate (to-index-map props))
   (dao/populate {"unique" true})))
(defn create-unique-indexes []
  (let [tables (keys dao/cllctn-mp)]
    (map
     (fn [n] (unique-index
              (unique-index-map n)
              (dao/cllctn-mp n)))
     tables)))
