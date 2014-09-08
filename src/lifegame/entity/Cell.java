package lifegame.entity;

/****
 * Created by Mengqi on 2014/9/8 0008.
 *
 * 生命遊戲的規則可簡化為某位置有無細胞：
 * 某位置之細胞鄰居數為 0、1、4、5、6、7、8 時，則該位置下次狀態必無細胞存活。
 * 某位置之細胞鄰居數為 2 時，則該位置下次狀態保持不變（有細胞就有細胞，無細胞就無細胞）。
 * 某位置之細胞鄰居數為 3 時，則該位置下次狀態必有細胞存活。
 *
 ****/
public class Cell {
    //细胞位置
    final int i, j;

    /***
     * 构造函数
     * @param i 细胞位置-x
     * @param j 细胞位置-y
     */
    public Cell(int i, int j) {
        this.i = i;
        this.j = j;
    }

    /***
     * 细胞更新自己的生存状态
     * @param current 当前状态
     * @return 更新后的细胞
     */
    public Cell seekToSurvive(Cell[][] current) {
        return isLivable(current) ? this : null;
    }

    /**
     * 判断本细胞（this）是否存活
     * @param cells 当前状态
     * @return 是否存活
     */
    public boolean isLivable(Cell[][] cells) {
        switch (neighbors(cells)) {
            case 0:case 1:case 4: return  false;
            case 2: return this == cells[i][j];
        }
        return true;
    }

    /***
     * 获取邻居数量
     * @param cells 当前状态
     * @return 邻居数量
     */
    public int neighbors(Cell[][] cells) {
        // 8 个邻居方向
        int dirs[][] = {{-1, 0}, {-1, 1}, {0, 1},{1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        //计算邻居数量
        int count = 0;
        for (int i = 0; i < 8 && count < 4; i++) {
            int r = this.i + dirs[i][0];
            int c = this.j + dirs[i][1];
            //位置合法且该位置存在细胞，则邻居计数器+1
            if (r > -1 && r < cells.length && c > -1 && c < cells[0].length && cells[r][c] != null) {
                count++;
            }
        }
        return count;
    }


    /**
     * 判断两个细胞类相等
     * @param obj 另一个细胞类
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Cell that = (Cell)obj;
        return this.i == that.i && this.j == that.j;
    }

    /**
     * 获得本细胞的哈希值
     * @return 本细胞的哈希值
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.i;
        hash = 59 * hash + this.j;
        return hash;
    }
}
