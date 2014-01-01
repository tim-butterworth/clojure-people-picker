(ns people-matcher-web-application.router.router
  (:require [people-matcher-web-application.io.mongo :as mongo])
  (:require [people-matcher-web-application.verifier.verifier
             :as verifier])
  (:require [people-matcher-web-application.router.router-util :as util])
  (:require [people-matcher-web-application.router.routes :as routes]))
;verify the form... can be done with a map... return message if fails
;verify database operation... return message if fails
;verify cookie if needed... sucess, update cookie, fail return message
;return new page

(defn success-fail [sf ff tester result]
  (if (tester result)
    (sf result)
    (ff result)))
(defn pass-through-cookies [result c]
  (assoc result :cookies c))
(defn mod-result [f key]
  (fn [result params cookies]
    (assoc result
      key
      (f params cookies))))
(defn result-f-lst [bodyf cookiesf]
  [(mod-result bodyf :body)
   (mod-result cookiesf :cookies)
   (mod-result (fn [n m] 200) :status)
   (mod-result (fn [n m] {"content-type" "text/html"}) :headers)])
(defn reduce-to-result [f-lst]
  (fn [p c]
    (reduce
     (fn [rslt f] (f rslt p c))
     {}
     f-lst)))
(defn p-only [f]
  (fn [p c] (f p)))
(defn c-only [f]
  (fn [p c] (f c)))
(defn prep-uri [uri]
  ((conj
    (vec
     (filter
      (fn [n] (not (. n equals "")))
      (clojure.string/split uri #"\/")))
    :default) 0))
(def default-route routes/home)
(def route-lst [["/login" {:get routes/login
                         :post routes/login}]
               ["/home" {:get routes/home}]
               ["/register" {:get routes/register
                            :post routes/new-user}]
               ["/" {:get routes/home}]])
(defn print-return [arg]
  (println arg)
  arg)
(defn get-route [request]
  ((util/find-match
    route-lst
    default-route
    (request :uri)
    (request :request-method))
   (request :params)
   (request :cookies)))
