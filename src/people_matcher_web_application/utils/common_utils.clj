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
(defn replace-builder [ptrn val]
  (fn [n] (clojure.string/replace n ptrn val)))
