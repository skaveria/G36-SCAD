(ns magwell.mag-ear
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn mag-ear
  "Cylindrical ear feature (additive), top-flush with shell."
  []
  (let [{:keys [diameter height pos]} p/mag-ear
        {:keys [x y]} pos
        r (/ diameter 2.0)
        shell-z (:z p/outer-dims)
        z0 (- shell-z height)]   ;; top-flush alignment
    (m/translate [x y z0]
      (m/cylinder r height :center false))))

(defn mag-ear-visual
  []
  (m/color [0 1 0 1] ;; green so it reads as additive
    (mag-ear)))

(defn mag-ear-hole
  "6mm through-hole cutter, intentionally overlong so it always cuts through."
  []
  (let [{:keys [diameter height pos]} p/mag-ear
        {:keys [x y]} pos
        shell-z (:z p/outer-dims)
        z0 (- shell-z height)
        r  (/ 6.0 2.0)
        extra 10.0
        z-start (- z0 (/ extra 2.0))
        h (+ height extra)]
    (m/translate [x y z-start]
      (m/cylinder r h :center false))))

(defn mag-ear-with-hole
  "Ear cylinder with a 6mm through-hole removed."
  []
  (m/difference
   (mag-ear)
   (mag-ear-hole)))
