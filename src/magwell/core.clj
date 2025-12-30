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
   [magwell.lever-pocket :as lever-pocket]))


(defn model
  []
  (m/difference
   (m/union
    (m/difference
     (shell/shell-with-slant-cut)
     (mag-inner/mag-inner-cutter)
     (lever-pocket/lever-pocket)
     (mag-rim/mag-rim)
     (mag-rim/mag-rim-mirrored)
     (mag-rim/mag-rim-z))
    (mag-ear/mag-ear))
    (ear-shelf/ear-shelf)
   (mag-ear/mag-ear-hole)   ;; if still used
   (pinhole/pinhole)
   (pinhole/pinhole-2)
   (dovetail/dovetail)          ;; <-- ship it here
   ))

;; <-- global drill

;; or mag-ear, whichever you're using

(defn debug-model []
  (m/union
   (model)
   (ear-ridge/ear-ridge-visual)))


(defn -main
  [& _]
  (binding [scad-clj.model/*center* false]
    ;; swap to (debug-model) whenever you want overlays
    (mio/write-scad! "out/magwell.scad" (debug-model))))
