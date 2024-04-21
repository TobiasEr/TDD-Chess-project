package ax.ha.tdd.chess.web;

import ax.ha.tdd.chess.engine.pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ChessboardView implements Iterable<List<ChessPieceView>> {

    final List<List<ChessPieceView>> board;

    public ChessboardView(Iterable<ChessPiece[]> chessboard) {
       board = new ArrayList<>();

       for (ChessPiece[] chessPieces : chessboard) {
           board.add(Arrays.stream(chessPieces)
                   .map(x -> {if (x == null) {return null;} else {return new ChessPieceView(x);}})
                   .toList());
       }
    }

    @Override
    public Iterator<List<ChessPieceView>> iterator() {
        return board.iterator();
    }
}
