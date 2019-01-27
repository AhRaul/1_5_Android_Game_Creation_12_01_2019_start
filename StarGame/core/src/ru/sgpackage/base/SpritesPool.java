package ru.sgpackage.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    protected List<T> activeObjects = new ArrayList<T>();
    protected List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {    //поиск свободных обьектов
            object = newObject();       //если их нет, создать новый обьект
        } else {
            object = freeObjects.remove(freeObjects.size() -1);
        }
        activeObjects.add(object);
        System.out.println("active/free:" + activeObjects.size() + "/" + freeObjects.size());          //вывод информации о массиве пуль
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (int i = 0; i<activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            sprite.update(delta);
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {  //рисование активного объекта из списка
        for (int i = 0; i<activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            sprite.draw(batch);
        }
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i<activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if(sprite.isDestroyed()) {
                free(sprite);
                i--;
            }
        }
    }

    public void free(T object) {    //метод освобождения обьекта
        activeObjects.remove(object);
        freeObjects.add(object);
        object.flushDestroy();      //т.к. обьект не помечен на удаление, подготовка к повторному использованию
        System.out.println("active/free:" + activeObjects.size() + "/" + freeObjects.size());          //вывод информации о массиве пуль
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }
}
