(ns magwell.core
  (:require [scad-clj.model :as m]
            [magwell.io :as mio]
            [magwell.shell :as shell]
            [magwell.mag-rim :as mag-rim]
            [magwell.mag-inner :as mag-inner]))

(ns magwell.core
  (:require [scad-clj.model :as m]
            [magwell.io :as mio]
            [magwell.shell :as shell]
            [magwell.mag-inner :as mag-inner]
            [magwell.mag-rim :as mag-rim]))

(defn model
  "Final magwell solid."
  []
  (m/difference
   (shell/shell-with-slant-cut)
   (mag-inner/mag-inner-cutter)
   (mag-rim/mag-rim)
   (mag-rim/mag-rim-mirrored)))

(defn -main
  [& _]
  (binding [scad-clj.model/*center* false]
    (mio/write-scad! "out/magwell.scad" (model))))
