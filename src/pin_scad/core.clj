(ns hello-scad.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))

(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

(defn hello []
  ;; a single cube, centered at origin
  (m/cube 10))

(defn -main [& _]
  (write-scad! "out/hello.scad" (hello)))
