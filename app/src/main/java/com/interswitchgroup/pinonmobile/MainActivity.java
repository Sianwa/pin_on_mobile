package com.interswitchgroup.pinonmobile;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.interswitchgroup.pinonmobile.databinding.ActivityMainBinding;
import com.interswitchgroup.pinonmobile.models.Account;
import com.interswitchgroup.pinonmobile.models.Institution;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.fab.setOnClickListener(view -> {
            String rsaPubKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo+eP/jVrcZ5G3A6glDSnY2kpdz67G+3z0Oc0XeI+7kHfdmwyjdC2nalTVyEW4iGLzMUvWX1iK5W1Ozrq8E8NRTy/PNTWCd71+mgvFzt6Fgab8lTRMv8+oniM6X4i9mbOkB0nKaByGmY/DDDiYoCBehvB32KaJIyr39LNke5iB98+3TWhv/cZsJ8LabhdHXgWXsnWZonke0hSkH+lpnph+zFHi1z1bLqghC2zPAUCzBbaYoDVM6DGWJ9tJVHYjMDHjNJC617PEEtOBwIWv5G19Aw51DvtD3cIwGzuA5f5xe4LWc2tZ0RVfUekIvJzwe0MvTjRHqZWCX/08FmayGlNfwIDAQAB";
            String rsaPrivateKeyString = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCj54/+NWtxnkbcDqCUNKdjaSl3Prsb7fPQ5zRd4j7uQd92bDKN0LadqVNXIRbiIYvMxS9ZfWIrlbU7OurwTw1FPL881NYJ3vX6aC8XO3oWBpvyVNEy/z6ieIzpfiL2Zs6QHScpoHIaZj8MMOJigIF6G8HfYpokjKvf0s2R7mIH3z7dNaG/9xmwnwtpuF0deBZeydZmieR7SFKQf6WmemH7MUeLXPVsuqCELbM8BQLMFtpigNUzoMZYn20lUdiMwMeM0kLrXs8QS04HAha/kbX0DDnUO+0PdwjAbO4Dl/nF7gtZza1nRFV9R6Qi8nPB7Qy9ONEeplYJf/TwWZrIaU1/AgMBAAECggEAD5eXRKUbUAcxEX0YvJCXvebCqZPfo+QKljxwaF/+AZdlpTPcyU3qGWyCv56nuSJc1MGLZBV/8cp/n59Wuz6h8gy52pUauXyq5MPleu3PCupdCnwUHKhYcodKTGoR9GPKUb5cO+MGB8njRIsf9iPobU/XrSMJq+Fv9k5s/O1zCPGGZb4Mu7TkqIQSaShQO1mvLXvQ3Bp9CsxdD5SDJs8G2vxZLOlEbChPr7QW4khkN05AVHT8xxc4QUL1IVVf+VrVy68wUs129R4V9BTXojAEu1R9mbkleTWwH50UH/Sd3zQeiQ2MoK7b/kGb5mYqnlDoZnsGtng51Zqff5RPnRDntQKBgQDNV612LWSN5U+z4eS4Q8wDWNftsPnFYTab9r1RfRDtvZuzv7Pc+TpLmWWk/kMMBbaco2luFdTHxg7Xo9fk2rEdLaigE6qz71ulL3/qCaLLdw2sAT3UFKdeQ5d4hRNV1hFLIaX1UXJHT1RsLHIoJzVEHi9rgy+9T+RqUev0jYXqswKBgQDMVuNJlAZjFclhdUcyTTP84k21jKvvVcwpCtHhtZG1XTbMzJt4RaVqvanpo844kYKauPCMdXwIaFgwpV08sa3PGcoQemDx8LUPtyO+f95+msFK57f59qMEoGKUJT1ieKO/SdQr3rySo+Vf1WcP5rOl6HVR6xWtnKkmNuDR4kFoBQKBgE4Yg3tHpk+lH9v9FLzT5Bp9xpm6zjO4VkmY3MXKOA8DJt2FEkX/b6Fi9Np8bUl8PshyCd35ZZSZCfoPcUOzvNqpC9HdyPVoGkXHu/FpusWBQOzjB/3J4SGjuU735bOml6soX/LeCAWA8U221a/ZwZNnm4dbPGPWp7ub7o5y6LSrAoGAYBHJsmIhzpwDngphesjJVG+hUWXdwBx6bCFmI9QVuUsl5Iud3KIB73lUVUBqSDZBTTT+A0uJEPrd26EjgNGYgfICClU/FwCwX78e0wWTObrQfcMLwD2wzxAIyNXpUk6dzeWMF0QVLGxZ/wB6AAPbGnl8DxOTkZhB/nF2qbbSQXECgYEAxKXTRNBY7D95lBEh/ESgxTW7RrXT9swcpgZnT0+ASD2WISfYA9VrRrmyHSm9BZDM0tQpHvylnekXtvla+I6lAXU9WV8A/6WpZsYkA2CUaqC5ILvMwuDJKtZxNWiXK0yCmzKTECCiR4no5n69IONVi2VolxqHgNKpfdX48QhzQbw=";
            try {
                Institution institution = new Institution("IKIA971FB5996EADBD16534494CB87B90D1DB3EAD105"
                        ,"ONcmxGU4B+A+qaHp+/Nw19yO9w117PY36/SxsH1A1Wc=",54,
                        rsaPubKeyString,rsaPrivateKeyString,"deee79ba-f912-11eb-9a03-0242ac130003");

                Account account = new Account("4726390060000056","1410916");
                PinOnMobile pinOnMobile = PinOnMobile.getInstance(MainActivity.this,institution,account);
                pinOnMobile.setPin(
                        response -> Snackbar
                                .make(view, "Set Pin correctly occurred", Snackbar.LENGTH_LONG)
                                .show(),
                        error -> Snackbar
                                .make(view, error.getMessage(), Snackbar.LENGTH_LONG)
                                .show());
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                        .show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}