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
(defn register []
  (str "<html><body>hi</body></html>"))
(defn home []
  (body-wrap "hi"))
