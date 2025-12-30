(ns magwell.io
  (:require [clojure.java.io :as io]
            [scad-clj.scad :as s]))

(defn write-scad!
  [path model]
  (io/make-parents path)
  (spit path
        (str
         "$fn = 32;\n\n"   ;; <-- increase cylinder smoothness here
         (s/write-scad model)))
  path)
