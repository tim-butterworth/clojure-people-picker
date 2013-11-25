(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty)
  (:require [people-matcher-web-application.routes.routes :as routes]))

(defn handler [request-data]
  (println request-data)
  (let
      [params (request-data :params)
       password (params "password")
       name (params "name")
       uri (request-data :uri)]
    (println params)
    (println uri)
    (println (count (clojure.string/split uri #"\/")))
  (-> (response (routes/page name password))
      (content-type "text/html"))))

(def app
  (-> handler wrap-params))

(defn start [port]
  (run-jetty app {:port port :join? false}))

(defn -main []
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "3000"))]
    (start port)))
