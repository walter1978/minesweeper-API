# Minesweeper (code challenge)

# Tech stack
  - Java 8
  - Spring Boot
  - H2 in memory database
  - Gradle
  
# Run service
./gradlew bootRun

# Run unit tests / integration tests
./gradlew test

# Endpoints
- Create board
###### Description:
Creates a new board ready to play. This endpoint is called when the user starts a new game.
Returns a board with "PLAYING" status.
```
- METHOD
POST
- URL
http://localhost:9090/api/v1/boards/
- HEADERS
Content-Typeapplication/json
- BODY
{
    "userId": 10,
    "rows": 10,
    "columns": 10,
    "mines": 2
}
- SUCCESS RESPONSE CODE
201
- RESPONSE
{
    "boardId": 1,
    "userId": 10,
    "startTime": "2020-07-10T16:11:18.382Z",
    "endTime": null,
    "lastTimePlayed": "2020-07-10T16:11:18.382Z",
    "totalTime": 0,
    "status": "PLAYING",
    "completed": false,
    "rows": 10,
    "columns": 10,
    "mines": 2,
    "visibleCells": 0,
    "cells": [
        [
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
...
```
- Pause board
###### Description:
Pauses a board.
The board status is set to "PAUSED".
```
- METHOD
PUT
- URL
http://localhost:9090/api/v1/boards/status/paused/{boardId}
- HEADERS
Content-Typeapplication/json
- BODY
- SUCCESS RESPONSE CODE
200
- RESPONSE
```
- Cancel board
###### Description:
Cancels a board. This endpoint is called when the user wants to quit the game and not save progress.
The board status is set to "CANCELED".
```
- METHOD
PUT
- URL
http://localhost:9090/api/v1/boards/status/canceled/{boardId}
- HEADERS
Content-Typeapplication/json
- BODY
- SUCCESS RESPONSE CODE
200
- RESPONSE
```
- Play board
###### Description:
Plays a previously paused board. 
This endpoint is called when the user wants to continue a paused game. 
Returns the board with "PLAYING" status.
```
- METHOD
POST
- URL
http://localhost:9090/api/v1/boards/status/playing/{boardId}
- HEADERS
Content-Typeapplication/json
- BODY
- SUCCESS RESPONSE CODE
200
- RESPONSE
{
    "boardId": 1,
    "userId": 10,
    "startTime": "2020-07-10T16:11:18.382Z",
    "endTime": null,
    "lastTimePlayed": "2020-07-10T16:11:18.382Z",
    "totalTime": 0,
    "status": "PLAYING",
    "completed": false,
    "rows": 10,
    "columns": 10,
    "mines": 2,
    "visibleCells": 0,
    "cells": [
        [
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
...
```
- Update cell
###### Description:
Changes the status of a cell. 
This endpoint is called thwn the user has selected a cell to be revealed / marked with a red flag or a question mark.
The board is updated and returned.
```
- METHOD
PUT
- URL
http://localhost:9090/api/v1/boards/cell
- HEADERS
Content-Typeapplication/json
- BODY
{
    "boardId": 5,
    "x": 3,
    "y": 4,
    "action": "REVEAL"
}
- SUCCESS RESPONSE CODE
200
- RESPONSE
{
    "boardId": 1,
    "userId": 10,
    "startTime": "2020-07-10T16:11:18.382Z",
    "endTime": null,
    "lastTimePlayed": "2020-07-10T16:11:18.382Z",
    "totalTime": 0,
    "status": "PLAYING",
    "completed": false,
    "rows": 10,
    "columns": 10,
    "mines": 2,
    "visibleCells": 0,
    "cells": [
        [
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
            {
                "mine": false,
                "minesAround": 0,
                "visible": false,
                "redFlag": false,
                "quotationMark": false
            },
...
```
- List user boards
###### Description:
Lists all the boards for a user. 
```
- METHOD
GET
- URL
http://localhost:9090/api/v1/boards/user/{userId}
- HEADERS
Content-Typeapplication/json
- BODY
- SUCCESS RESPONSE CODE
200
- RESPONSE
[
    {
        "boardId": 1,
        "userId": 10,
        "startTime": "2020-07-10T16:11:18.382Z",
        "endTime": null,
        "lastTimePlayed": "2020-07-10T16:17:47.374Z",
        "totalTime": 0,
        "status": "PAUSED",
        "completed": false
    }
]
```
- Health check
###### Description:
Used to determine if the service is up. 
```
- METHOD
GET
- URL
https://minesweeper-wat.herokuapp.com/actuator/health
- HEADERS
- BODY
- SUCCESS RESPONSE CODE
200
- RESPONSE
{
  "status": "UP"
}
```

# Javascript client
/client/minesweeper-client.js

# Heroku link
https://minesweeper-wat.herokuapp.com

# Notes
## Possible improvements
- Return two matrices of numbers (data, visibility) to represent the board from the endpoints in order to reduce the network traffic.
- Store the board in the db as two matrices of numbers (data, visibility) in order to reduce the data size, but once it is restored from the db translate it into a matrix of Cells to keep an "object oriented" coding style.
- Full users handling: login, CRUD, etc
- Security: enable HTTPS, generate token on user login, check token on every request, etc
