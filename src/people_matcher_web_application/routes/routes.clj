(ns people-matcher-web-application.routes.routes)
(defn page [name password]
  (str "<html><body>"
       (if (and name password)
         (str "Nice to meet you, " name "!")
         (str "<form>"
              "Name: <input name='name' type='text'>"
              "Password: <input name='password' type='text'>"
              "<input type='submit'>"
              "</form>"))
       "</body></html>"))
