package mysnapp.app.dei.com.mysnapp.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import mysnapp.app.dei.com.mysnapp.data.remote.ResponseModel;
import mysnapp.app.dei.com.mysnapp.repos.HomeRepo;

public class ClaimPhotosViewModel extends ViewModel {

    private HomeRepo repo = HomeRepo.getInstance();

    void claimQRCode(String qrCode) {
        repo.getInstance().claimQRCode(qrCode);
    }

    void onDestroy () {
        repo.unsubcribe();
    }

    LiveData<ResponseModel> getClaimCodeResponse() {
        return repo.getClaimCodeResponse();
    }
    LiveData<Throwable> getClaimCodeError() {
        return repo.getClaimCodeError();
    }

}
