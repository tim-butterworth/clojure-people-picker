(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty
        ring.middleware.cookies
        ring.middleware.stacktrace)
  (:require [people-matcher-web-application.router.router
             :as router])
  (:require [people-matcher-web-application.utils.common-utils :as common])
  (:require [people-matcher-web-application.database-prep-scripts.mongo-prep-scripts
             :as script]))

(defn handler [request-data]
    (router/get-route request-data))
(def app
  (-> handler wrap-cookies wrap-params))
(defn start [port]
  (run-jetty app {:port port :join? false}))
(defn -main []
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "3000"))]
    (start port)))
