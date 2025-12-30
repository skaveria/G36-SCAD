(ns magwell.core
  (:require [scad-clj.model :as m]
            [scad-clj.scad  :as s]
            [clojure.java.io :as io]))



(defn write-scad! [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

;; Units: mm (convention)
(def dims
  {:height 38
   :width  90
   :depth  65})

(defn envelope []
  (let [{:keys [width depth height]} dims]
    (m/cube width depth height)))

(defn -main [& _]
  (binding [scad-clj.model/*center* false]
    (write-scad! "out/magwell.scad" (envelope))))
