(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

; winning conditions
(defn bingo? [{:keys [board draw]}]
  (apply = (conj board (first draw))))

; board score
(defn score [{:keys [board draw]}] 0)

; define game state & game state reducer

; from a list of boards and a sequence of numbers, find the winning board. find the board score.

(defn answers []
  (-> "files/day4.txt" slurp str/split-lines)
  (println "todo"))