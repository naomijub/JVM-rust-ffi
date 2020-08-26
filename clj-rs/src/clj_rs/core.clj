(ns clj-rs.core
  (:import jnr.ffi.LibraryLoader))

(defn -main [& args]
  (let [cljrust (-> (gen-interface :name "LibC" :methods [[hello [String] String] [add [int int] int]])
                    LibraryLoader/create
                    (.load "cljrust"))]
    (println (-> cljrust (.hello "duuuh")))
    (println (-> cljrust (.add 3 4)))))
  
