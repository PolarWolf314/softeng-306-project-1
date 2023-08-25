package nz.ac.aucklanduni.se306project1;

import android.app.Application;

import androidx.annotation.Nullable;

import nz.ac.aucklanduni.se306project1.dataproviders.AuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.FirebaseAuthenticationProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.FirebaseItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.ItemDataProvider;
import nz.ac.aucklanduni.se306project1.dataproviders.UserDataProvider;

public class EngiWearApplication extends Application {

    @Nullable
    private UserDataProvider userDataProvider;
    private AuthenticationProvider authenticationProvider;
    private ItemDataProvider itemDataProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        this.itemDataProvider = new FirebaseItemDataProvider();
        this.authenticationProvider = new FirebaseAuthenticationProvider(this.itemDataProvider);
    }

    @Nullable
    public UserDataProvider getUserDataProvider() {
        return this.userDataProvider;
    }

    public void setUserDataProvider(@Nullable final UserDataProvider userDataProvider) {
        this.userDataProvider = userDataProvider;
    }

    public AuthenticationProvider getAuthenticationProvider() {
        return this.authenticationProvider;
    }

    public ItemDataProvider getItemDataProvider() {
        return this.itemDataProvider;
    }
}
