package ru.geekbrains.dungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.units.Monster;

public class MonsterController {
    private static final int MAX_MONSTERS = 100;

    private GameController gc;
    private Monster[] monsters;

    public Monster[] getMonsters() {
        return monsters;
    }

    public MonsterController(GameController gc, TextureAtlas atlas) {
        this.gc = gc;
        this.monsters = new Monster[MAX_MONSTERS];
        for (int i = 0; i < monsters.length; i++) {
            monsters[i] = new Monster(atlas, gc);
        }
    }

    public void activate(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (!m.isActive()) {
                m.activate(cellX, cellY);
                return;
            }
        }
    }

    public Monster getMonsterInCell(int cellX, int cellY) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                if (m.getCellX() == cellX && m.getCellY() == cellY) {
                    return m;
                }
            }
        }
        return null;
    }

    public void getDamage(Monster m) {
        int x = gc.getHero().getTargetX();
        int y = gc.getHero().getTargetY();
        if (m.isActive()) {
            if ((x == m.getCellX() + 1 || x == m.getCellX() - 1) && y == m.getCellY() ||
                    (y == m.getCellY() + 1 || y == m.getCellY() - 1) && x == m.getCellX()) {
                if (Math.random() < 0.25) {
                    gc.getHero().takeDamage(1);
                }
            }
        }
    }


    public void update(float dt) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.update(dt);
                getDamage(m);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Monster m : monsters) {
            if (m.isActive()) {
                m.render(batch);
            }
        }
    }
}
