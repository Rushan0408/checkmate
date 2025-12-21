package io.github.Rushan0408.checkmate.controller;

// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import java.util.Optional;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import io.github.Rushan0408.checkmate.dto.http.QueueRequest;

// import io.github.Rushan0408.checkmate.dto.http.QueueRequest;
// import io.github.Rushan0408.checkmate.dto.http.QueueResponse;
// import io.github.Rushan0408.checkmate.model.GameRoom;
// import io.github.Rushan0408.checkmate.model.Player;
// import io.github.Rushan0408.checkmate.state.AppState;

@RestController
@RequestMapping("/queue")
public class QueueController {

    // private final AppState appState;

    // public QueueController(AppState appState) {
    //     this.appState = appState;
    // }

    @PostMapping
    public String joinQueue() {
        return "working";
    }
}

