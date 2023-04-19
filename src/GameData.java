public class GameData
{
    private char[][] grid = {{' ',' ', ' ', ' ',' ', ' ', ' ' },
                             {' ',' ', ' ', ' ',' ', ' ', ' ' },
                             {' ',' ', ' ', ' ',' ', ' ', ' ' },
                             {' ',' ', ' ', ' ',' ', ' ', ' ' },
                             {' ',' ', ' ', ' ',' ', ' ', ' ' },
                             {' ',' ', ' ', ' ',' ', ' ', ' ' }};

    public char[][] getGrid()
    {
        return grid;
    }

    public void reset()
    {

        grid = new char[7][6];
        for(int r=0;r<grid.length; r++)
            for(int c=0; c<grid[0].length; c++)
                grid[r][c]=' ';
    }


    public boolean isCat()
    {
        if(!isWinner('R') && !isWinner('Y'))
        {
            boolean allFull = true;
            for (char[] chars : grid)
                for (int c = 0; c < grid[0].length; c++)
                    if (chars[c] == ' ') {
                        allFull = false;
                        break;
                    }
            return allFull;
        }
        else
            return false;
    }

    public boolean isWinner(char letter)
    {
        //horizontal win
        for(int c = 0; c < grid[0].length-3; c++) {
            for (int r = 0; r < grid.length; r++) {
                if (grid[r][c] == letter && grid[r][c+1] == letter && grid[r][c+2] == letter && grid[r][c+3] == letter) {
                    return true;
                }
            }
        }

        //vertical win
        for (int i = 0; i< grid.length-3 ; i++ ){
            for (int j = 0; j< grid[0].length; j++){
                if (grid[i][j] == letter && grid[i+1][j] == letter && grid[i+2][j] == letter && grid[i+3][j] == letter){
                    return true;
                }
            }
        }

        // diag going up right
        for (int i=3; i< grid.length; i++){
            for (int j=0; j< grid[0].length-3; j++){
                if (grid[i][j] == letter && grid[i-1][j+1] == letter && grid[i-2][j+2] == letter && grid[i-3][j+3] == letter)
                    return true;
            }
        }

        //diag going down right
        for (int i=3; i< grid.length; i++){
            for (int j=3; j<grid[0].length; j++){
                if (grid[i][j] == letter && grid[i-1][j-1] == letter && grid[i-2][j-2] == letter && grid[i-3][j-3] == letter)
                    return true;
            }
        }
        return false;
    }

}
