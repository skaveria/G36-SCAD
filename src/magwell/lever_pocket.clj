(ns magwell.lever-pocket
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn lever-pocket-profile
  "2D profile in X/Z. Symmetric about X=0.

  Intended shape:
  - width w2 for bottom (h2-h1) units
  - width w1 for top h1 units
  - immediate step (no slant)."
  []
  (let [{:keys [w1 w2 h1 h2]} p/lever-pocket
        z-wide (- h2 h1)          ;; bottom section height (e.g. 9)
        x1 (/ w1 2.0)             ;; half-width of narrow section (e.g. 10)
        x2 (/ w2 2.0)]            ;; half-width of wide section (e.g. 14)
    (m/polygon
     [[(- x2) 0]                  ;; bottom wide rectangle
      [x2 0]
      [x2 z-wide]
      [x1 z-wide]                 ;; step in to narrow
      [x1 h2]                     ;; up to top
      [(- x1) h2]
      [(- x1) z-wide]
      [(- x2) z-wide]])))         ;; step out to wide


(defn extrude-along-y
  [depth profile]
  (m/rotate [0 (- (/ Math/PI 2.0)) 0]
    (m/extrude-linear {:height depth}
      profile)))

(defn lever-pocket
  "Lever pocket cutter solid (depth into +Y)."
  []
  (let [{:keys [d pos]} p/lever-pocket
        {:keys [x y z]} pos]
    (m/translate [x y z]
      (extrude-along-y d (lever-pocket-profile)))))

(defn lever-pocket-visual
  []
  (m/color [1 0 1 1]
    (lever-pocket)))
