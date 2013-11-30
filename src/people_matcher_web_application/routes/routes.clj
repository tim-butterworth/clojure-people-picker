(ns people-matcher-web-application.routes.routes)
(defn body-wrap [body-text]
  (str "<html><body>"
  body-text
  "</body></html>"))
(defn page [name password]
  (str "<html><body>"
       (if (and name password)
         (str "Nice to meet you, " name "!")
         (str "<form action='login' method='post'>"
              "Name: <input name='name' type='text'>"
              "Password: <input name='password' type='password'>"
              "<input type='submit'>"
              "</form>"))
       "</body></html>"))
(defn register [params]
  (body-wrap (clojure.string/join
              #" "
              ["register a new user"
               "<form action='makenewuser' method='post'>"
              "Name: <input name='name' type='text'>"
              "Password: <input name='password' type='password'>"
              "<input type='submit'>"
               "</form>"])))
(defn login [params]
  (body-wrap "login page"))
(defn home [params]
  (body-wrap (clojure.string/join
              #" "
              ["<div>"
               "<span>"
               "<a href='/login'>login</a>"
               "</span>"
               "<br/>"
               "<span>"
               "<a href='/register'>register</a>"
               "</span>"
               "</div>"])))
(defn new-user [n]
  (body-wrap "new user has been created... maybe"))
