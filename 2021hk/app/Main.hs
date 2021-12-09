module Main where
import System.IO

import Data.List(transpose, group, sort)

-- cheating a little, this will go bang if it can't read an int from a string
-- explicitly cast return type to Int so we don't have to deal with it
read' :: String -> Int
read' =  read

binaryToDecimal n = 2 * binaryToDecimal (div n 10) + mod n 10

-- cheating again, haskell wants me to say what happens when the list is empty or missing values

main = do 
    let i = ["00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010"]
    let r = map (group . sort) $ transpose i

    let gammaRate = binaryToDecimal . read' $ map (\(x:y:xs) -> if length x < length y then head x else head y) r
    let epsilonRate = binaryToDecimal . read' $ map (\(x:y:xs) -> if length x > length y then head x else head y) r

    print $ gammaRate * epsilonRate
