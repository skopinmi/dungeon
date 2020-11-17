package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.GameController;
import ru.geekbrains.dungeon.GameMap;

public class Hero extends Unit {
    float movementTime;
    float movementMaxTime;
    int targetX, targetY;
    private int experience;
    private int maxExperience;
    private byte moveCount;
    private TextureRegion textureMove;

    public Hero(TextureAtlas atlas, GameController gc) {
        super(gc, 1, 1, 10);
        this.texture = atlas.findRegion("knight");
        this.textureHp = atlas.findRegion("hp");
        this.textureMove = atlas.findRegion("hp");
        this.movementMaxTime = 0.2f;
        this.targetX = cellX;
        this.targetY = cellY;
        // у нас только 2 монстра дальше герой становится сильнее / преходит на другой уровень :)
        this.maxExperience = 2;
        this.experience = 0;
        this.moveCount = 5;
    }

    // добавить опыт

    public void addExperience(int experience) {
        this.experience += experience;
    }

    public void addMoveCount() {
        moveCount -= 1;
        if (this.moveCount == 0) {
            this.moveCount = 5;
        }
    }
// проверка жив ли герой
    public boolean isAlive() {
        return hp > 0;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void update(float dt) {
        checkMovement(dt);
    }

    public boolean isStayStill() {
        return cellY == targetY && cellX == targetX;
    }

    public void checkMovement(float dt) {
        if (Gdx.input.justTouched() && isStayStill()) {
            if (Math.abs(gc.getCursorX() - cellX) + Math.abs(gc.getCursorY() - cellY) == 1) {
                targetX = gc.getCursorX();
                targetY = gc.getCursorY();
            }
        }

        Monster m = gc.getMonsterController().getMonsterInCell(targetX, targetY);
        if (m != null) {
            targetX = cellX;
            targetY = cellY;
            m.takeDamage(1);
// нападение монстра
            m.getDamage(this);

            if (!m.isActive()) {
                addExperience(1);
            }
// счет шагов - при ударе расходуется ход
            addMoveCount();
        }

        if (!gc.getGameMap().isCellPassable(targetX, targetY)) {
            targetX = cellX;
            targetY = cellY;
        }

        if (!isStayStill()) {
            movementTime += dt;
            if (movementTime > movementMaxTime) {
                movementTime = 0;
                cellX = targetX;
                cellY = targetY;
// счет шагов
                addMoveCount();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if (isAlive()) {
            float px = cellX * GameMap.CELL_SIZE;
            float py = cellY * GameMap.CELL_SIZE;
            if (!isStayStill()) {
                px = cellX * GameMap.CELL_SIZE + (targetX - cellX) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
                py = cellY * GameMap.CELL_SIZE + (targetY - cellY) * (movementTime / movementMaxTime) * GameMap.CELL_SIZE;
            }
            batch.draw(texture, px, py);
            batch.setColor(0.0f, 0.0f, 0.0f, 1.0f);
            batch.draw(textureHp, px + 1, py + 51, 58, 10);
            batch.setColor(0.7f, 0.0f, 0.0f, 1.0f);
            batch.draw(textureHp, px + 2, py + 52, 56, 8);
            batch.setColor(0.0f, 1.0f, 0.0f, 1.0f);
            batch.draw(textureHp, px + 2, py + 52, (float) hp / hpMax * 56, 8);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            // отображается опыт под жизнью -
            batch.draw(textureHp, px + 2, py + 50, (float) experience /maxExperience * 56, 2);
            // отображение ходов
            batch.draw(textureHp, px + 2, py + 2, (float) moveCount / 5 * 56, 8);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
}
