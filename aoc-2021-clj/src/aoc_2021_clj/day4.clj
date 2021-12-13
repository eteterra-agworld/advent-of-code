(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

(defn filter-out [coll from]
  (filter (complement (into #{} coll)) from))

; board
(defn size-of [board] (-> (count board) Math/sqrt int))
(defn rows-of [board] (partition (size-of board) board))
(defn columns-of [board]
  (loop [columns [] shift 0]
    (if (< shift (size-of board))
      (recur (conj columns (take-nth (size-of board) (drop shift board))) (inc shift))
      columns)))

; win conditions
(defn entire-line-marked-off? [lines draw]
  (->> lines (map #(filter-out draw %)) (some empty?) (boolean)))

(defn horizontal-win? [board draw] (entire-line-marked-off? (rows-of board) draw))
(defn vertical-win? [board draw] (entire-line-marked-off? (columns-of board) draw))

(defn bingo? [{:keys [board draw]}]
  (or
   (horizontal-win? board draw)
   (vertical-win? board draw)))

; final score
(defn score 
  "final score is sum of all unmarked numbers * last number drawn"
  [{:keys [board draw]}]
  (* (apply + (filter-out draw board)) (last draw)))

; define game state & game state reducer

; from a list of boards and a sequence of numbers, find the winning board. find the board score.

(defn answers []
  (-> "files/day4.txt" slurp str/split-lines)
  (println "todo"))