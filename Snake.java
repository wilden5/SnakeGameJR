package com.javarush.games.snake;
import java.util.List;
import java.util.ArrayList;
import com.javarush.engine.cell.*;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private final static String HEAD_SIGN = "\uD83D\uDC32";
    private final static String BODY_SIGN = "\u2B55";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction) {//  Метод запрещающий изменять направление
        // движения змейки, если параметр метода противоположен текущему направлению змейки
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x) {
            return;
        }
        if ((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y) {
            return;
        }
        
        if (direction == direction.UP && this.direction == direction.DOWN){
            return;
        } else if (direction == direction.LEFT && this.direction == direction.RIGHT){
            return;
        } else if (direction == direction.RIGHT && this.direction == direction.LEFT){
            return;
        } else if (direction == direction.DOWN && this.direction == direction.UP){
            return;
        }
        this.direction = direction;
    }
    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public void draw(Game game){
        Color color = isAlive? Color.BLACK : Color.RED;
        for (int i = 0; i < snakeParts.size(); i++){
            GameObject part = snakeParts.get(i);
            String sign = (i !=0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

    public Snake(int x, int y){
        GameObject number1 = new GameObject(x,y);
        GameObject number2 = new GameObject(x+1,y);
        GameObject number3 = new GameObject(x+2,y);

        snakeParts.add(number1);
        snakeParts.add(number2);
        snakeParts.add(number3);
    }
    public void move(Apple apple){
        GameObject newHead = createNewHead(); // Вызывается метод
        if(newHead.x >=SnakeGame.WIDTH
            || newHead.x < 0
            || newHead.y >= SnakeGame.HEIGHT
            || newHead.y < 0){
            isAlive = false; // Змейка умирает, если выходит за пределы
            return;         // игрового поля
        }
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0,newHead); // Голова добавляется на позицию 0

        if (newHead.x == apple.x && newHead.y == apple.y){
            apple.isAlive = false;
        } else {
            removeTail(); // После создания головы вызывается этот метод
        }
    }

    public GameObject createNewHead() {
        GameObject oldHead = snakeParts.get(0);
        if (direction == direction.LEFT) {
            return new GameObject(oldHead.x - 1, oldHead.y);
        } else if (direction == direction.RIGHT) {
            return new GameObject(oldHead.x + 1, oldHead.y);
        } else if (direction == direction.UP) {
            return new GameObject(oldHead.x, oldHead.y - 1);
        } else if (direction == direction.DOWN) {
            return new GameObject(oldHead.x, oldHead.y + 1);
        }
    return createNewHead();
    }

    public boolean checkCollision(GameObject gameObject){
        for (GameObject part : snakeParts){
            if (part.x == gameObject.x && part.y == gameObject.y){
                return true;
            }
        }
        return false;
    }
    public int getLength(){

        return snakeParts.size();
    }
}
