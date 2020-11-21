package ru.geekbrains.dungeon.units;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.dungeon.GameController;

public class Monster extends Unit {
    private float aiBrainsImplseTime;
    private Unit target;

//    дальность зрения
    private int vigilance;

    public Monster(TextureAtlas atlas, GameController gc) {
        super(gc, 5, 2, 10);
        this.texture = atlas.findRegion("monster");
        this.textureHp = atlas.findRegion("hp");
        this.hp = -1;
        this.vigilance = 5;
    }

    public void activate(int cellX, int cellY) {
        this.cellX = cellX;
        this.cellY = cellY;
        this.targetX = cellX;
        this.targetY = cellY;
        this.hpMax = 10;
        this.hp = hpMax;
        this.target = gc.getUnitController().getHero();
    }

    public void update(float dt) {
        super.update(dt);
        if (canIMakeAction()) {
            if (isStayStill()) {
                aiBrainsImplseTime += dt;
            }
            if (aiBrainsImplseTime > 0.4f) {
                aiBrainsImplseTime = 0.0f;
                if (canIAttackThisTarget(target)) {
                    attack(target);
                } else {
                    tryToMove();
                }
            }
        }
    }

    public void tryToMove() {
        int bestX = -1, bestY = -1;
        // как меняются bestX bestY
        // если враг не в поле видимости :
        if (!searchForEnemy()) {
            // рандомно меняется либо Х либо У
            if (Math.random() > 0.5) {
                bestX = cellX + howChange();
                bestY = cellY;
            } else {
                bestX = cellX;
                bestY = cellY + howChange();
            }
        } else {
            float bestDst = 10000;
            for (int i = cellX - 1; i <= cellX + 1; i++) {
                for (int j = cellY - 1; j <= cellY + 1; j++) {
                    if (Math.abs(cellX - i) + Math.abs(cellY - j) == 1 && gc.getGameMap().isCellPassable(i, j) && gc.getUnitController().isCellFree(i, j)) {
                        float dst = (float) Math.sqrt((i - target.getCellX()) * (i - target.getCellX()) + (j - target.getCellY()) * (j - target.getCellY()));
                        if (dst < bestDst) {
                            bestDst = dst;
                            bestX = i;
                            bestY = j;
                        }
                    }
                }
            }
        }
        goTo(bestX, bestY);
    }
// есть ли враг в поле видимости?
    public boolean searchForEnemy () {
        float dst = (float) Math.sqrt((cellX - target.getCellX()) * (cellX - target.getCellX()) +
                (cellY - target.getCellY()) * (cellY - target.getCellY()));
        System.out.println(dst);
        if (dst < vigilance) {
            return true;
        }
        return false;
    }
// рандомное +1 или -1
    public int howChange () {
        if (Math.random() > 0.5) {
            return 1;
        } else {
            return -1;
        }
    }
}
