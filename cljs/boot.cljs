(ns boot
  (:require [goog.events]
            [goog.net.XhrIo]
            [goog.Uri.QueryData]))

(defn ^:export ping [end]
  (let [request (goog.net.XhrIo.)]
    (.send request (str "/" end) "GET")))

(def ^:export scale-factor 0.859375)
(def ^:export timing (js-obj "t" 50))

(defn image [src onload]
  (let [img (js/Image.)]
    (set! (.-onload img) (partial onload img))
    (.setAttribute img "src" src)
    img))

(defn ^:export main []
  (log "main")
  (let [can (.getElementById js/document "snap")
        context (.getContext can "2d")]
    (.scale context scale-factor scale-factor)
    ((fn f []
       (image (str "/snap.jpg?" (.random js/Math))
              (fn [img]
                (js/setTimeout f 50)
                (.drawImage context img 0 0)))))))

(defn ^:export led-flip []
  (ping "pin/gpio2_9"))
