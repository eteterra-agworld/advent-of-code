module Day1 where

import Data.List (transpose, tails)

timesTrue = length . filter (True==)
isConsecutiveNumberLarger coll = zipWith (<) coll $ tail coll
part1 = timesTrue . isConsecutiveNumberLarger

slidingWindows size coll = transpose $ take size $ tails coll
part2 coll = part1 . map sum $ slidingWindows 3 coll