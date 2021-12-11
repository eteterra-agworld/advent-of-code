(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

(defn bingo? [{:keys [board draw]}]
  (apply = (conj board (first draw))))

(defn filter-out [coll & {:keys [from]}]
  (filter (complement (into #{} coll)) from))

(defn score 
  "final score is sum of all unmarked numbers * last number drawn"
  [{:keys [board draw]}]
  (* (apply + (filter-out draw :from board)) (last draw)))

; define game state & game state reducer

; from a list of boards and a sequence of numbers, find the winning board. find the board score.

(defn answers []
  (-> "files/day4.txt" slurp str/split-lines)
  (println "todo"))