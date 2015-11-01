# Task

Create a clojure-based RESTful API which has the following
functionality:

* An endpoint that takes an arbitrary amount of numbers and returns the
  only the odd ones.
  * Decide how the numbers are input.
  * Output JSON.

* Another endpoint that takes a matrix representing a
  noughts-and-crosses board and returns the winner information (or
  whether the match was a draw).
  * Bonus points for validating the input.

You choose the libraries that you think most suit the task.


##To start the server, run:

    lein ring server-headless

#Odd Numbers

##Example to test the /odd-numbers endpoint with GET:

    curl -X GET http://localhost:3000/odd-numbers?values=1,2,3,4,5,6,7

##Example to test the /odd-numbers endpoint with POST:

    curl -H "Content-Type: application/json" -X POST -d '{"source-list": [1, 22, 45, 66, 8723872, 3, 299]}' http://localhost:3000/odd-numbers

#Noughts and Crosses board

##Examples to test /noughts-and-crosses endpoint:

Winner: 1 ('X')

    curl -H "Content-Type: application/json" -X POST -d '{"board": [[1, 0, 0], [0, 1, 1], [1, 1, 1]]}' http://localhost:3000/noughts-and-crosses

Winner: 0 ('O')

    curl -H "Content-Type: application/json" -X POST -d '{"board": [[0, 0, 0], [0, 1, 1], [1, 1, 0]]}' http://localhost:3000/noughts-and-crosses

Draw

    curl -H "Content-Type: application/json" -X POST -d '{"board": [[1, 0, 0], [0, 1, 1], [1, 1, 0]]}' http://localhost:3000/noughts-and-crosses

Winner: 1 ('X') with a partially completed board (use "null" for empty spaces)

    curl -H "Content-Type: application/json" -X POST -d '{"board": [[1, 0, 0], ['null', 1, 0], ['null', 1, 0]]}' http://localhost:3000/noughts-and-crosses

Winner: 0 ('O') with a partially completed board (use "null" for empty spaces)

    curl -H "Content-Type: application/json" -X POST -d '{"board": [[0, 'null', 1], [0,'null','null'], [0,'null','null']]}' http://localhost:3000/noughts-and-crosses

