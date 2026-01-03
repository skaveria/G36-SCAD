(ns magwell.mag-inner
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn mag-inner-cube
  []
  (let [{:keys [x y z]} p/mag-inner-dims
        {px :x py :y pz :z} p/mag-inner-pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mag-side-channel-cube
  []
  (let [{:keys [x y z]} p/mag-side-channel-dims
        {px :x py :y pz :z} p/mag-side-channel-pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mag-inner-cutter
  []
  (m/union
   (mag-inner-cube)
   (mag-side-channel-cube)))
