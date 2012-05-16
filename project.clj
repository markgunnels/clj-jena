(defproject clj-jena "0.0.1-SNAPSHOT"
  :description "FIXME: Clojure wrapper for Jena"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.apache.jena/jena-core "2.7.1-incubating-SNAPSHOT"]
                 [org.apache.jena/jena-tdb "0.9.1-incubating-SNAPSHOT"]]
  :plugins [[lein-swank "1.4.4"]]
  :repositories {"jena-snapshot" "https://repository.apache.org/content/repositories/snapshots/"})