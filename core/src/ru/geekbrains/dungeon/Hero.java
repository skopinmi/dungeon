package ru.geekbrains.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Hero {

    private ProjectileController projectileController;
    private Vector2 position;
    private Vector2 move;
    private Vector2 derection;
    private boolean isSingle;
//    private int countOfShot;
    private TextureRegion texture;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);
        this.move = new Vector2(0, 0);
        this.derection = new Vector2(1, 0);
        this.isSingle = true;
//        this.countOfShot = 1;
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
    }

    public void update(float dt) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
////            двойной выстрел циклом - 20 px между снарядами
//            for (int i = 0 ; i < countOfShot; i++) {
//                projectileController.activate(position.x + i * 20 * derection.x,
//                        position.y + i * 20 * derection.y,
//                        200 * derection.x, 200 * derection.y);
//            }
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//           20 px между снарядами
            if (isSingle) {
                projectileController.activate(position.x, position.y, 200 * derection.x, 200 * derection.y);
            } else {
                projectileController.activate(position.x, position.y, 200 * derection.x, 200 * derection.y);
                projectileController.activate(position.x +  20 * derection.x, position.y + 20 * derection.y,
                        200 * derection.x, 200 * derection.y);
            }
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
//            if (countOfShot == 2) {
//                countOfShot = 1;
//            } else {
//                countOfShot = 2;
//            }
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)){
            if (isSingle) {
                isSingle = false;
            } else {
                isSingle = true;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            move.set(0, 40);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            move.set(0, -40);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            move.set( -40, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            move.set(40, 0);
        }
// проверка выхода за пределы игрового поля
        if(position.x + move.x > 800 || position.x + move.x < 0 ||
                position.y + move.y < 0 ||position.y + move.y > 720) {
            move.set(0, 0);
        }
// изменение направления при движении
        if (move.x != 0 || move.y != 0 ) {
            derection.set(move);
            derection.nor();
        }
        position.add(move);
        move.set(0, 0);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 20, position.y - 20);
    }
}
