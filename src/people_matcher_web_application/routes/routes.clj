(ns people-matcher-web-application.routes.routes)
(defn body-wrap [body-text]
  (str "<html><body>"
  (clojure.string/join #" " body-text)
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
  (body-wrap ["register a new user"
               "<form action='register' method='post'>"
              "Name: <input name='name' type='text'>"
              "Password: <input name='password' type='password'>"
              "<input type='submit'>"
               "</form>"]))
(defn login [params]
  (body-wrap ["login page" params]))
(defn home [params]
  (body-wrap ["<div>"
               "<span>"
               "<a href='/login'>login</a>"
               "</span>"
               "<br/>"
               "<span>"
               "<a href='/register'>register</a>"
               "</span>"
               "</div>"]))
(defn new-user [n]
  (body-wrap (clojure.string/join #"" ["new user has been created... " n])))
