(ns magwell.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))

(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

(def dims {:x 90 :y 65 :z 38})

(defn block []
  (let [{:keys [x y z]} dims]
    (m/cube x y z :center false)))

(defn cutter []
  (m/translate [0 0 -1]
      (m/extrude-linear
       {:height 200}           ;; extrude through Y
       (m/polygon
        [[-1   -1]               ;; bottom-left
         [75  -1]               ;; bottom-right
         [75  0]              ;; top-right
         [-1  20]]))))        ;; top-left

(defn model []
  (m/difference (block) (cutter)))

(defn -main [& _]
  (binding [scad-clj.model/*center* false]
    (write-scad! "out/magwell.scad" (model))))
