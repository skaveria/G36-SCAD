(ns magwell.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))

(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

;; --- Dimensions (match the working SCAD) -------------------------------

(def outer-dims {:x 90 :y 65 :z 38})

(def mag-inner-dims {:x 65 :y 120 :z 27})
(def mag-inner-pos  {:x 6  :y -20 :z 5.5})

(def mag-side-channel-dims {:x 4 :y 120 :z 15})
(def mag-side-channel-pos  {:x 70 :y -20 :z 11.5})

;; --- Geometry ----------------------------------------------------------

(defn outer-block []
  (let [{:keys [x y z]} outer-dims]
    (m/cube x y z :center false)))

(defn mag-slant-cutter []
  (m/translate [0 0 -1]
    (m/extrude-linear {:height 200}
      (m/polygon
       [[-1 -1]
        [75 -1]
        [75  0]
        [-1 20]]))))

(defn shell-with-slant-cut []
  (m/difference
   (outer-block)
   (mag-slant-cutter)))

(defn mag-inner-cube []
  (let [{:keys [x y z]} mag-inner-dims
        {px :x py :y pz :z} mag-inner-pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mag-side-channel-cube []
  (let [{:keys [x y z]} mag-side-channel-dims
        {px :x py :y pz :z} mag-side-channel-pos]
    (m/translate [px py pz]
      (m/cube x y z :center false))))

(defn mag-inner-cutter []
  (m/union
   (mag-inner-cube)
   (mag-side-channel-cube)))

(defn mag-inner-visual []
  (m/color [1 0 0 1]
    (mag-inner-cutter)))

(defn model []
  (m/difference
   (shell-with-slant-cut)
   (mag-inner-cutter)))

(defn -main [& _]
  (binding [scad-clj.model/*center* false]
    (write-scad! "out/magwell.scad" (model))))
