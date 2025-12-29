(ns hello-scad.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))

(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)


(defn hello []
  (m/minkowski
    ;; main shape
    (m/difference
      (m/cube 20 20 20)
      (m/translate [10 10 0]
                   (m/cylinder 5 25)))

    ;; bevel kernel (radius = bevel size)
    (m/sphere 1)))

(defn -main [& _]
  (write-scad! "out/hello.scad" (hello)))
