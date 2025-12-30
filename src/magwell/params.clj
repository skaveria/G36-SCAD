(ns magwell.params)

(def outer-dims {:x 95 :y 65 :z 38})

(def mag-inner-dims {:x 65 :y 120 :z 27})
(def mag-inner-pos  {:x 6  :y -20 :z 5.5})

(def mag-side-channel-dims {:x 5 :y 120 :z 15})
(def mag-side-channel-pos  {:x 70 :y -20 :z 11.5})

(def mag-rim-dims {:x 120 :y 20 :z 20})
(def mag-rim-pos {:x -15 :y 59.5 :z 35})

(def mag-rim-z-dims
  {:x 20    ;; thickness in X
   :y 20    ;; thickness in Y
   :z 120}) ;; long in Z

(def mag-rim-z-pos
  {:x 85    ;; tweak by eye
   :y 59.5    ;; positive Y
   :z -15}) ;; start before so it spans Z fully

(def mag-ear
  {:diameter 22
   :height   38
   ;; placement — tweak by eye
   :pos {:x -5   ;; placeholder, adjust once visualized
         :y 54
         :z 0}})

(def lever-pocket
  {:w1 20
   :w2 27
   :h1 15
   :h2 25
   :d  10
   :pos {:x 80 :y -1 :z 19}})

(def pinhole
  {:diameter 3
   ;; tweak these freely
   :pos {:x 75.5
         :y 15
         :z 0}})

(def pinhole-2
  {:diameter 3
   ;; tweak freely
   :pos {:x 86.5
         :y 30
         :z 0}})

(def ear-shelf
  {:dims {:x 60   ;; long in X
          :y 15
          :z 15}
   ;; manual world-space placement (tweak these)
   :pos {:x -58
         :y 50
         :z 11.5}})


(def dovetail
  {:w1 32
   :w2 25
   :h1 3
   :h2 10
   :d  200
   :pos {:x 95 :y -20 :z 19}})
