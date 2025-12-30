(ns magwell.io
  (:require [clojure.java.io :as io]
            [scad-clj.scad :as s]))

(defn write-scad!
  [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)
