(ns magwell.ear-grooves
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn trapezoid-profile
  "2D trapezoid in X/Z, symmetric about X=0, resting on Z=0.
  Bottom width > top width gives a gentle taper."
  [{:keys [w-bottom w-top h]}]
  (let [xb (/ w-bottom 2.0)
        xt (/ w-top 2.0)]
    (m/polygon
     [[(- xb) 0]
      [xb 0]
      [xt h]
      [(- xt) h]])))

(defn extrude-along-y
  "Extrude a 2D (X/Z) profile along +Y.
  scad-clj rotates are in radians."
  [len profile]
  (m/rotate [0 (- (/ Math/PI 2.0)) 0]   ;; map +Z extrusion -> +Y
    (m/extrude-linear {:height len}
      profile)))

(defn ear-groove
  "One trapezoid rib (additive)."
  []
  (let [{:keys [trap length]} p/ear-grooves]
    (extrude-along-y length (trapezoid-profile trap))))

(defn ear-grooves
  "Array of trapezoid ribs starting at p/ear-grooves :pos, spaced along X."
  []
  (let [{:keys [count spacing pos]} p/ear-grooves
        {:keys [x y z]} pos]
    (apply m/union
           (for [i (range count)]
             (m/translate [(+ x (* i spacing)) y z]
               (ear-groove))))))

(defn ear-grooves-visual
  []
  (m/color [0.2 1 0.2 1]   ;; bright-ish green
    (ear-grooves)))
