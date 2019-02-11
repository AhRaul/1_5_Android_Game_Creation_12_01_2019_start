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

    public void resize(Rect worldBounds)  {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        System.out.println(worldBounds.getWidth()/10f);
        setPosition(worldBounds.getWidth()/10f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        setState();
        for (int i = 0; i < scale.length; i++) {
            if(scale[i].getState() >=0 && scale[i].getState() < 10) {
                scale[i].draw(batch);
            }
        }
    }

    /**
     * set для опредлеения позции и размеров полосы здоровья
     *
     * (логика метода не универсальна, учтен случай только конкретно для 10 ячеек здоровья)
     * @param width - ширина одной ячейки полосы здоровья
     */
    public void setPosition(float width) {
        float startpos = worldBounds.pos.x - width/2 - width*4;    //позиция первой ячейки здоровья
        for (int i = 0; i < scale.length; i++) {
            scale[i].setWidthProportion(width);                      //определение размеров одной ячейки
            scale[i].setBottom(worldBounds.getBottom());                    //расположение ячейки на оси y
            scale[i].pos.x = startpos;                                      //расположение ячейки на оси х
            startpos = startpos + width;                           //смещение на одну позицию вправо для следующей ячейки
        }
    }

    //задать состояние (цвет) шкалы здоровья
    public void setState() {
        if(mainShip.getHPPercent() >= 100) {
            for(int i = 9; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if(mainShip.getHPPercent() >= 90) {
            scale[9].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 8; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 80) {
            scale[9].setState(10);
            scale[8].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 7; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 70) {
            for(int i = 9; i >=8; i--) {
                scale[i].setState(10);
            }
            scale[7].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 6; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 60) {
            for(int i = 9; i >=7; i--) {
                scale[i].setState(10);
            }
            scale[6].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 5; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 50) {
            for(int i = 9; i >=6; i--) {
                scale[i].setState(10);
            }
            scale[5].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 4; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 40) {
            for(int i = 9; i >=5; i--) {
                scale[i].setState(10);
            }
            scale[4].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 3; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 30) {
            for(int i = 9; i >=4; i--) {
                scale[i].setState(10);
            }
            scale[3].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 2; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 20) {
            for(int i = 9; i >=3; i--) {
                scale[i].setState(10);
            }
            scale[2].setState(10 - mainShip.getHPPercent()%10);
            for(int i = 1; i >=0; i--) {
                scale[i].setState(0);
            }

        } else if (mainShip.getHPPercent() >= 10) {
            for(int i = 9; i >=2; i--) {
                scale[i].setState(10);
            }
            scale[1].setState(10 - mainShip.getHPPercent()%10);
            scale[0].setState(0);

        } else if (mainShip.getHPPercent() >= 0) {
            for(int i = 9; i >=1; i--) {
                scale[i].setState(10);
            }
            scale[0].setState(10 - mainShip.getHPPercent()%10);
        } else {
            for(int i = 9; i >=0; i--) {
                scale[i].setState(10);
            }
        }
    }
}
