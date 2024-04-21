package ax.ha.tdd.chess.web;

import ax.ha.tdd.chess.engine.Game;
import ax.ha.tdd.chess.engine.GameImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChessController {

    Game game = new GameImpl();

    @GetMapping({"/", "/chess"})
    public String chess(Model model, @RequestParam(name = "move", required = false) String move) {
        if (move != null && !move.isEmpty()) {
            game.move(move);
        }
        model.addAttribute("player", new ColorView(game.getPlayerToMove()));
        model.addAttribute("lastMoveResult", game.getLastMoveResult());
        model.addAttribute("chessboard",
                new ChessboardView(game.getBoard()));
        return "chess";
    }

    @GetMapping({"/new"})
    public String newGame() {
        game = new GameImpl();
        return "redirect:/chess";
    }

}
