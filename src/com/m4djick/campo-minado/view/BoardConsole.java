package view;

import exception.ExplosionException;
import exception.QuitException;
import model.Board;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class BoardConsole

{

    private final Board board;
    private final Scanner input = new Scanner(System.in);

    public BoardConsole(Board board) {
        this.board = board;
        executeGame();
    
    }

    private void executeGame() {

        try {
            boolean keepGoing = true;

            while (keepGoing) {
                gameLoop();
                
                System.out.println("Another match? (S/n)");
                if(input.nextLine().equalsIgnoreCase("n")) {
                    System.out.println("See you next time!");
                    keepGoing = false;
                }


            }


        }catch (QuitException e) {
            System.out.println("See you next time!");
        }finally {
            input.close();
        }

    }

    private void gameLoop()

    {

        try {
            while (!board.madeIt()) {
                System.out.println(board);

                String answer =
                        captureValue
                                ("Next position (x, y): ");

                Iterator<Integer> xy =
                Arrays
                        .stream(answer.split(","))
                        .map(p -> Integer.parseInt(p.trim()) + - 1).iterator();


                answer = captureValue("1: Open or 2: (un)mark: ");

                if(answer.equals("1")) {
                    board.open(xy.next(), xy.next());

                } else if(answer.equals("2")) {
                    board.alterMark(xy.next(), xy.next());
                }

            }

            System.out.println("Congrats. You won! :)");

        }catch (ExplosionException e) {
            System.out.println(board);
            System.out.println("Game Over! :(");


        }finally {
            board.restart();
        }


    }

    private String captureValue(String text)
    {
        System.out.print(text);
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("quit")) {
            throw new QuitException();
        }

        return answer;
    }

}
