package seaboy;

import java.util.Random;

public class Place {
    
    public Place(Cell[][] cell) {
        //Очистка поля
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                cell[x][y] = Cell.EMPTY;
            }
        }
        
        for (int i = 0; i < 10; i++) {
            if (i == 0) ship(cell, 4);
            else if (i < 3)ship(cell, 3);
            else if (i < 6)ship(cell, 2);
            else ship(cell, 1);
        }
    }
    
    private void ship(Cell[][] cell, int shipLength) {
        Random random = new Random();
        while(true) {
            int x, y; // начальная клетка корабля

            // 0 - горизонтальное расположение (по оси x); 1 - вертикальное
            int location = random.nextInt(2);
            if (location == 1) {
                //System.out.println("Vertical");
                x = random.nextInt(10);
                y = random.nextInt(11 - shipLength);
            }
            else {
                //System.out.println("Horizontal");
                x = random.nextInt(11 - shipLength);
                y = random.nextInt(10);
            }

            boolean correctPlace = true;
            //System.out.println("x = " + x + " y = " + y );

            for (int i = -1; i <= shipLength; i++) {
                for (int j = -1; j <= 1; j++) {
                    int xx, yy;
                    if (location == 1) {
                        xx = x + j;
                        yy = y + i;
                    }
                    else {
                        xx = x + i;
                        yy = y + j;
                    }

                    if ((xx < 0) || (xx >= 10) || (yy < 0) || (yy >= 10))
                    {
                        continue;
                    }

                    if (cell[xx][yy] != Cell.EMPTY) {
                        correctPlace = false;
                        break;
                    }
                }
                if (!correctPlace) {
                    //System.out.println("wrongPlace");
                    break;
                }
            }
            
            if (correctPlace) {
                for (int i = 0; i < shipLength; i++) {

                    if (location == 1) {
                        cell[x][y+i] = Cell.SHIP;
                        //System.out.println("Ship on x = " + x + ", y = " + (y + i));
                    }
                    else {
                        cell[x+i][y] = Cell.SHIP;
                        //System.out.println("Ship on x = " + (x+i) + ", y = " + y);
                    }                
                }
                break;
            }
        }
    }
}

