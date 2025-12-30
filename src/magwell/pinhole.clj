(ns magwell.pinhole
  (:require [scad-clj.model :as m]
            [magwell.params :as p]))

(defn pinhole
  "3mm diameter through-hole that cuts the entire model."
  []
  (let [{:keys [diameter pos]} p/pinhole
        {:keys [x y z]} pos
        r (/ diameter 2.0)
        ;; overkill height so it always passes through
        h 200
        z0 (- z 100)]
    (m/translate [x y z0]
      (m/cylinder r h :center false))))

(defn pinhole-visual
  []
  (m/color [1 1 0 1]   ;; yellow = global cut
    (pinhole)))

(defn pinhole-2
  "Second 3mm diameter through-hole."
  []
  (let [{:keys [diameter pos]} p/pinhole-2
        {:keys [x y z]} pos
        r (/ diameter 2.0)
        h 200
        z0 (- z 100)]
    (m/translate [x y z0]
      (m/cylinder r h :center false))))

(defn pinhole-2-visual
  []
  (m/color [1 1 0 1]
    (pinhole-2)))
