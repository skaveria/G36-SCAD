(ns magwell.core
  (:require [scad-clj.model :as m]
            [magwell.io :as mio]
            [magwell.shell :as shell]
            [magwell.mag-inner :as mag-inner]
            [magwell.mag-rim :as mag-rim]
            [magwell.mag-ear :as mag-ear]))

(defn model
  []
  (m/difference
   (m/union
    (m/difference
     (shell/shell-with-slant-cut)
     (mag-inner/mag-inner-cutter)
     (mag-rim/mag-rim)
     (mag-rim/mag-rim-mirrored)
     (mag-rim/mag-rim-z))
    (mag-ear/mag-ear))
   (mag-ear/mag-ear-hole)))

(defn debug-model
  []
  (m/union
   (model)
   (mag-ear/mag-ear-visual)
   (mag-rim/mag-rim-visual)
   (mag-rim/mag-rim-mirrored-visual)
   (mag-rim/mag-rim-z-visual)))

(defn -main
  [& _]
  (binding [scad-clj.model/*center* false]
    ;; swap to (debug-model) whenever you want overlays
    (mio/write-scad! "out/magwell.scad" (model))))
