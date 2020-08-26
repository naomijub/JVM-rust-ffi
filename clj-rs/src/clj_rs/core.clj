(ns clj-rs.core
  (:import jnr.ffi.LibraryLoader))

(defn -main [& args]
  (-> (gen-interface :name "LibC" :methods [[puts [String] int]])
      LibraryLoader/create
      (.load "c")
      (.puts "Hello, World")))
