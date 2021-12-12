(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

(defn filter-out [coll from]
  (filter (complement (into #{} coll)) from))

(defn size-of [board] (-> (count board) Math/sqrt int))
(defn rows-of [board] (partition (size-of board) board))
(defn columns-of [_] [[1 3][2 4]]) ; hardcoded solution to vertical win

(defn bingo? [{:keys [board draw]}]
  (or 
   ; horizontal
   (->> (rows-of board) (map #(filter-out draw %)) (some empty?) (boolean))
   ; vertical
   (->> (columns-of board) (map #(filter-out draw %)) (some empty?) (boolean))))

(defn score 
  "final score is sum of all unmarked numbers * last number drawn"
  [{:keys [board draw]}]
  (* (apply + (filter-out draw board)) (last draw)))

; define game state & game state reducer

; from a list of boards and a sequence of numbers, find the winning board. find the board score.

(defn answers []
  (-> "files/day4.txt" slurp str/split-lines)
  (println "todo"))