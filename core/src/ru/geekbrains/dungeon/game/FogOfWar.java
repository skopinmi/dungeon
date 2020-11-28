package ru.geekbrains.dungeon.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.geekbrains.dungeon.helpers.Assets;
import ru.geekbrains.dungeon.helpers.Utils;

public class FogOfWar {

    private final float GOOD_VISIBLE = 0.0f;
    private final float BAD_VISIBLE = 0.5f;
    private final float NOT_VISIBLE = 1.0f;
    public final int CELLS_X;
    public final int CELLS_Y;
    public final int CELL_SIZE;
    private TextureRegion fogTexture;

    private  GameController gc;
    private float [][] fogOfWarMap;

    public FogOfWar(GameController gc) {
        this.gc = gc;

        CELLS_X = gc.getGameMap().getCellsX();
        CELLS_Y = gc.getGameMap().getCellsY();
        CELL_SIZE = gc.getGameMap().getCellSize();

        // взял траву и закрасил :)
        this.fogTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.fogOfWarMap = new float[CELLS_X][CELLS_Y];
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = 0; j < CELLS_Y; j++) {
                this.fogOfWarMap[i][j] = NOT_VISIBLE;
            }
        }
    }

    public void update () {
        int x = gc.getUnitController().getHero().cellX;
        int y = gc.getUnitController().getHero().cellY;

        for (int i = 0; i < CELLS_X; i++) {
            for (int j = 0; j < CELLS_Y; j++) {
                if (Utils.getCellsIntDistance(i, j, x, y) < 5) {
                    fogOfWarMap[i][j] = GOOD_VISIBLE;
                } else if (Utils.getCellsIntDistance(i, j, x, y) == 5) {
                    fogOfWarMap[i][j] = BAD_VISIBLE;
                } else {
                    this.fogOfWarMap[i][j] = NOT_VISIBLE;
                }
            }
        }

    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < CELLS_X; i++) {
            for (int j = CELLS_Y - 1; j >= 0; j--) {
                batch.setColor(0.0f, 0.0f, 0.0f, fogOfWarMap[i][j]);
                batch.draw(fogTexture, i * CELL_SIZE, j * CELL_SIZE);
            }
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
