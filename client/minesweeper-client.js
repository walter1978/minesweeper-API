var baseUrl = "https://minesweeper-wat.herokuapp.com"
var apiVersion = "/api/v1"

function serverStatus(successCallback, failureCallback) {
    $.ajax({
        url: baseUrl + "/actuator/health",
        contentType: "application/json",
        dataType: 'json',
        method: "GET",
        crossDomain: true,
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function createBoard(userId, rows, columns, mines, successCallback, failureCallback) {
    var data = {
        "userId": userId,
        "rows": rows,
        "columns": columns,
        "mines": mines
    }

    $.ajax({
        url: baseUrl + apiVersion + "/boards/",
        contentType: "application/json",
        dataType: 'json',
        method: "POST",
        crossDomain: true,
        data: JSON.stringify(data),
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function pauseBoard(boardId, failureCallback) {
    $.ajax({
        url: baseUrl + apiVersion + "/boards/status/paused/" + boardId,
        contentType: "application/json",
        dataType: 'json',
        method: "PUT",
        crossDomain: true,
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function playBoard(boardId, failureCallback) {
    $.ajax({
        url: baseUrl + apiVersion + "/boards/status/playing/" + boardId,
        contentType: "application/json",
        dataType: 'json',
        method: "PUT",
        crossDomain: true,
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function cancelBoard(boardId, failureCallback) {
    $.ajax({
        url: baseUrl + apiVersion + "/boards/status/canceled/" + boardId,
        contentType: "application/json",
        dataType: 'json',
        method: "PUT",
        crossDomain: true,
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function updateCellStatus(boardId, x, y, action, successCallback, failureCallback) {
    var data = {
        "boardId": boardId,
        "x": x,
        "y": y,
        "action": action
    }

    $.ajax({
        url: baseUrl + apiVersion + "/boards/cell",
        contentType: "application/json",
        dataType: 'json',
        method: "PUT",
        crossDomain: true,
        data: JSON.stringify(data),
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}

function listUserBoards(userId, successCallback, failureCallback) {
    $.ajax({
        url: baseUrl + apiVersion + "/boards/user/" + userId,
        contentType: "application/json",
        dataType: 'json',
        method: "GET",
        crossDomain: true,
        success: function(response) {
            successCallback(response);
        },
        fail: function(response) {
            failureCallback(response);
        }
    });
}
