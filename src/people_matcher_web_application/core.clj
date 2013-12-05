(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty
        ring.middleware.cookies
        ring.middleware.stacktrace)
  (:require [people-matcher-web-application.router.router
             :as router])
  (:require [people-matcher-web-application.dao.mongo-dao
             :as mongo-dao])
  (:require [people-matcher-web-application.login-verifier.login-verifier
             :as login-verifier]))
(defn handler [request-data]
  (println request-data)
  (let
      [params (request-data :params)
       uri (request-data :uri)
       request-method (request-data :request-method)
       cookies (request-data :cookies)]
    (println (cookies :value))
    (if
        (login-verifier/verify cookies)
      {:status 200
       :headers {"content-type" "text/html"}
       :body (router/route params uri request-method)}
      {:status 200
       :headers {"content-type" "text/html"}
       :body "<span>defect</span>"})))
(def app
  (-> handler wrap-cookies wrap-params))
(defn start [port]
  (run-jetty app {:port port :join? false}))
(defn -main []
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "3000"))]
    (start port)))
