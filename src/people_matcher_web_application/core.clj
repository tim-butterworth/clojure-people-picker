(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty)
  (:require [people-matcher-web-application.router.router :as router])
  (:require [people-matcher-web-application.dao.mongo-dao :as mongo-dao]))

(defn handler [request-data]
  (println request-data)
  (let
      [params (request-data :params)
       uri (request-data :uri)
       request-method (request-data :request-method)]
  (-> (response (router/route params uri request-method))
      (content-type "text/html"))))
(def app
  (-> handler wrap-params))
(defn start [port]
  (run-jetty app {:port port :join? false}))
(defn -main []
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "3000"))]
    (start port)))
