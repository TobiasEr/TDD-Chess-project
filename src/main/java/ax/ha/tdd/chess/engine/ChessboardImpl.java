package ax.ha.tdd.chess.engine;

import ax.ha.tdd.chess.engine.pieces.*;

import java.util.Iterator;
import java.util.List;

public class ChessboardImpl implements Chessboard {
    private final ChessPiece[][] board = new ChessPieceBase[8][8];
    private int movesMade = 0;

    public static ChessboardImpl startingBoard() {
        final ChessboardImpl chessboard = new ChessboardImpl();

        chessboard.withMirroredPiece(PieceType.PAWN, List.of(0, 1, 2, 3, 4, 5, 6, 7), 1)
                .withMirroredPiece(PieceType.ROOK, List.of(0, 7), 0)
                .withMirroredPiece(PieceType.KNIGHT, List.of(1, 6), 0)
                .withMirroredPiece(PieceType.BISHOP, List.of(2, 5), 0)
                .withMirroredPiece(PieceType.QUEEN, List.of(3), 0)
                .withMirroredPiece(PieceType.KING, List.of(4), 0);
        return chessboard;
    }

    public ChessPiece getPieceAt(final Square square) {
        return board[square.getY()][square.getX()];
    }

    public void addPiece(final ChessPiece chessPiece) {
        board[chessPiece.getLocation().getY()][chessPiece.getLocation().getX()] = chessPiece;
    }

    public void removePieceAt(Square square) {
        board[square.getY()][square.getX()] = null;
    }

    @Override
    public King getOpponentKing(Color color) {
        King opponentKing = null;
        for (ChessPiece[] row : this) {
            for (ChessPiece piece : row) {
                if (piece != null && piece.getType() == PieceType.KING && piece.getColor() != color) {
                    opponentKing = (King) piece;
                }
            }
        }
        return opponentKing;
    }

    @Override
    public void increaseMovesMade() {
        movesMade++;
    }

    @Override
    public int getMovesMade() {
        return movesMade;
    }

    public void resetMovesMade() {
        movesMade = 0;
    }

    @Override
    public void movePiece(ChessPiece piece, Square destination) {
        Square location = piece.getLocation();
        piece.setLocation(destination);
        addPiece(piece);
        removePieceAt(location);
    }

    /**
     * Helper method to initialize chessboard with {@link ChessPiece}.
     * Basically mirrors all added pieces for both players.
     * When all pieces have been implemented, this should be replaced with the proper implementations.
     *
     * @param pieceType    pieceType
     * @param xCoordinates xCoordinates
     * @param yCoordinate  yCoordinateOffset
     * @return itself, like a builder pattern
     */
    private ChessboardImpl withMirroredPiece(final PieceType pieceType,
                                             final List<Integer> xCoordinates, final int yCoordinate) {
        xCoordinates.forEach(xCoordinate -> {
            switch (pieceType) {
                case PAWN -> {
                    addPiece(new Pawn(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new Pawn(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
                case ROOK -> {
                    addPiece(new Rook(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new Rook(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
                case BISHOP -> {
                    addPiece(new Bishop(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new Bishop(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
                case KNIGHT -> {
                    addPiece(new Knight(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new Knight(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
                case QUEEN -> {
                    addPiece(new Queen(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new Queen(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
                case KING -> {
                    addPiece(new King(Color.BLACK, new Square(xCoordinate, yCoordinate)));
                    addPiece(new King(Color.WHITE, new Square(xCoordinate, 7 - yCoordinate)));
                }
            }
        });
        return this;
    }

    @Override
    public Iterator<ChessPiece[]> iterator() {
        return List.of(board).iterator();
    }
}
