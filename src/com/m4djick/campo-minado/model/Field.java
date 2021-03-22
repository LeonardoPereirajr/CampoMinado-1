package model;

import exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Field

{

    private final int LINE;
    private final int COLUMN;

    private boolean opened = false;
    private boolean mine = false;
    private boolean marked = false;

    private List<Field> surrounds = new ArrayList<>();

     Field(int line, int column) {
        this.LINE = line;
        this.COLUMN = column;
    }



     boolean addNeighbour(Field neighbour) {
        boolean differentLine = LINE != neighbour.LINE;
        boolean differentColumn = COLUMN != neighbour.COLUMN;

        boolean onDiagonal = differentLine && differentColumn;

        int lineDif = Math.abs(neighbour.LINE - LINE);
        int columnDif = Math.abs(neighbour.COLUMN - COLUMN);

        int totalDif = lineDif + columnDif;

        if(onDiagonal && totalDif == 2) {
            surrounds.add(neighbour);
            return true;
        }

        else if(!onDiagonal && totalDif == 1) {
            surrounds.add(neighbour);
            return true;
        }

        return false;
    }

    void alterMark() {
         if(!opened) marked = !marked;
    }

    boolean open() {

         if(!opened && !marked) {
             opened = true;

             if(mine) {
                 throw new ExplosionException();
             }

             if(safeSurround()) {
                 surrounds.forEach(Field::open);
             }

             return true;
         }

         return false;
    }

    void makeMined() {
         mine = true;
    }

    public boolean isOpened() {
         return opened;
     }

    public boolean isMarked() { return marked; }

    Predicate<Field> isMined = (n) -> n.mine;

    boolean safeSurround() {

        return surrounds.stream().noneMatch(isMined);

    }

    public int getCOLUMN() {
        return COLUMN;
    }

    public int getLINE() {
        return LINE;
    }

    boolean goalReached() {
        boolean safe = mine && marked;
        boolean unlocked = !mine && opened;

        return safe || unlocked;
    }

    long minedNeighbours() {
        return surrounds.stream().filter(isMined).count();
    }

    void restart() {
        opened = false;
        mine = false;
        marked = false;
    }

    @Override
    public String toString() {
       if(marked) return "x";
       else if(opened && mine) return "*";
       else if(opened && minedNeighbours() > 0)
           return  Long.toString(minedNeighbours());
       else if(opened) return "_";
       else return "?";
    }

    public boolean isMine() {
        return mine;
    }

    void setOpened() {
        this.opened = true;
    }


}
