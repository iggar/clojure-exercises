(ns bc-rest.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [bc-rest.handler :refer :all]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 404))
      (is (= (:body response) "Not Found"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))

  (testing "odd-numbers route"
    (let [response (app (mock/request :get "/odd-numbers?values=1,2,3,4,5,6,7"))]
      (is (= (:status response) 200))))

  (testing "noughts and crosses invalid board"
    (let [board [['Z', 1, 0] [0, 0, 1] [0, 0, 1]]]
    (is (not (valid-board? board)))))

  (testing "noughts and crosses full valid board with result = nil (draw)"
    (let [board [[1, 0, 0] [0, 1, 1] [1, 1, 0]]]
    (is (valid-board? board))
    (is (nil? (check-board-winner board)))))

  (testing "noughts and crosses full valid board with result = 0 (noughts win) on row"
    (let [board [[1, 0, 1] [0, 1, 0] [0, 0, 0]]]
    (is (valid-board? board))
    (is (= 0 (check-board-winner board)))))

  (testing "noughts and crosses full valid board with result = 0 (noughts win) on diagonal"
    (let [board [[1, 1, 0] [0, 0, 1] [0, 0, 1]]]
    (is (valid-board? board))
    (is (= 0 (check-board-winner board)))))

  (testing "noughts and crosses full valid board with result = 1 (crosses win) on column"
    (let [board [[1, 1, 0] [1, 0, 1] [1, 0, 0]]]
    (is (valid-board? board))
    (is (= 1 (check-board-winner board)))))

  (testing "noughts and crosses full valid board with result = 1 (crosses win) on diagonal"
    (let [board [[1, 0, 0] [0, 1, 1] [1, 0, 1]]]
    (is (valid-board? board))
    (is (= 1 (check-board-winner board)))))

  (testing "noughts and crosses partially completed valid board with result = nil (draw)"
    (let [board [[0, 1, 1] [1, nil, 1] [0, 0, nil]]]
    (is (valid-board? board))
    (is (nil? (check-board-winner board)))))

  (testing "noughts and crosses partially completed valid board with result = 0 (noughts win)"
    (let [board [[1, 0, 0] [nil, 1, 0] [nil, 1, 0]]]
    (is (valid-board? board))
    (is (= 0 (check-board-winner board))))))

