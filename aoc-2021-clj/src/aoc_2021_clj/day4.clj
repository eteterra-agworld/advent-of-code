(ns aoc-2021-clj.day4
  (:require [clojure.string :as str]))

(defn filter-out [draw from]
  (filter (complement (into #{} draw)) from))

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

(defn horizontal-win? [board draw]
  (entire-line-marked-off? (rows-of board) draw))

(defn vertical-win? [board draw]
  (entire-line-marked-off? (columns-of board) draw))

(defn bingo? [{:keys [board draw]}]
  (or (horizontal-win? board draw) (vertical-win? board draw)))

(def bingo?-memo (memoize bingo?))

(defn play [boards numbers]
  (loop [draw []]
    (let [winners (filter #(bingo?-memo {:board % :draw draw}) boards)]
      (if (empty? winners)
        (recur (take (-> draw count inc) numbers))
        [winners draw]))))

(defn score [{:keys [board draw]}]
  (* (apply + (filter-out draw board)) (last draw)))

; parse input
(def read-file (comp str/split-lines slurp))

(defn lines
  "split input file into groups based on empty lines"
  [input]
  (->> (partition-by empty? input) (remove #(empty? (first %)))))

(defn parse-board
  "parse list of numbers from multiple lines representing rows of a single board"
  [board]
  (map #(Integer/parseInt %) (str/split (str/join, " " (map str/trim board)) #"\s+")))

(defn parse
  "parse input into map of draw numbers and boards"
  [lines]
  {:numbers (map #(Integer/parseInt %) (str/split (first (first lines)) #",")) :boards (map parse-board (rest lines))})

; part 1
(defn first-board-to-win [boards numbers]
  (let [[winners draw] (play boards numbers)]
    (score {:board (first winners) :draw draw})))

; part 2
(defn last-board-to-win [all-boards numbers]
  (loop [boards all-boards]
    (let [[winners draw] (play boards numbers)]
      (if (= 1 (count boards))
        (score {:board (first winners) :draw draw})
        (recur (filter-out winners boards))))))

(defn answers []
  (let [{numbers :numbers boards :boards} (-> "files/day4.txt" read-file lines parse)]
    (println "Answer 1:" (first-board-to-win boards numbers))
    (println "Answer 2:" (last-board-to-win boards numbers))))