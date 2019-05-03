package mysnapp.app.dei.com.mysnapp.dagger.step1;

import javax.inject.Inject;

public class War {

    private static final String TAG = "War";

    private Starks starks;

    private Boltons boltons;

    @Inject
    public War(Starks starks, Boltons bolton) {
        this.starks = starks;
        this.boltons = bolton;
    }


    public void prepare() {
        starks.prepareForWar();
        boltons.prepareForWar();
    }

    public void report() {
        starks.reportToWar();
        boltons.reportToWar();
    }

}
