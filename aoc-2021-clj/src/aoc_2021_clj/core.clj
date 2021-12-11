(ns aoc-2021-clj.core
  (:gen-class)
  (:require [clojure.string :as str]))

(def read-lines (comp str/split-lines slurp))
(def transpose (partial apply mapv vector))
(defn binary->decimal [x] (-> x str/join (Integer/parseInt 2)))

; part 1
(def most-common-key (comp first (partial apply max-key second)))
(def least-common-key (comp first (partial apply min-key second)))

(def gamma-rate-from (comp binary->decimal (partial map most-common-key)))
(def epsilon-rate-from (comp binary->decimal (partial map least-common-key)))

; part 2
(defn search-binary-numbers-by [predicate]
  (fn [numbers]
    (loop [remainder numbers index 0]
    (let [bits (map #(nth % index) remainder)
            bit (predicate (frequencies bits))
            filtered-values (filter #(= bit (nth % index)) remainder)]
        (if (= 0 (count filtered-values)) 
          (binary->decimal (last remainder))
          (recur filtered-values (inc index)))))))

(def tie? (comp (partial apply =) (partial map second)))
(def oxygen-generator-rating-from (search-binary-numbers-by #(if (tie? %) \1 (most-common-key %))))
(def co2-scrubber-rating-from (search-binary-numbers-by #(if (tie? %) \0 (least-common-key %))))

(defn -main []
  (let [input (read-lines "files/day3.txt")
        bit-frequencies (map frequencies (transpose input))]
    (println "Answer 1:" (* (epsilon-rate-from bit-frequencies) (gamma-rate-from bit-frequencies)))
    (println "Answer 2:" (* (oxygen-generator-rating-from input) (co2-scrubber-rating-from input)))))