(ns people-matcher-web-application.router.router
  (:require [people-matcher-web-application.routes.routes :as routes])
  (:require [people-matcher-web-application.io.mongo :as mongo]))

(def routes-mp
  {
   :get
   {
    "login" routes/login
    "register" routes/register
    }
   :post
   {
    "makenewuser" (fn [n]
                    (->
                     (mongo/new-user n)
                     routes/new-user))
    }})
(defn route [params uri request-method]
  (let [path (clojure.string/split uri #"\/")
        request-routes-mp (routes-mp request-method)]
  (println path)
  (if (and (<= 1 (count path)) (not (= nil (request-routes-mp (path 1)))))
    ((request-routes-mp (path 1)) params)
    (routes/home params))))
