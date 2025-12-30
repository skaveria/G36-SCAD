(ns magwell.mag-rim
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn mag-rim-at
  "Rim cube placed at an explicit position map {:x :y :z}."
  [pos]
  (let [{:keys [x y z]} p/mag-rim-dims
        {px :x py :y pz :z} pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mirror-pos-z
  "Mirror a :center false cube position across the Z midplane of the outer block.
  Assumes `pos` is the cube's minimum corner position (its translate), and `dims`
  contains the cube's {:z ...} thickness."
  [pos dims]
  (let [midz (/ (:z p/outer-dims) 2.0)
        pz   (:z pos)
        dz   (:z dims)
        pz2  (- (* 2.0 midz) (+ pz dz))]
    (assoc pos :z pz2)))

(defn mag-rim
  "Primary rim feature (uses p/mag-rim-pos)."
  []
  (mag-rim-at p/mag-rim-pos))

(defn mag-rim-mirrored
  "Second rim feature mirrored across Z midplane."
  []
  (mag-rim-at (mirror-pos-z p/mag-rim-pos p/mag-rim-dims)))

(defn mag-rim-visual
  []
  (m/color [0 0 1 1]
    (mag-rim)))

(defn mag-rim-mirrored-visual
  []
  (m/color [0 0 1 1]
    (mag-rim-mirrored)))

(defn mag-rim-z
  "Secondary rim section that is long in Z (uses p/mag-rim-z-pos)."
  []
  (let [{:keys [x y z]} p/mag-rim-z-dims
        {px :x py :y pz :z} p/mag-rim-z-pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mag-rim-z-visual []
  (m/color [0 0 1 1]
    (mag-rim-z)))
