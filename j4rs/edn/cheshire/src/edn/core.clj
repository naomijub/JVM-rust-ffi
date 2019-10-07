(ns edn.core
  (:require [cheshire.core :refer :all])
  (:gen-class
    :name edn.core.cheshire
    :methods [#^{:static true} [parseJsonString [String] String]]))

(defn parseJsonString [json-str]
  (str (parse-string json-str true)))

(defn -parseJsonString [json-str]
  (parseJsonString json-str))

(defn -main []
  (println "Init EDN"))
