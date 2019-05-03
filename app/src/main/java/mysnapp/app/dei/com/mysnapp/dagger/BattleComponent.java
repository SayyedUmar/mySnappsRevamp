package mysnapp.app.dei.com.mysnapp.dagger;

import dagger.Component;
import mysnapp.app.dei.com.mysnapp.dagger.step1.Boltons;
import mysnapp.app.dei.com.mysnapp.dagger.step1.Starks;
import mysnapp.app.dei.com.mysnapp.dagger.step1.War;
import mysnapp.app.dei.com.mysnapp.dagger.step2.BraavosModule;
import mysnapp.app.dei.com.mysnapp.dagger.step2.Cash;
import mysnapp.app.dei.com.mysnapp.dagger.step2.Soldiers;

@Component(modules = BraavosModule.class)
public interface BattleComponent {
    War getWar();
    Starks getStarks();
    Boltons getBoltons();

    Cash getCash();
    Soldiers getSoldiers();
}
