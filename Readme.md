Following the style of Quizz or Trivia games, where "who knows more" wins, we are going to build an app to measure participants' knowledge about movies and series. For this, we will use as reference the content available on the IMDB website and consume this data to our database via Rest API.

The application should, when starting, check if there are movies loaded in the file "movies.csv". If it is empty, fill in the data of the movies that will be available to play. To play, you must be registered in the file "players.csv". All you need is a username (5-10) and password (4-8), both letters and numbers, no special characters or spaces. For security, the password must be encrypted with SHA-1.

In each move request, the username and password will be informed, and the response will contain two movies/series. The player must answer the id of the one he believes has the highest score on IMDB. If he gets it right, he must move to the next round. If he misses, he loses "one life" - of the three initially available. When you reset the "lives", your final score listed among the ranking of competitors will be displayed.

The progress of each player will be stored in the file "games.csv", containing login and current progress in the format "x/y" where x=hits and y=total. The history will be in "ranking.csv" sorted from highest score to lowest.

Flow
Start "movies.csv" file with movies available to play. You choose how to do it.
Start "players.csv" file with players able to play. You choose how to do it.
On each turn, a GET request for "/quizz" will be made. The answer contains two movies.
The player's response will be sent as a POST request to "/quizz" informing username, password and id of the winning movie/series. Everything must be encapsulated in the "request body". The endpoint responds true/false to the result and updates the progress in "games.csv".
When the player loses, the final progress is written in "ranking.csv", being careful to keep it in order. The ranking can be accessed under "/ranking"