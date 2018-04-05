import Rules.Chessboard;
import Rules.Move;
import Rules.Rules;
import sun.misc.IOUtils;

import java.io.*;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args)
    {
        //Chessboard cb = Chessboard.getChessboardFromFEN("rnbBkbnr/pp1p1ppp/8/8/3p4/8/PPP1PPPP/RN1QKBNR b 4");
        //ArrayList<Move> moves = cb.getMoves();
        //cb.makeMove(moves.get(0));
        //System.out.println(moves);
        //System.out.println(cb.getFEN() + " " + cb.getGameStatus());

        //moves = cb.getMoves();
        //cb.makeMove(moves.get(1));
        //System.out.println(moves);
        //System.out.println(cb.getFEN() + " " + cb.getGameStatus());

        //moves = cb.getMoves();
        //System.out.println(moves);
        //cb.makeMove(moves.get(6));
        //System.out.println(cb.getFEN() + " " + cb.getGameStatus());

        //moves = cb.getMoves();
        //System.out.println(moves);
        //cb.makeMove(moves.get(28));
        //System.out.println(cb.getFEN() + " " + cb.getGameStatus());

        //Chessboard cb = Chessboard.getChessboardFromFEN("rnbBkbnr/pp1p1ppp/8/8/3p4/8/PPP1PPPP/RN1QKBNR b 4");
        //ArrayList<Move> moves = cb.getMoves();
        //cb.makeMove(moves.get(0));

        //Chessboard c1 = cb.copy();
        //Chessboard c2 = cb.copy();

        //System.out.println(c1.getMoves());
        //System.out.println(c2.get);

        try (BufferedReader br = new BufferedReader(new FileReader("progressive_checkmates.csv"))) {
            String line;
            boolean f = true;
            while ((line = br.readLine()) != null) {
                if(f)
                    f=false;
                else
                {
                    String[] el = line.split(",");
                    Seminar1 solver = new Seminar1();

                    System.out.println();
                    System.out.println("Problem " + el[0]);
                    String output = solver.solve(el[1].replace("\"", ""));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Seminar1 solver = new Seminar1();
        //String input = getInput();
        //String output = solver.solve(input);
        //boolean correct = checkOutput(input, output);
        //System.out.println("Input: " + input);
        //System.out.println("Output: " + output);
        //System.out.println("Result: " + correct);
    }

    private static boolean checkOutput(String input, String output) {
        // TODO: Create a method that checks if all moves are legal and the end state is checkmate (use provided library)
        Chessboard cb = Chessboard.getChessboardFromFEN(input);
        return false;
    }

    private static String getInput() {
        // TODO: Create a parser for input files
        // For now return this example
        return "rnbqkbnr/pp1p1ppp/4p3/4P3/8/N7/PP3PPP/R1BqKBNR w 5";
    }
}
