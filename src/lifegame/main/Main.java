package lifegame.main;

import lifegame.entity.Cell;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

/***
 * 生命遊戲（Game of life）為1970年由英國數學家J. H. Conway提出，某細胞的鄰居包括上、下、左、右、左上、左下、右上與右下相鄰之細胞，遊戲規則如下：
 * 孤單死亡：如果細胞的鄰居小於一個，則該細胞在下次狀態將死亡。
 * 擁擠死亡：如果細胞的鄰居在四個以上，則該細胞在下次狀態將死亡。
 * 穩定：如果細胞的鄰居為二個或三個，則下一次狀態為穩定存活。
 * 復活：如果某位置原無細胞存活，而該位置的鄰居為三個，則該位置將復活一細胞。
 */
public class Main {

    /***
     * 初始化
     * @param rowData 初始化细胞矩阵
     * @return 构建的Cell二维数组
     */
    public static Cell[][] asCells(int[][] rowData) {
        Cell[][] cells = new Cell[rowData.length][rowData[0].length];
        for (int i = 0; i < rowData.length; i++) {
            for (int j = 0; j < rowData[i].length; j++) {
                if (rowData[i][j] == 1) {
                    cells[i][j] = new Cell(i, j);
                }
            }
        }
        return cells;
    }

    /**
     * 获取指定位置细胞，当细胞不存在时创建一个细胞
     * @param cells 当前状态
     * @param i 位置-x
     * @param j 位置-y
     * @return 该位置细胞
     */
    public static Cell getOrNew(Cell[][] cells, int i, int j) {
        return cells[i][j] == null ? new Cell(i, j) : cells[i][j];
    }

    /***
     * 更新到下一状态
     * @param current 当前状态
     * @return 更新后的状态
     */
    public static Cell[][] produce(Cell[][] current) {
        Cell[][] next = new Cell[current.length][current[0].length];
        for (int i = 0; i < current.length; i++) {
            for (int j = 0; j < current[i].length; j++) {
                next[i][j] = getOrNew(current, i, j).seekToSurvive(current);
            }
        }
        return next;
    }

    /***
     * 打印当前状态
     * @param cells 当前状态
     */
    public static void print(Cell[][] cells) {
        System.out.println("当前状态：");
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print(cell == null ? '_' : 'O');
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Cell[][] current = asCells(new int[][]{
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 1},
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 1},
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 1},
                {0, 1, 1, 1, 0, 0, 1, 0, 1, 1},
                {0, 1, 1, 1, 0, 1, 0, 0, 1, 1},
                {0, 1, 0, 1, 1, 0, 0, 1, 1, 1},
                {0, 1, 0, 1, 0, 1, 0, 0, 1, 1},
                {0, 1, 0, 1, 0, 0, 1, 0, 1, 1},
                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1},
                {0, 1, 0, 1, 1, 0, 0, 0, 1, 1},
        });
        print(current);
        Cell[][] next = produce(current);
        while (!Arrays.deepEquals(current, next)) {
            current = next;
            print(current);
            next = produce(current);
        }
    }
}
