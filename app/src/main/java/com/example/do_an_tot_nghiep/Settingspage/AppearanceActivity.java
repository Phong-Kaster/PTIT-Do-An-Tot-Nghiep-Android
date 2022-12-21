package com.example.do_an_tot_nghiep.Settingspage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.example.do_an_tot_nghiep.Adapter.FilterOptionAdapter;
import com.example.do_an_tot_nghiep.Helper.GlobalVariable;
import com.example.do_an_tot_nghiep.Helper.Tooltip;
import com.example.do_an_tot_nghiep.MainActivity;
import com.example.do_an_tot_nghiep.Model.Option;
import com.example.do_an_tot_nghiep.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Phong-Kaster
 * @since 30-11-2022
 * Appearance Activity accommodates dark mode and language in application
 */
public class AppearanceActivity extends AppCompatActivity {

    private final String TAG = "Appearance Activity";
    private ImageButton btnBack;
    private Spinner sprLanguage;
    private SwitchCompat switchDarkMode;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance);

        setupComponent();
        setupEvent();
        setupSpinnerLanguage();
    }

    /**
     * @since 30-11-2022
     */
    private void setupComponent()
    {
        GlobalVariable globalVariable = (GlobalVariable) this.getApplication();
        sharedPreferences = this.getApplication()
                .getSharedPreferences(globalVariable.getSharedReferenceKey(), MODE_PRIVATE);

        int darkMode = sharedPreferences.getInt("darkMode", 1);// 1 is off, 2 is on

        btnBack = findViewById(R.id.btnBack);
        sprLanguage = findViewById(R.id.sprLanguage);

        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchDarkMode.setChecked(false);
        if(darkMode == 2)
        {
            switchDarkMode.setChecked(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tooltip.setLocale(this, sharedPreferences);
    }

    /**
     * @since 30-11-2022
     * setup spinner language
     */
    private void setupSpinnerLanguage()
    {
        /*prepare options*/
        List<Option> list = new ArrayList<>();
        Option option1 = new Option(R.drawable.ic_vietnamese_square, getString(R.string.vietnamese));
        Option option2 = new Option(R.drawable.ic_english_square, getString(R.string.english));
        Option option3 = new Option(R.drawable.ic_germany_square, getString(R.string.deutsch));

        list.add(option1);
        list.add(option2);
        list.add(option3);



        /*create spinner*/
        FilterOptionAdapter filterAdapter = new FilterOptionAdapter(this, list);
        sprLanguage.setAdapter(filterAdapter);
        sprLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = list.get(position).getName();
                times++;
                setupLanguage(text);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        /*set selected language in spinner*/
        String applicationLanguage = sharedPreferences.getString("language", getString(R.string.vietnamese));
        String vietnamese = getString(R.string.vietnamese);
        String english = getString(R.string.english);
        String germany = getString(R.string.deutsch);

//        System.out.println(TAG);
//        System.out.println("application language: " + applicationLanguage);

        if(Objects.equals(applicationLanguage, vietnamese))
        {
            sprLanguage.setSelection(0);
        }
        else if(Objects.equals(applicationLanguage, english))
        {
            sprLanguage.setSelection(1);
        }
        else if(Objects.equals(applicationLanguage, germany))
        {
            sprLanguage.setSelection(2);
        }
    }

    /**
     * @since 30-11-2022
     * setup event
     */
    private void setupEvent()
    {
        btnBack.setOnClickListener(view->finish());

        /*Switch On == turn on dark mode | Switch Off == turn off dark mode*/
        switchDarkMode.setOnCheckedChangeListener((compoundButton, flag) -> {
            int value;
            if(flag)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                value = AppCompatDelegate.MODE_NIGHT_YES;
            }
            else
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                value = AppCompatDelegate.MODE_NIGHT_NO;
            }
            sharedPreferences.edit().putInt("darkMode",value).apply();
        });
    }

    /**
     * @since 30-11-2022
     * set up language for the application
     * times is the number of we select on spinner
     * times == 1 means the first time we open this activity so that we ignore the first time.
     */
    int times = 0;
    private void setupLanguage(String language)
    {
        if( times == 1)
        {
            return;
        }

        String vietnamese = getString(R.string.vietnamese);
        String english = getString(R.string.english);
        String deutsch = getString(R.string.deutsch);

        Locale myLocale = new Locale("en");

        if(Objects.equals(language, vietnamese))
        {
            myLocale = new Locale("vi");
        }
        if( Objects.equals(language, deutsch))
        {
            myLocale = new Locale("de");
        }

        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(myLocale);

        resources.updateConfiguration(configuration, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);

        //save the application's language in ROM
        sharedPreferences.edit().putString("language",language ).apply();
    }
}