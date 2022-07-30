package com.example.earningcoin.Withdraw;

import android.os.StrictMode;

public class Application  extends android.app.Application {

    //withdraw amazon 1 set (18)
    @Override
    public void onCreate() {
        super.onCreate ();

        StrictMode.VmPolicy.Builder  builder = new StrictMode.VmPolicy.Builder ();
        StrictMode.setVmPolicy ( builder.build () );

    }
}
