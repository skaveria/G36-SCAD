(ns magwell.core
  (:require
   [scad-clj.model :as m]
   [magwell.io :as mio]
   [magwell.shell :as shell]
   [magwell.mag-inner :as mag-inner]
   [magwell.lever-pocket :as lever-pocket]
   [magwell.mag-rim :as mag-rim]
   [magwell.mag-ear :as mag-ear]
   [magwell.pinhole :as pinhole]
   [magwell.ear-shelf :as ear-shelf]
   [magwell.dovetail :as dovetail]
   [magwell.ear-ridge :as ear-ridge]
   [magwell.x-bore :as x-bore]
   [magwell.params :as p]
   [magwell.lever-pocket :as lever-pocket]))


(defn model
  []
  (m/difference
   (m/union
    (m/difference
     (shell/shell-with-slant-cut)
     (mag-inner/mag-inner-cutter)
     (lever-pocket/lever-pocket)
     (dovetail/dovetail)
     (mag-rim/mag-rim)
     (mag-rim/mag-rim-mirrored)
     (mag-rim/mag-rim-z))
    (mag-ear/mag-ear)
    (ear-ridge/ear-ridges)
    )
   (ear-shelf/ear-shelf)        ;; <-- shelf subtracts from everything
   (mag-ear/mag-ear-hole)
   (x-bore/x-bore)
   (pinhole/pinhole)
   (pinhole/pinhole-2)
   ))

(defn debug-model []
  (m/union
   (model)
   (ear-ridge/ear-ridges-visual)))


(defn -main
  [& _]
  (binding [scad-clj.model/*center* false]
    (mio/write-scad-with-import-cutters!
     "out/magwell.scad"
     (model)
     [{:pos (:pos p/sidecutter)   :rot (:rot p/sidecutter)   :stl "sidecutter.stl"}
      {:pos (:pos p/sidecutter-2) :rot (:rot p/sidecutter-2) :stl "sidecutter.stl"}])))
