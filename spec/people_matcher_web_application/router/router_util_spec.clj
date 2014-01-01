(ns people-matcher-web-application.router.router-util-spec
  (:require [speclj.core :refer :all]
            [people-matcher-web-application.router.router-util :refer :all]))

(describe "empty-str"

  (it "pass in empty string"
    (should (empty-str "")))

  (it "pass in nil"
    (should (empty-str nil)))

  (it "pass in non-empty string"
    (should-not (empty-str " ")))
)

(describe "Tests for begins-with"

  (it "multi char string begins with"
    (should (begins-with ":neato" ":")))

  (it "one char string begins with"
    (should (begins-with ":" ":")))

  (it "multi char string does not begin with"
    (should-not (begins-with "a:neato" ":")))

  (it "empty string"
    (should-not (begins-with "" ":")))

  (it "nil in place of a string"
    (should-not (begins-with nil ":")))
)

(describe "Tests for ends-with"

  (it "string longer than 1 char ends with"
    (should (ends-with ":neato" "o")))

  (it "one char string ends with"
    (should (ends-with ":" ":")))

  (it "string longer than 1 char does not end with"
    (should-not (ends-with "a:neato" ":")))

  (it "empty string"
    (should-not (ends-with "" ":")))

  (it "nil in place of a string"
    (should-not (ends-with nil ":")))
)

(describe "Tests for vec-compare"

  (it "regular vector"
    (should (= true
               (vec-compare ["a" "b" "c"] ["a" "b" "c"]))))

  (it "vector with parameters"
    (should (= true
               (vec-compare ["a" ":b" "c"] ["a" "random value" "c"]))))

  (it "vector with wild card"
      (should (= true
                 (vec-compare ["a" ":b" "*"] ["a" "random value" "c" "d" "e"]))))

  (it "vector compared to longer vector"
      (should (= true
                 (vec-compare ["a" ":b" "*"] ["a" "random value" "c" "d" "e"]))))
)

(describe "These paths should match"

  (it "is true"
    (should (match-uris "/cool/new/:uri" "cool/new/random")))

  (it "wildcard on the end of the key uri"
    (should (match-uris "/cool/new/:uri/*" "cool/new/random/interesing/will/it/work")))

  (it "wildcard on the end of the key uri"
    (should (match-uris "/cool/new/:uri/*" "cool/new/random/interesing")))

  (it "wildcard on the end of the key uri"
    (should (match-uris "" "/")))

  (it "wildcard on the end of the key uri"
    (should (match-uris "/" "")))

  (it "wildcard on the end of the key uri"
    (should (match-uris "/" "")))
)

(describe "These paths should not match"

  (it "incoming url is too long"
    (should-not (match-uris "/cool/new/:uri" "cool/new/random/neato")))

  (it "incoming url is too short"
    (should-not (match-uris "/cool/new/*" "cool/new")))
)
(defn get-route-lst []
  [
   ["/cool/new/:uri"
    {:get (fn [n] 1)}]
   ["/cool/new/:uri"
    {:get (fn [n] 2)}]
   ["/cool/new/:uri"
    {:post (fn [n] 2)}]
   ["/cool/old/:uri"
    {:get (fn [n] 5)}]])
(defn default-rt []
  (fn [n m] "default"))
(describe "Finding match in a list"
    (it "sould take the first matching url"
        (should (= ((find-match (get-route-lst)  (default-rt) "cool/new/random" :get) "found") 1)))

    (it "url that is not first is returnable"
        (should (= ((find-match (get-route-lst) (default-rt) "cool/old/random" :get) "found") 5)))

    (it "non matching url returns default"
        (should (= ((find-match (get-route-lst) (default-rt) "cool/should/return/default/random" :get) "found" "arg2") "default")))

    (it "url match with wrong method returns default"
        (should (= ((find-match (get-route-lst) (default-rt) "cool/old/random" :post) "found" "arg2") "default")))

    (it "url match with wrong method returns default"
        (should (= ((find-match (get-route-lst) (default-rt) "fav.icon" :post) "found" "arg2") "default")))

    (it "returns a url other than the first match if method does not match"
        (should (= ((find-match (get-route-lst)  (default-rt) "cool/new/random" :post) "found") 2)))
)
(run-specs)
