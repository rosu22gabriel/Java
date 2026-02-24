
class FirInmultire extends Thread {
    private double[][] matX, matY, matRez;
    private int indexLinieStart, indexColoanaStart, indexLinieEnd, indexColoanaEnd;

    public FirInmultire(double[][] matX, double[][] matY, double[][] matRez,
                        int indexLinieStart, int indexColoanaStart,
                        int indexLinieEnd, int indexColoanaEnd) {
        this.matX = matX;
        this.matY = matY;
        this.matRez = matRez;

        this.indexLinieStart = indexLinieStart;
        this.indexColoanaStart = indexColoanaStart;

        this.indexLinieEnd = indexLinieEnd;
        this.indexColoanaEnd = indexColoanaEnd;
    }

    @Override
    public void run(){
        for (int i = indexLinieStart; i <= indexLinieEnd; i++) {
            for (int j = indexColoanaStart; j <= indexColoanaEnd; j++) {
                matRez[i][j] = 0;
                for (int k = 0; k < matY.length; k++) {
                    matRez[i][j] += matX[i][k] * matY[k][j];
                }
            }
        }
    }
}

public class Seminar04_InmultireMatrice {
    public static void main(String[] args) throws InterruptedException {
        int n = 1000;
        int numarBlocuri = 2; // Numar de blocuri linie/coloana pentru matX/matY

        double[][] matX = new double[n][n],
                matY = new double[n][n],
                matRez = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matX[i][j] = Math.random() * 10 - 5;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matY[i][j] = Math.random() * 10 - 5;
            }
        }
    }



}

