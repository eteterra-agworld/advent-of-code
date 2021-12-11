(ns aoc-2021-clj.day3
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
(defn filter-at [index predicate coll]
    (let [bit (->> coll (map #(nth % index)) frequencies predicate)]
      (filter #(= bit (nth % index)) coll)))

(defn search-input [predicate input]
  (loop [values input index 0]
    (let [filtered-values (filter-at index predicate values)]
      (if (= 0 (count filtered-values))
        (binary->decimal (last values))
        (recur filtered-values (inc index))))))

(def equal-values? (comp (partial apply =) (partial map second)))
(def oxygen-generator-rating-from (partial search-input #(if (equal-values? %) \1 (most-common-key %))))
(def co2-scrubber-rating-from (partial search-input #(if (equal-values? %) \0 (least-common-key %))))

(defn answers []
  (let [input (read-lines "files/day3.txt")
        bit-frequencies (map frequencies (transpose input))]
    (println "Answer 1:" (* (epsilon-rate-from bit-frequencies) (gamma-rate-from bit-frequencies)))
    (println "Answer 2:" (* (oxygen-generator-rating-from input) (co2-scrubber-rating-from input)))))