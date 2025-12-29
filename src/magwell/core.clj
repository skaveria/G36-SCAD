(ns magwell.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))

(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

(defn magwell-block []
  ;; 1 unit = 1 mm
  (m/cube 60 40 80))

(defn -main [& _]
  (write-scad! "out/magwell.scad" (magwell-block)))

