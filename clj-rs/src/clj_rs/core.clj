(ns clj-rs.core
  (:import jnr.ffi.LibraryLoader))

(def edn 
  {:first-name "Julia"
   :last-name "Naomi"
   :age 30})


(defn -main [& args]
  (let [cljrust (-> (gen-interface :name "LibC" :methods [[hello [String] String] [add [int int] int] [person_txt [String] String]])
                    LibraryLoader/create
                    (.load "cljrust"))]
    (println (-> cljrust (.hello "Hello World")))
    (println (-> cljrust (.person_txt (pr-str edn))))
    (println (-> cljrust (.add 3 4)))))
  
