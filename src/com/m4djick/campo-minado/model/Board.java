package model;

import exception.ExplosionException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board

{

    private final int lines;
    private final int columns;
    private final int mines;

    public final List<Field> fields = new ArrayList<>();

    public Board(int lines, int columns, int mines) {
        this.lines = lines;
        this.columns = columns;
        this.mines = mines;

        generateFields();
        setNeighbours();
        sortMines();
    }

    public void open(int line, int column) {
        try{
            Predicate<Field> matchPosition
                    = f -> {
                return f.getLINE() == line && f.getCOLUMN() == column;
            };


            fields.parallelStream()
                    .filter(matchPosition)
                    .findFirst().ifPresent(Field::open);

        }catch (ExplosionException e){
            fields.parallelStream()
                    .filter(Field::isMine)
                    .forEach(f -> f.setOpened());
            throw e;
        }
    }

    public void alterMark(int line, int column) {
        Predicate<Field> matchPosition
                = f -> {
            return f.getLINE() == line && f.getCOLUMN() == column;
        };


        fields.parallelStream()
                .filter(matchPosition)
                .findFirst().ifPresent(Field::alterMark);
    }

/*    private void sortMines() {

        for(int i=0; i<mines; i++) {
            double a = Math.random();
            double b = Math.random();

            int parameterLines = (int) Math.pow(10, Integer.toString(lines).length());
            int parameterColumns = (int) Math.pow(10, Integer.toString(columns).length());

            int line = (int) a*parameterLines;
            int column = (int) b*parameterColumns;

            for(Field f: fields) {
                if(f.getLINE() == line && f.getCOLUMN() == column) f.makeMined();
            }
        }

    }*/

    private void sortMines() {
        long minesSoFar = 0;

        Predicate<Field> mined = Field::isMine;

        while (minesSoFar<mines) {

            int random = (int) (Math.random()*fields.size());

            fields.get(random).makeMined();

            minesSoFar = fields.stream().filter(mined).count();
        }
    }

    public boolean madeIt() {
        return fields.stream().allMatch(Field::goalReached);
    }

    public void restart() {
        fields.parallelStream().forEach(Field::restart);
        sortMines();
    }



    private void setNeighbours() {
        for(Field f: fields) {
            for(Field f1: fields) f.addNeighbour(f1);
        }
    }

    private void generateFields() {
        for(int i=0; i<lines; i++) {
            for(int j=0; j<columns; j++) {

                fields.add(new Field(i,j));

            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ]");
        for (int i = 0; i < columns; i++) {
            sb.append("[");
            sb.append(i+1);
            sb.append("]");
        }

        sb.append("\n");

        int c = 0;
        for (int i=0; i<lines; i++)
        {
            sb.append("[");
            sb.append(i+1);
            sb.append("]");

            for (int j=0; j< columns; j++)

            {
                sb.append(" ");
                sb.append(fields.get(c));
                sb.append(" ");
                c++;
            }
            sb.append("\n");
        }

        return  sb.toString();


    }
}
