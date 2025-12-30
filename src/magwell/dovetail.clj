(ns magwell.dovetail
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn dovetail-profile
  "2D X/Z profile, symmetric about X=0. T-shape (no taper)."
  []
  (let [{:keys [w1 w2 h1 h2]} p/dovetail
        z-stem (- h2 h1)
        x1 (/ w1 2.0)
        x2 (/ w2 2.0)]
    (m/polygon
     [[(- x2) 0]
      [x2 0]
      [x2 z-stem]
      [x1 z-stem]
      [x1 h2]
      [(- x1) h2]
      [(- x1) z-stem]
      [(- x2) z-stem]])))

(defn dovetail
  "Dovetail cutter solid (matches rotate([0,90,90]) linear_extrude(height=200))."
  []
  (let [{:keys [d pos]} p/dovetail
        {:keys [x y z]} pos
        a90 (/ Math/PI 2.0)]
    (m/translate [x y z]
      (m/rotate [0 a90 a90]
        (m/extrude-linear {:height d}
          (dovetail-profile))))))

(defn dovetail-visual
  []
  (m/color [0 1 1 1]
    (dovetail)))
