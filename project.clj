(defproject bc-rest "0.1.0-SNAPSHOT"
  :description "BC - Clojure test"
  :url "http://betconnect.com/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler bc-rest.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
