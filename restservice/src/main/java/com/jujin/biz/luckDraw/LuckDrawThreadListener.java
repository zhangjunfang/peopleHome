package com.jujin.biz.luckDraw;

import java.util.Observable;
import java.util.Observer;

public class LuckDrawThreadListener implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Thread[LuckDrawPersistence]——shutdown(exception)");
        LuckDrawPersistenceService run = LuckDrawPersistenceService.getInstance();
        run.addObserver(this);
        new Thread(run).start();
        System.out.println("Thread[LuckDrawPersistence]——restart");

    }

}