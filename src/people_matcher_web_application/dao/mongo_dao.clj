(ns people-matcher-web-application.dao.mongo-dao)

(import com.mongodb.DB)
(import com.mongodb.Mongo)
(import com.mongodb.MongoClient)
(import com.mongodb.BasicDBObject)
(import com.mongodb.DBCollection)
(import com.mongodb.DBCursor)
(import com.mongodb.MongoClientURI)
(import com.mongodb.WriteConcern)

(def uri (new MongoClientURI (. System getenv "MONGOHQ_URL")))
(def client (new MongoClient uri))
(def db (. client getDB "peoplematcher"))

(if
    (not
     (and
      (= nil (. uri getUsername))
      (= nil (. uri getPassword))))
  (. db authenticate
     (. uri getUsername)
     (. uri getPassword)))

(defn populate [datamap]
  (reduce
   (fn [result n] (. result append n (datamap n)))
   (new BasicDBObject)
   (keys datamap)))
(defn toVec [cursor]
  (loop [rslt []]
    (if (. cursor hasNext)
      (recur (conj rslt (. cursor next)))
      rslt)))
(defn insert [data cllctn]
    (. (. cllctn save (populate data) (. WriteConcern NORMAL)) getLastError))
(defn findData [data cllctn]
  (let [cursor (. cllctn find (populate data))]
    (if (not (= nil cursor))
      (toVec cursor)
      [])))
(defn deleteData [data cllctn]
  (. cllctn remove (populate data)))

(def cllctn-mp {:users (. db getCollection "users")
                :people (. db getCollection "people")
                :restrictions (. db getCollection "restrictions")})
(def fun-mp {:insert insert
             :find findData
             :delete deleteData})

(defn doAction [data actionKey cllctnKey]
    ((fun-mp actionKey) data (cllctn-mp cllctnKey)))
;code to create a unique index
;(. (cllcnt-mp :users) ensureIndex (populate {"username" 1}) (populate {"unique" true}))
