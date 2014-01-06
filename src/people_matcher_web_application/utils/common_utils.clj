(ns people-matcher-web-application.utils.common-utils)
(defn print-return [arg]
  (println arg)
  arg)
(defn success-failure [success failure tester result]
  (if (tester result)
    (success result)
    (failure result)))
(defn success-failure-builder [success failure tester]
  (fn [result]
    (success-failure success failure tester result)))
(defn validate-params-1 [params]
  (if (not (= nil (params :name)))
    true
    false))
(defn validate-params-2 [params]
  (if (not (= nil (params :age)))
    true
    false))
(defn validate-params-3 [params]
  (if (not (= nil (params :height)))
    true
    false))
(def first (success-failure-builder
            (fn [n] "success")
            (fn [n] "failed on first")
            validate-params-1))
(def second (success-failure-builder
             first
             (fn [n] (println "failed on second"))
             validate-params-2))
(def third (success-failure-builder
            second
            (fn [n] (println "failed on third"))
            validate-params-3))
