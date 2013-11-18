(ns people-matcher-web-application.core
  (:gen-class)
  (:use ring.middleware.params
        ring.util.response
        ring.adapter.jetty)
  (:require [people-matcher-web-application.routes.routes :as routes]))

(defn handler [{{name "name"} :params}]
  (-> (response (routes/page name))
      (content-type "text/html")))

(def app
  (-> handler wrap-params))

(defn start [port]
  (run-jetty handler {:port port :join? false}))

(defn -main []
  (let [port (Integer/parseInt
               (or (System/getenv "PORT") "3000"))]
  (start port)))

;(defn -main
;  "I don't do a whole lot ... yet."
;  [& args]
;  (run-jetty handler {:port 3000}))
