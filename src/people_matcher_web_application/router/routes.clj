(ns people-matcher-web-application.router.routes
  (:require [people-matcher-web-application.views.views :as views])
  (:require [people-matcher-web-application.io.mongo :as mongo])
  (:require [people-matcher-web-application.utils.common-utils :as util])
  (:require [people-matcher-web-application.utils.cookie-utils :as cookie-util])
  (:require [people-matcher-web-application.verifier.verifier :as verifier])
  (:require [people-matcher-web-application.models.models :as models]))
(defn add-cookie [cookie body]
  (assoc body :cookies cookie))
(defn get-params [request]
  (request :params))
(defn too-simple-html [body]
  {:status 200
   :body body})
(defn too-html [body code]
  {:status code
   :body body})
(defn simple-body [f request]
  (too-simple-html (f (get-params request))))
(defn error-body [f msg code]
  (too-html (f msg) code))
(defn home [request]
   (simple-body views/home request))
;LOGIN
(defn login [request]
  (simple-body views/login request))
(defn try-logging-in [request]
  (fn [args]
    (util/success-failure
     (fn [args]
       (add-cookie
        (cookie-util/create-cookie args)
        (simple-body views/logged-in-home request)))
     (fn [args] (error-body views/error "Invalid username password combination" 200))
     (fn [lst] (not (= 0 (count lst))))
     (mongo/check-user (get-params request)))))
(defn login-home [request]
  (println "login-home")
  (println (mongo/check-user (get-params request)))
  (util/success-failure
   (try-logging-in request)
   (fn [req] (simple-body views/login req))
   (verifier/param-validator-builder models/login-params)
   (get-params request)))
;REGISTER
(defn register [request]
  (simple-body views/register request))
(defn try-saving-user [request]
  (fn [params]
    (util/success-failure
     (fn [args] (simple-body views/new-user request))
     (fn [args] (error-body views/error (clojure.string/join #"<br/>" ["Error saving new user" (. args get "err")]) 200))
     verifier/user-created
     (mongo/new-user params))))
(defn new-user [request]
  (util/success-failure
   (try-saving-user request)
   (fn [req] (simple-body views/register req))
   verifier/param-validator-evaluator
   ((verifier/param-validator-builder models/new-user-params)
    (get-params request))))
;ADD PEOPLE
(defn add-person [request]
  (println request)
  (simple-body views/add-person request))
(defn new-person [request]
  (println request)
  (simple-body views/add-person request))
