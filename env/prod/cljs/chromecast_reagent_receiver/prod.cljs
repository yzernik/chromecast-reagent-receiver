(ns chromecast-reagent-receiver.prod
  (:require [chromecast-reagent-receiver.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
