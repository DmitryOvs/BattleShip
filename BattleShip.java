import java.util.Scanner;
public class BattleShip {
          static Scanner scanner = new Scanner(System.in);
          static final int FILED_LENGTH = 10;

        public static void main(String[] args) {

            System.out.println("Привет игрок NumberOne. Введи свое имя");
            String play1Name = scanner.nextLine();
            System.out.println(play1Name + " О! как красиво тебя зовут!");

            System.out.println("Привет игрок Two. Введи свое имя");
            String play2Name = scanner.nextLine();
            System.out.println(play2Name + ", а твое имя еще красивей!");


            char[][] playerField1 = new char[FILED_LENGTH][FILED_LENGTH];
            char[][] playerField2 = new char[FILED_LENGTH][FILED_LENGTH];



            fillPlayerField(playerField1);
            fillPlayerField(playerField2);
            playGame(play1Name, play2Name, playerField1, playerField2);
        }

        private static void fillPlayerField(char[][] playerField){
            for (int i = 4; i >=1 ; i--) {
                for (int j = 0; j < 5-i; j++) {
                    System.out.println("Укажите координаты " + i + " мачтового корабля. Осталось расставить " + (5 - i - j) + " корабля такого типа");
                    System.out.println("Введите координату по оси \"x\":");
                    int x = scanner.nextInt();
                    System.out.println("Введите координату по оси \"y\":");
                    int y = scanner.nextInt();
                    System.out.println("Корабль распологается: 1 -  по горизонтали; 2 - по вертикали ");
                    int position = scanner.nextInt();

                    int validateResult = validateCoordForShip(playerField, x, y, position, i);
                    if (validateResult == 0) {
                        if (position == 1) {
                            for (int q = 0; q < i; q++) {
                                playerField[y][x + q] = '1';
                            }
                        }
                        if (position == 2) {
                            for (int q = 0; q < i; q++) {
                                playerField[y + q][x] = '1';
                            }
                        }
                        printField(playerField);
                    }
                    else if(validateResult == -1){
                        System.out.println("Ошибка ввода: или слишком близко к соседнему кораблю. Попробуй еще раз!!!");
                        j = j-1;
                    }
                    else if(validateResult == -2){
                        System.out.println("Ошибка ввода: корабль выходит за границы поля. Попробуй еще раз!!!");
                        j = j-1;
                    }
                    else if(validateResult == -3) {
                        System.out.println("Ошибка ввода: неверно направление расположения корабля. Попробуй еще раз!!!");
                        j = j - 1;
                    }
                }
            }
        }
        private static void printField(char[][] field){
            for (char[] cells : field){
                for (char cell : cells){
                    if (cell == 0){
                        System.out.print( " |");
                    } else{
                        System.out.print(cell+"|");
                    }
                }
                System.out.println("");
                System.out.println("--------------------");
            }
        }
        private static void playGame(String player1Name, String player2Name, char[][] playerField1, char[][] playerField2){
            char[][] playerBattle1Field = new char[FILED_LENGTH][FILED_LENGTH];
            char[][] playerBattle2Field = new char[FILED_LENGTH][FILED_LENGTH];

            String currentPlayerName = player1Name;
            char[][] currentPlayerField = playerField2;
            char[][] currentPlayerBattleField = playerBattle1Field;

            while (isPlayerAlive(playerField1) && isPlayerAlive(playerField2)) {
                System.out.println(currentPlayerName + " , пожалуйста введите координату \"x\" для выстрела");
                int xShot = scanner.nextInt();
                System.out.println(currentPlayerName + " , пожалуйста введите координату \"y\" для выстрела");
                int yShot = scanner.nextInt();

                int shotResult = handleShot(currentPlayerBattleField, currentPlayerField, xShot, yShot);

                if (shotResult == 0) {
                    currentPlayerName = player2Name;
                    currentPlayerField = playerField1;
                    currentPlayerBattleField = playerBattle2Field;
                }
            }
            System.out.println(currentPlayerName + "победил");
        }
        private static int handleShot(char[][] battleField, char[][] field, int x, int y){
            if ('1' == field[y][x]){
                field[y][x] = '#';
                battleField[y][x] = '#';
                System.out.println("Хороший выстрел.");
                return 1;}
            else {
                battleField[y][x] = '*';
                System.out.println("Касай!!!!!");
                return 0;}
        }
        private static boolean isPlayerAlive(char[][] field){
            for (char[] cells : field){
                for (char cell : cells){
                    if ('1' == cell){
                        return true;
                    }
                }
            }
            return false;
        }
        private static int validateCoordForShip(char[][] field, int x, int y, int position, int typeShip){
            for (int i = 0; i < typeShip - 1; i++) {
                if (y + i > 9 || x + i > 9 || x == 0 || y == 0)
                    return -2;
            }
            if (position == 1) {
                for (int i = 0; i < typeShip - 1; i++) {
                    if ('1' == field[y][x + i] || '1' == field[y - 1][x + i] || '1' == field[y + 1][x + i] || '1' == field[y][x + i + 1]
                            || '1' == field[y][x + i - 1])
                        return -1;
                }
            }
            if (position == 2) {
                for (int i = 0; i < typeShip - 1; i++) {
                    if ('1' == field[y + 1][x] || '1' == field[y + i][x - 1] || '1' == field[y + i][x + 1] || '1' == field[y + i + 1][x]
                            || '1' == field[y + i - 1][x])
                        return -1;
                }
            }
            if (position > 2 || position < 1)
                return -3;
            return 0;
        }
    }

