(ns bc-rest.handler
  (:use ring.util.response)
  (:require
            [clojure.string :as string]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.middleware.json :as middleware]))

(defn get-odd-values-urlparameters [parms]
  "Filter list and returns only odd numbers. GET version"
  (response {:odd-numbers (filter odd? (map #(Integer/parseInt %) (string/split (get (:params parms) :values) #",")))}))

(defn get-odd-values [body]
  "Filter list and returns only odd numbers. POST version"
  (response {:odd-numbers (filter odd? (body "source-list"))}))

(defn invalid-symbol? [symbol]
  "Checks if the provided symbol is valid as a noughts-and-crosses board entry. Valid values are 0 for noughts, 1 for crosses and 'null' for empyt spaces"
  (not (or (= symbol 1) (= symbol 0) (= symbol nil))))

(defn valid-board? [board]
  "Checks if the board is valid, i.e. contain only valid characters"
  (empty? (filter invalid-symbol? (flatten board))))

(defn get-diagonal1 [board]
  "Gets diagonal values of a 3x3 matrix (noughts-and-crosses board)"
  (cons (first (first board)) (cons (second (second board)) (cons (last (last board)) []))))

(defn get-diagonal2 [board]
  "Gets the antidiagonal values of a 3x3 matrix (noughts-and-crosses board)"
  (cons (last (first board)) (cons (second (second board)) (cons (first (last board)) []))))

(defn check-row [row]
  "Checks if the row contains only one character type. That means this is a winning row"
  (if (= 1 (count (frequencies row))) (first row)))

(defn check-diagonals [board]
  "Checks the two board diagonals and return tru if either one is a winning row"
  (or (check-row (get-diagonal1 board)) (check-row (get-diagonal2 board))))

(defn check-row-board [board rowindex]
  "Checks if the nth row (rowindex) of the board is a winning row"
  (check-row (nth board rowindex)))

(defn transpose [matrix]
  "Transposes the board matrix"
  (apply mapv vector matrix))

(defn check-column-board [board columnindex]
  "Checks if the nth column (columnindex) of the board is a winning column"
  (check-row-board (transpose board) columnindex))

(defn check-board-winner [board]
  "Returns the winner of a board. 0 means noughts win. 1 means crosses win. 'nil' means there is a draw"
  (or (check-row-board board 0) (check-row-board board 1) (check-row-board board 2)
    (check-column-board board 0) (check-column-board board 1)
    (check-column-board board 2) (check-diagonals board)))

(defn get-winner-information [body]
  "Analyses a noughts-and-crosses board and returns the winner"
  (let [board (body "board")]
   (if (not (valid-board? board)) (response {:winner "Sorry, this board is invalid. Please use 0 for 'O', 1 for 'X' and nil for empty spaces"})
   (if (nil? (check-board-winner board)) (response {:winner "It's a draw!"}) (response {:winner (str (check-board-winner board))})))))

(defroutes app-routes
  (context "/odd-numbers" [] (defroutes bc-routes
    (POST "/" {body :body} (get-odd-values body)))
    (GET "/" request (get-odd-values-urlparameters request)))
  (context "/noughts-and-crosses" [] (defroutes bc-routes
    (POST "/" {body :body} (get-winner-information body))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
    (middleware/wrap-json-body)
    (middleware/wrap-json-response)))
