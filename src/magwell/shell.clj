(ns magwell.shell
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn outer-block
  []
  (let [{:keys [x y z]} p/outer-dims]
    (m/cube x y z :center false)))

(defn mag-slant-cutter
  []
  (m/translate [0 0 -1]
    (m/extrude-linear {:height 200}
      (m/polygon
       [[-1 -1]
        [75 -1]
        [75  0]
        [-1 20]]))))

(defn shell-with-slant-cut
  []
  (m/difference
   (outer-block)
   (mag-slant-cutter)))
