package ru.geekbrains.dungeon.game.units;

import com.badlogic.gdx.math.MathUtils;
import lombok.Getter;

@Getter
public class Stats {
    int level;
    int hp, maxHp;
    int attackPoints, minAttackPoints, maxAttackPoints;
    int movePoints, minMovePoints, maxMovePoints;
    int visionRadius;
    // сытость
    int fill;

    public Stats(int level, int maxHp, int minAttackPoints, int maxAttackPoint, int minMovePoints, int maxMovePoint) {
        this.level = level;
        this.maxHp = maxHp;
        this.hp = this.maxHp;
        this.minAttackPoints = minAttackPoints;
        this.maxAttackPoints = maxAttackPoint;
        this.minMovePoints = minMovePoints;
        this.maxMovePoints = maxMovePoint;
        this.visionRadius = 5;
        this.attackPoints = 5;
        this.fill = 100;
    }

    public void restorePoints() {
        attackPoints = MathUtils.random(minAttackPoints, maxAttackPoints);
        movePoints = MathUtils.random(minMovePoints, maxMovePoints);
    }

    public void restoreHp(int amount) {
        hp += amount;
        if (hp > maxHp) {
            hp = maxHp;
        }
    }

    public void fullRestoreHp() {
        hp = maxHp;
    }

    public void resetPoints() {
        attackPoints = 0;
        movePoints = 0;
    }

    public boolean doIHaveAnyPoints() {
        return attackPoints > 0 || movePoints > 0;
    }

    public void addFill () {
        fill += MathUtils.random(10 , 25);
        if (fill > 100){
            fill = 100;
        }
    }

    public void lessFill(){
        if (fill > 0) {
            fill--;
        } else {
            hp--;
        }
    }
}
