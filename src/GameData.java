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

        grid = new char[6][7];
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
        for (int r = 0; r< grid.length-3 ; r++ ){
            for (int c = 0; c< grid[0].length; c++){
                if (grid[r][c] == letter && grid[r+1][c] == letter && grid[r+2][c] == letter && grid[r+3][c] == letter){
                    return true;
                }
            }
        }

        // diag going up right
        for (int r=3; r< grid.length; r++){
            for (int c=0; c< grid[0].length-3; c++){
                if (grid[r][c] == letter && grid[r-1][c+1] == letter && grid[r-2][c+2] == letter && grid[r-3][c+3] == letter)
                    return true;
            }
        }

        //diag going down right
        for (int r=3; r< grid.length; r++){
            for (int c=3; c<grid[0].length; c++){
                if (grid[r][c] == letter && grid[r-1][c-1] == letter && grid[r-2][c-2] == letter && grid[r-3][c-3] == letter)
                    return true;
            }
        }
        return false;
    }

}
