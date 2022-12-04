package hashlife;

abstract class Node {
    private int dimension;
    private int size;
    private int density;
    private boolean isOff;

    Node(int dimension, boolean isOff, int density){
        this.dimension = dimension;
        this.isOff = isOff;
        this.density = density;
        this.size = 1 << dimension; //2^dimension
    }

    public Node result(){
        return result(this.dimension-2);
    }

    public int[][] toArray(){
        int[][] array = new int[size][size];
		fillArray(array, 0, 0);
		return array;
    }

    abstract Node getQuad(int numquad);

    abstract Node result(int numSteps);

    abstract int getCell(int x, int y);

    abstract Node setCell(int x, int y, int state);

    abstract void fillArray(int[][] array, int x, int y);

    abstract Node simplify();

    abstract Node centre();

    public int getDimension(){
        return this.dimension;
    }

    public int getSize(){
        return this.size;
    }

    public int getDensity(){
        return this.density;
    }

    public boolean isOff(){
        return this.isOff;
    }
}
