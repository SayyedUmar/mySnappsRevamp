package mysnapp.app.dei.com.mysnapp.dagger.step2;

import dagger.Module;
import dagger.Provides;

@Module
public class BraavosModule {

    Cash cash;
    Soldiers soldiers;

    @Provides //Provides cash dependency
    Cash provideCash(){
        return new Cash();
    }

    @Provides //provides soldiers dependency
    Soldiers provideSoldiers(){
        return new Soldiers();
    }


    /*public BraavosModule(Cash cash, Soldiers soldiers){
        this.cash = cash;
        this.soldiers = soldiers;
    }

    @Provides  //Provides cash dependency
    Cash provideCash(){
        return cash;
    }

    @Provides //provides soldiers dependency
    Soldiers provideSoldiers(){
        return soldiers;
    }*/
}
