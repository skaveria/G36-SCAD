(ns magwell.io
  (:require [clojure.java.io :as io]
            [scad-clj.scad :as s]))

(defn write-scad!
  [path model]
  (io/make-parents path)
  (spit path (s/write-scad model))
  path)

(defn write-scad-with-import-cutter!
  "Write MODEL, but wrap it in a top-level difference() that also subtracts an
  imported STL cutter (with translate/rotate in OpenSCAD degrees).

  Assumes the STL is next to the output .scad file (your case: out/sidecutter.stl)."
  [path model {:keys [pos rot]} stl-filename]
  (io/make-parents path)
  (let [base-scad (s/write-scad model)
        cut-scad  (format
                   (str "translate([%s,%s,%s]) rotate([%s,%s,%s]) import(\"%s\");\n")
                   (:x pos) (:y pos) (:z pos)
                   (:x rot) (:y rot) (:z rot)
                   stl-filename)
        final     (str
                   "difference() {\n"
                   base-scad "\n"
                   cut-scad
                   "}\n")]
    (spit path final)
    path))


(defn write-scad-with-import-visual!
  "Write a debug SCAD that shows MODEL plus a colored imported STL (no subtraction)."
  [path model {:keys [pos rot]} stl-filename]
  (io/make-parents path)
  (let [base-scad (s/write-scad model)
        imp-scad  (format
                   (str "color([0,1,1,0.6]) translate([%s,%s,%s]) rotate([%s,%s,%s]) import(\"%s\");\n")
                   (:x pos) (:y pos) (:z pos)
                   (:x rot) (:y rot) (:z rot)
                   stl-filename)
        final     (str
                   "union() {\n"
                   base-scad "\n"
                   imp-scad
                   "}\n")]
    (spit path final)
    path))

(defn write-scad-with-import-cutters!
  [path model cutters]
  (io/make-parents path)
  (let [base-scad (s/write-scad model)
        cuts-scad (apply str
                         (for [{:keys [pos rot stl]} cutters]
                           (format
                            "translate([%s,%s,%s]) rotate([%s,%s,%s]) import(\"%s\");\n"
                            (:x pos) (:y pos) (:z pos)
                            (:x rot) (:y rot) (:z rot)
                            stl)))
        final     (str
                   "$fn = 48;\n\n"
                   "difference() {\n"
                   base-scad "\n"
                   cuts-scad
                   "}\n")]
    (spit path final)
    path))
