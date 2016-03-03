package cat.olivadevelop.universalwar.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.UUID;

import cat.olivadevelop.universalwar.UniversalWarGame;
import cat.olivadevelop.universalwar.tools.ColorGame;
import cat.olivadevelop.universalwar.tools.GameLogic;
import cat.olivadevelop.universalwar.tools.Publicidad;

public class AndroidLauncher extends AndroidApplication implements Publicidad {

    private static final String AD_UNIT_ID_BANNER = "ca-app-pub-2083012446340886/1235136300";
    private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2083012446340886/8758403107";
    protected AdView adView;
    protected View gameView;
    private InterstitialAd interstitialAd;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //init(new InvadersGame(), config);
        settings = getSharedPreferences(GameLogic.prefsName, MODE_PRIVATE);

        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

        AdView admobView = createAdView();
        /*if (settings.getBoolean("localReg", true)) {
            //layout.addView(admobView);
        }*/
        GameLogic.setShowADS(settings.getBoolean("localReg", true));
        View gameView = createGameView(config);
        layout.addView(gameView);

        setContentView(layout);
        startAdvertising(admobView);
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                //Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private AdView createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID_BANNER);
        adView.setId(R.id.banner); // this is an arbitrary id, allows for relative positioning in createGameView()
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        adView.setLayoutParams(params);
        adView.setBackgroundColor(Color.rgb565(ColorGame.valueOf("1a1a25")));
        return adView;
    }

    private View createGameView(AndroidApplicationConfiguration cfg) {
        gameView = initializeForView(new UniversalWarGame(this, generateIDDevice()), cfg);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.BELOW, adView.getId());
        gameView.setLayoutParams(params);
        return gameView;
    }

    @Override
    public void showOrLoadInterstital() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                        //Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
                    } else {
                        AdRequest interstitialRequest = new AdRequest.Builder().build();
                        interstitialAd.loadAd(interstitialRequest);
                        //Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private void startAdvertising(AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.loadAd(adRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) adView.resume();
    }

    @Override
    public void onPause() {
        if (adView != null) adView.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (adView != null) adView.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (settings.getBoolean("isFirstRun", true)) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.msg2))
                    .setMessage(getString(R.string.msg1))
                    .setNeutralButton(getString(R.string.msg3), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            settings.edit().putBoolean("isFirstRun", false).apply();
                        }
                    }).show();
        }
    }

    private String generateIDDevice() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }
}
