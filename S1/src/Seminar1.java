import Rules.Chessboard;
import Rules.Move;
import Rules.Rules;

import java.util.*;


class ChessboardMod
{

    static public int heuristicCalls = 0;

    Chessboard c;
    List<Move> moves = new ArrayList<>();

    private Double precalc_h = null;

    public List<Move> doneMoves()
    {
        return this.moves;
    }

    public ChessboardMod(Chessboard c)
    {
        this.c = c;
    }

    public ChessboardMod(Chessboard c, List<Move> moves)
    {
        this.c = c;
        this.moves = moves;
    }

    public ChessboardMod copy()
    {
        return new ChessboardMod(this.c.copy(), new ArrayList(this.moves));
    }

    public void makeMove(Move m)
    {
        this.c.makeMove(m);
        this.moves.add(m);
        this.precalc_h = null;
    }

    public ArrayList<Move> getMoves()
    {
        return this.c.getMoves();
    }

    public int getGameStatus()
    {
        return this.c.getGameStatus();
    }

    public int getMovesLeft()
    {
        return this.c.getMovesLeft();
    }

    public String getFEN()
    {
        return this.c.getFEN();
    }

    @Override
    public String toString() {
        return this.c.toString();
    }

    public double Heuristic()
    {
        if(this.precalc_h==null)
        {
            heuristicCalls++;
            //float n = distHeuristic();
            this.precalc_h = 4*(double) pokritostHeuristic() + (double)promocijaHeuristic2() + 1.5*distHeuristic();
        }
        return this.precalc_h;
    }

    public void printHeuristic()
    {
        if(promocijaHeuristic2()>-1) {
            int neki = promocijaHeuristic2();

            int rfg  =34;
        }

        System.out.println(4*pokritostHeuristic() + " " + promocijaHeuristic2() + " " + 1.5*distHeuristic() + " = " + this.Heuristic());
    }

    public double distHeuristic()
    {
        int[][] board = this.c.getBoard();
        int color = this.c.getColor();

        int kingi = 0;
        int kingj = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(board[i][j]*color==-6)
                {
                    kingi = i;
                    kingj = j;
                }
            }
        }

        double heuristic = 0;
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                float weight = 0;
                if(board[i][j]*color>0) {
                    ;
                    switch (Math.abs(board[i][j])) {
                        case Chessboard.QUEEN:
                            weight = 10;
                            break;

                        case Chessboard.ROOK:
                            weight = 6;
                            break;

                        case Chessboard.BISHOP:
                            weight = 5;
                            break;
                        case Chessboard.KNIGHT:
                            weight = 4;
                            break;
                        default:
                            weight=0;
                            break;
                    }
                    float d = (float) (Math.abs(kingi - i) + Math.abs(kingj - j));
                    heuristic += weight * (1 / d);
                    //System.out.println(d + " " + weight * (1 / d));
                }


            }
        }

        return heuristic;


    }

    public int promocijaHeuristic2()
    {
        //TODO check if possible

        int[][] board = this.c.getBoard();
        int color = this.c.getColor();



        int poz = 0;
        if(color == 1)
        {
            for (int i = 7; i >=0; i--)
            {
                for (int j = 0; j < 8; j++)
                {
                    if(board[i][j]==1)
                    {
                        if(i+1+this.c.getMovesLeft()>=8)
                            return i+1;
                        else
                            return 0;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if(board[i][j]==-1)
                    {
                        if(8-i+this.getMovesLeft()>=8)
                            return 8 - i;
                        else
                            return 0;
                    }
                }
            }
        }
        return 0;

    }

    public double pokritostHeuristic()
    {
        int[][] board = this.c.getBoard();

        int color = this.c.getColor();
        int kingx = 0;
        int kingy = 0;

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if(board[i][j]*color==-6)
                {
                    kingx = i;
                    kingy = j;
                }
            }
        }

        int[][] directions = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}, {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        double heuristic = 0;

        if(Rules.isCovered(board, kingx, kingy, color))
            heuristic++;

        for (int i = 0; i < directions.length; i++)
        {
            int[] direction = directions[i];
            int x = kingx + direction[0];
            int y = kingy + direction[1];

            if (x >= 0 && y >= 0 && x < 8 && y < 8)
            {
                if(board[x][y] * color < 0)
                {
                    heuristic+=0.5;
                    continue;
                }

                if(Rules.isCovered(board, x, y, color))
                {
                    heuristic++;
                    continue;
                }
            }
            else
            {
                //heuristic++;
            }
        }

        return heuristic;
    }
}

public class Seminar1
{
    public static Comparator<ChessboardMod> chessboardComparator = new Comparator<ChessboardMod>()
    {
        @Override
        public int compare(ChessboardMod o1, ChessboardMod o2)
        {
            return Double.compare(o2.Heuristic(), o1.Heuristic());
        }
    };

    public String solve(String fen)
    {
        int searched = 0;

        Chessboard cb = Chessboard.getChessboardFromFEN(fen);

        Set<String> visited = new HashSet<>();



        PriorityQueue<ChessboardMod> priorityQueue = new PriorityQueue<>(chessboardComparator);

        ChessboardMod cm = new ChessboardMod(cb);
        priorityQueue.add(cm);
        visited.add(cm.getFEN());

        while (!priorityQueue.isEmpty())
        {
            ChessboardMod cb1 = priorityQueue.poll();

            //System.out.println(cb1.Heuristic() + " " + cb1.moves.size());

            //Double h = cb1.Heuristic();
            //cb1.printHeuristic();

            searched++;

            for(Move move:cb1.getMoves())
            {
                ChessboardMod cbc = cb1.copy();
                cbc.makeMove(move);
                if(cbc.getGameStatus() == 2)
                {
                    System.out.println("CM");
                    System.out.println(cbc.doneMoves());
                    System.out.println(cbc.getFEN());
                    System.out.println("searched nodes:" + searched);
                    cbc.printHeuristic();
                    System.out.println("h cals:" + ChessboardMod.heuristicCalls);
                    return null;
                }

                if (cbc.getMovesLeft() > 0 && cbc.getGameStatus()!=1)
                {
                    if(!visited.contains(cbc.getFEN()))
                    {
                        priorityQueue.add(cbc);
                        visited.add(cbc.getFEN());
                    }
                }
            }
        }
        return null;
    }


    public static String studentId() {
        return "63140191";
    }
}
