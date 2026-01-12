We CAN use @SubsribeMapping for matchmaking instead of @Matchmaking since this response will only ever be sent once

Why did i had to use custom HandShakeInterceptor?

Change the principal from player username to player id


----------------------------------------------------------------------------

ToDo - 
1.  
"/app/game":

    Trigger: client sends a move

    Payload: { "from": "e2", "to": "e4" }


Responsibilities:

    Validate turn

    Validate legality

    Apply move

    Update FEN

    Switch turn

    Broadcast update


---------------------------------------------------------------------------

2.
"/user/queue/game":

    Sent after every valid move

Payload: 

    {
        "newMove": true,
        "fen": "rnbqkbnr/pppppppp/..."
    }

---------------------------------------------------------------------------


