(ns people-matcher-web-application.router.router
  (:require [people-matcher-web-application.routes.routes :as routes])
  (:require [people-matcher-web-application.io.mongo :as mongo])
  (:require [people-matcher-web-application.verifier.verifier
             :as verifier]))
(defn success-fail [sf ff test args]
  (if test
    (sf args)
    (ff args)))
(def routes-mp
  {
   :get
   {
    "login" routes/login
    "register" routes/register
    }
   :post
   {
    "register" (fn [n] (let [result (mongo/new-user n)]
                         (success-fail
                          routes/login
                          routes/register
                          (verifier/user-created result)
                          result)))
    "login" (fn [n] (n))
    }})
(defn ok [body cookies]
  {:status 200
   :headers {"content-type" "text/html"}
   :body body
   :cookies cookies})
(defn prep-uri [uri]
  ((conj
   (vec
    (filter
     (fn [n] (not (. n equals "")))
     (clojure.string/split uri #"\/")))
   :default) 0))
(defn select-route [uri request-method]
    (let [rt ((routes-mp request-method) (prep-uri uri))]
      (if (= nil rt)
        routes/home
        rt)))
(defn route [params uri request-method cookies]
  (ok
   ((select-route uri request-method)
    params)
   {}))
