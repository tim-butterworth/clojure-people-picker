(ns people-matcher-web-application.views.views
  (:use hiccup.core))
(defn a [uri lbl]
  [:a {:href uri} lbl])
(defn input [lbl name type]
  [:span lbl [:input {:name name :type type}]])
(defn submit []
  [:input {:type "submit"}])
(defn form-wrap [action method contents]
  [:form {:action action :method method} contents [:br] (submit)])
(defn body-wrap [content]
  (html
   [:html
    [:body
     content]]))
(defn register [params]
  (println params)
  (body-wrap
   (form-wrap "register" "post"
              [:div
               (input "Name:" "name" "text")
               [:br]
               (input "Password:" "password" "password")
               [:br]])))
(defn login [params]
  (body-wrap
   (form-wrap "login" "post"
    [:div [:h1 "login page"]
     [:br]
     (input "Name:" "name" "text")
     [:br]
     (input "Password:" "password" "password")])))
(defn home [params]
  (body-wrap
   [:div
    [:span
     (a "/login" "login")
     [:br]
     (a "/register" "register")]]))
(defn new-user [n]
  (body-wrap [:div "A new user has been created... "
              [:span (a "/login" "login")]]))
(defn logged-in-home [n]
  (body-wrap [:div "you are now logged in noob..."
              [:br]
              (a "add-person" "add people") [:br]
              (a "view-people" "view people") [:br]
              (a "#" "more stuff to do") [:br]
              (a "logout" "logout") [:br]]))
(defn add-person [arg]
  (body-wrap [:div [:h1 "Add a person"]
              [:br]
              (form-wrap "add-person" "post"
                         [:div
                          (input "First Name:" "first name" "text")
                         [:br]
                         (input "Last Name:" "last name" "text")
                         [:br]
                         (input "Email:" "email" "text")])]))
(defn error [msg]
  (body-wrap [:div msg
              [:br]
              (a "home" "home")]))
