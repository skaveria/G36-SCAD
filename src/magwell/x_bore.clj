(ns magwell.x-bore
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn x-bore
  "Big cylindrical cutter oriented along +X."
  []
  (let [{:keys [diameter length pos]} p/x-bore
        {:keys [x y z]} pos
        r (/ diameter 2.0)
        a90 (/ Math/PI 2.0)]
    (m/translate [x y z]
      (m/rotate [0 a90 0]                 ;; Z-axis cylinder -> X-axis
        (m/cylinder r length :center true))))) ;; center=true is handy for through cuts

(defn x-bore-visual
  []
  (m/color [1 0.5 0 1]
    (x-bore)))
