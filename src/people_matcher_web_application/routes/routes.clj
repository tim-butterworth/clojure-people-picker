(ns people-matcher-web-application.routes.routes)
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
