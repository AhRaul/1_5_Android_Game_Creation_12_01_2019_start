package ru.sgpackage.pool;

import ru.sgpackage.base.SpritesPool;
import ru.sgpackage.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
