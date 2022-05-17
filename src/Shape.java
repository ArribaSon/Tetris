import java.util.Random;

public class Shape {

    private TypeShape pieceShape;
    private final int[][] coordinates;
    private final int[][][] coordinatesTable;

    public Shape() {

        coordinates = new int[4][2];

        //Фигуры
        coordinatesTable = new int[][][]{
                {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
                {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
                {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
                {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
                {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
                {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
                {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
                {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        setShape(TypeShape.NoShape);
    }

    //Поворот
    public Shape rotateLeft() {

        if (pieceShape == TypeShape.SquareShape) {

            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, getY(i));
            result.setY(i, -getX(i));
        }

        return result;
    }

    public Shape rotateRight() {

        if (pieceShape == TypeShape.SquareShape) {

            return this;
        }

        Shape result = new Shape();
        result.pieceShape = pieceShape;

        for (int i = 0; i < 4; ++i) {

            result.setX(i, -getY(i));
            result.setY(i, getX(i));
        }

        return result;
    }

    //Установка конкретного типа фигуры
    protected void setShape(TypeShape typeShape) {

        for (int i = 0; i < 4; i++) {

            System.arraycopy(coordinatesTable[typeShape.ordinal()][i], 0, coordinates[i], 0, 2);
        }

        pieceShape = typeShape;
    }

    public void setRandomShape() {

        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;

        TypeShape[] values = TypeShape.values();
        setShape(values[x]);
    }

    //Сеттеры
    private void setX(int index, int x) {

        coordinates[index][0] = x;
    }

    private void setY(int index, int y) {

        coordinates[index][1] = y;
    }

    //Геттеры
    public int getX(int index) {

        return coordinates[index][0];
    }

    public int getY(int index) {

        return coordinates[index][1];
    }

    public TypeShape getShape() {

        return pieceShape;
    }

    public int getMinY() {

        int m = coordinates[0][1];

        for (int i = 0; i < 4; i++) {

            m = Math.min(m, coordinates[i][1]);
        }

        return m;
    }
}

