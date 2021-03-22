import model.Board;
import view.BoardConsole;

public class Application

{

    public static void main(String[] args)

    {

        Board board = new Board(10,10,10);

        BoardConsole boardConsole = new BoardConsole(board);


    }


}
