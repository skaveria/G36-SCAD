(ns magwell.ear-ridge
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn trapezoid-profile
  "2D trapezoid in X/Z, symmetric about X=0, resting on Z=0."
  [{:keys [w-bottom w-top h]}]
  (let [xb (/ w-bottom 2.0)
        xt (/ w-top 2.0)]
    (m/polygon
     [[(- xb) 0]
      [xb 0]
      [xt h]
      [(- xt) h]])))

(defn ear-ridge
  "Single trapezoid rib (additive).

  Intended OpenSCAD equivalent:

    translate([-4.8, 44, 3]) {
      rotate([0, -90, 90]) {
        linear_extrude(height=24.2) {
          polygon(points=[[-3,0],[3,0],[1.25,4],[-1.25,4]]);
        }
      }
    }
  "
  []
  (let [{:keys [trap length pos rot]} p/ear-ridge
        {:keys [x y z]} pos]
    (m/translate [x y z]
      (m/rotate rot
        (m/extrude-linear {:height length}
          (trapezoid-profile trap))))))

(defn ear-ridge-visual
  []
  (m/color [0.2 1 0.2 1]
    (ear-ridge)))
