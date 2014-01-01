(defproject people-matcher-web-application "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.1"]
                 [ring/ring-jetty-adapter "1.2.0"]
                 [org.mongodb/mongo-java-driver "2.11.0"]
                 [hiccup "1.0.4"]]
  :profiles {:dev {:dependencies [[speclj "2.8.1"]]}}
  :plugins [[speclj "2.8.1"]]
  :test-paths ["spec"]
  :main ^:skip-aot people-matcher-web-application.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
