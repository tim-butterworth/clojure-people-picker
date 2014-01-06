(ns people-matcher-web-application.router.router
  (:require [people-matcher-web-application.io.mongo :as mongo])
  (:require [people-matcher-web-application.verifier.verifier
             :as verifier])
  (:require [people-matcher-web-application.router.router-util :as util])
  (:require [people-matcher-web-application.router.routes :as routes]))

(def default-route routes/home)
(def route-lst [["/login" {:get routes/login
                         :post routes/login-home}]
               ["/home" {:get routes/home}]
               ["/register" {:get routes/register
                            :post routes/new-user}]
               ["/" {:get routes/home}]
               ["/add-person" {:get routes/add-person
                               :post routes/new-person}]])
(defn get-route [request]
  ((util/find-match
    route-lst
    default-route
    (request :uri)
    (request :request-method))
   request))
