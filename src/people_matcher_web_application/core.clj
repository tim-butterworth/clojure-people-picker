(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty)
  (:require [people-matcher-web-application.routes.routes :as routes])
  (:require [people-matcher-web-application.dao.mongo-dao :as mongo-dao]))

(defn find-route [uri]
  (let [path (clojure.string/split uri #"\/")]
  (println path)
  (routes/home)))
(defn handler [request-data]
  (println request-data)
  (let
      [params (request-data :params)
       password (params "password")
       name (params "name")
       uri (request-data :uri)]
  (-> (response (find-route uri))
      (content-type "text/html"))))

(def app
  (-> handler wrap-params))

(defn start [port]
  (run-jetty app {:port port :join? false}))

(defn -main []
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "3000"))]
    (start port)))
