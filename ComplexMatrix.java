public class ComplexMatrix {
    private int n; // строки
    private int m; // столбцы
    private Complex[][] a;

    public ComplexMatrix(int rows, int cols) {
        n = rows;
        m = cols;
        a = new Complex[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = new Complex(0, 0);
            }
        }
    }

    public void set(int i, int j, Complex c) {
        a[i][j] = c;
    }

    public ComplexMatrix add(ComplexMatrix b) throws MatrixException {
        if (n != b.n || m != b.m) {
            throw new MatrixException("размеры не совпадают");
        }
        ComplexMatrix res = new ComplexMatrix(n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.a[i][j] = a[i][j].add(b.a[i][j]);
            }
        }
        return res;
    }

    public ComplexMatrix mul(ComplexMatrix b) throws MatrixException {
        if (m != b.n) {
            throw new MatrixException("нельзя умножить: столбцы первой != строки второй");
        }
        ComplexMatrix res = new ComplexMatrix(n, b.m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < b.m; j++) {
                Complex s = new Complex(0, 0);
                for (int k = 0; k < m; k++) {
                    s = s.add(a[i][k].mul(b.a[k][j]));
                }
                res.a[i][j] = s;
            }
        }
        return res;
    }

    // деление = умножение на обратную
    public ComplexMatrix div(ComplexMatrix b) throws MatrixException {
        return mul(b.inv());
    }

    public ComplexMatrix transpose() {
        ComplexMatrix res = new ComplexMatrix(m, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res.a[j][i] = a[i][j];
            }
        }
        return res;
    }

    public Complex det() throws MatrixException {
        if (n != m) {
            throw new MatrixException("определитель только у квадратных");
        }
        return detRec();
    }

    // рекурсивно по первой строке
    private Complex detRec() {
        if (n == 1) {
            return a[0][0];
        }
        Complex d = new Complex(0, 0);
        for (int j = 0; j < m; j++) {
            Complex t = a[0][j].mul(minor(0, j).detRec());
            if (j % 2 == 0) {
                d = d.add(t);
            } else {
                d = d.sub(t);
            }
        }
        return d;
    }

    public ComplexMatrix inv() throws MatrixException {
        Complex d = det();
        if (d.mod() < 1e-8) {
            throw new MatrixException("матрица вырождена, обратной нет");
        }

        ComplexMatrix res = new ComplexMatrix(n, m);
        if (n == 1) {
            res.a[0][0] = new Complex(1, 0).div(a[0][0]);
            return res;
        }

        // алгебраические дополнения + сразу транспонируем
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Complex c = minor(i, j).detRec();
                if ((i + j) % 2 == 1) {
                    c = new Complex(0, 0).sub(c);
                }
                res.a[j][i] = c.div(d);
            }
        }
        return res;
    }

    private ComplexMatrix minor(int row, int col) {
        ComplexMatrix r = new ComplexMatrix(n - 1, m - 1);
        int ri = 0;
        for (int i = 0; i < n; i++) {
            if (i == row) continue;
            int rj = 0;
            for (int j = 0; j < m; j++) {
                if (j == col) continue;
                r.a[ri][rj] = a[i][j];
                rj++;
            }
            ri++;
        }
        return r;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                sb.append(a[i][j]).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}