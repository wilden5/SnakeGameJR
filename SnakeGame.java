package com.javarush.games.snake;
import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;

    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;

    private int endScore;

    @Override
    public void showGrid(boolean isShow) {
        super.showGrid(false);
    }

    private void createGame(){
        snake = new Snake(WIDTH / 2,HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
    }

    private void createNewApple() { //подсмотрел реализацию, хотел сделать через for
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;
    }

    private void drawScene(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                setCellValueEx(x,y,Color.DARKSEAGREEN,""); // Кавычками
            }    // обозначается пустая строка в качестве переданного значения
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }
    @Override
    public void onTurn(int step){
        snake.move(apple);
        if (apple.isAlive == false){
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
            endScore++;
        }if (snake.isAlive == false){
            gameOver();
        }
        drawScene();
    }
    @Override
    public void onKeyPress(Key key){ // Считывание клавиш для змейки
        if (key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP){
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        } else if (key == key.SPACE && isGameStopped == true){
            createGame();
        }
    }

    public int endScore(){

        if(apple.isAlive == false){
            int endScore= 0;
            endScore++;
        }
        return endScore;
    };

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"YOUR SCORE: " +endScore +"." + " PRESS SPACE TO RESTART GAME",Color.BLACK, 20);
        endScore= 0;

    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE,"YOU WIN." + " YOUR SCORE: " +endScore +"." + "PRESS SPACE TO RESTART GAME",Color.BLACK, 15 );
    }

}

