(ns people-matcher-web-application.utils.cookie-utils
  (:require [people-matcher-web-application.utils.common-utils :as util]))
(defn update-cookie [cookie]
 (assoc cookie :expires
        (+
         (. System currentTimeMillis)
         (* 1000 15 60))))
(defn create-cookie [data]
  (let [hashname (. (data 0) get "userhash")]
    {"people-matcher"
     {:value
      (clojure.string/join ""
                           ["userdata="
                            hashname
                            ";"
                            "expires="
                            (+ (. System currentTimeMillis) (* 1000 15 60))])}}))
(defn valid-cookie [cookie]
  (if
      (>
       (cookie :expires)
       (. System currentTimeMillis))
    true
    false))
;(-> "value=userdata%3Dthis+is+my+cool+new+saltAa%3Bexpires%3D1389034440703" ((util/replace-builder #"%3B" ";")) ((util/replace-builder #"%3D" "=")))
