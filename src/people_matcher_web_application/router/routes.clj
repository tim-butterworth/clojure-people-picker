(ns people-matcher-web-application.router.routes
  (:require [people-matcher-web-application.views.views :as views]))
(defn too-html [body]
  {:status 200
   :body body})
(defn simple-body [fn params]
  (too-html (fn params)))
(defn home [params cookies]
  (simple-body views/home params))
(defn register [params cookies]
  (simple-body views/register params))
(defn login [params cookies]
  (simple-body views/login params))
(defn new-user [params cookies]
  (simple-body views/new-user params))
