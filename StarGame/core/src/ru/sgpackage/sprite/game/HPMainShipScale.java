package ru.sgpackage.sprite.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.sgpackage.base.Sprite;
import ru.sgpackage.math.Rect;

public class HPMainShipScale extends Sprite {

    private TextureRegion region;

    //полоска здоровья главного корабля состоит из 10 ячеек, стоящих друг за другом.
    private HPOneScale[] scale = new HPOneScale[10];
    private MainShip mainShip;            //корабль, состояние
    private Rect worldBounds;       //границы проекции координат игрового мира

    public HPMainShipScale(TextureAtlas atlas, MainShip mainShip) {
        this.region = atlas.findRegion("hp10War");
        this.mainShip = mainShip;
        for (int i = 0; i < scale.length; i++) {
            scale[i] = new HPOneScale(region, 1, 10, 10);
            //scale[i].setState(9);
        }
    }

    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        System.out.println(worldBounds.getWidth() / 10f);
        setPosition(worldBounds.getWidth() / 10f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        setState();
        for (int i = 0; i < scale.length; i++) {
            if (scale[i].getState() >= 0 && scale[i].getState() < 10) {
                scale[i].draw(batch);
            }
        }
    }

    /**
     * set для опредлеения позции и размеров полосы здоровья
     * <p>
     * (логика метода не универсальна, учтен случай только конкретно для 10 ячеек здоровья)
     *
     * @param width - ширина одной ячейки полосы здоровья
     */
    public void setPosition(float width) {
        float startpos = worldBounds.pos.x - width / 2 - width * 4;    //позиция первой ячейки здоровья
        for (int i = 0; i < scale.length; i++) {
            scale[i].setWidthProportion(width);                      //определение размеров одной ячейки
            scale[i].setBottom(worldBounds.getBottom());                    //расположение ячейки на оси y
            scale[i].pos.x = startpos;                                      //расположение ячейки на оси х
            startpos = startpos + width;                           //смещение на одну позицию вправо для следующей ячейки
        }
    }

    //v 1.1 сокращенная форма записи логики метода setState()
    //задать состояние (цвет) шкалы здоровья
    public void setState() {
        if (mainShip.getHPPercent() >= 100) {   //если здоровье на максимуме
            for (int i = 9; i >= 0; i--) {
                scale[i].setState(0);
            }
            return;
        }

        for (int i = 90; i >= 0; i = i - 10) {  //промежуточное состояние здоровья
            if (mainShip.getHPPercent() >= i) {
                for (int j = 9; j >= (i / 10) + 1; j--) {
                    scale[j].setState(10);
                }
                scale[i / 10].setState(10 - mainShip.getHPPercent() % 10);
                for (int j = (i / 10) - 1; j >= 0; j--) {
                    scale[j].setState(0);
                }
                return;
            }
        }

        for (int j = 9; j >= 0; j--) {          //при нулевом уровне здоровья
            scale[j].setState(10);
        }
    }
}
