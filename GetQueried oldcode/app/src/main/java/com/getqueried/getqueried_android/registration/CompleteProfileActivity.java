package com.getqueried.getqueried_android.registration;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.crashlytics.android.Crashlytics;
import com.getqueried.getqueried_android.R;
import com.getqueried.getqueried_android.dashboard.DashboardActivity;
import com.getqueried.getqueried_android.utils.Constants;
import com.getqueried.getqueried_android.utils.GeneralUtils;
import com.getqueried.getqueried_android.utils.NetworkActivity;
import com.getqueried.getqueried_android.utils.SharedPrefUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.getqueried.getqueried_android.utils.Constants.User.BIRTH;
import static com.getqueried.getqueried_android.utils.Constants.User.COUNTRYOFRESIDENCE;
import static com.getqueried.getqueried_android.utils.Constants.User.EDUCATIONLEVEL;
import static com.getqueried.getqueried_android.utils.Constants.User.GENDER;

public class CompleteProfileActivity extends NetworkActivity implements DatePickerDialog.OnDateSetListener {

    private static final String HEADER_TITLE = "COMPLETE YOUR PROFILE";
    private static final String TAG = "CompleteProfileActivity";
    private static final int TWENTY_YEARS = 20;
    private SharedPrefUtils.UserProfile userProfile;

    private String selectedGender;
    private String levelOfEducation = "edu1";

    private String[] level_of_education = {
            "Primary School", "High School", "Vocational training", "Bachelor Degree", "Master Degree"};

    @Bind(R.id.spinner_education_level)
    MaterialSpinner spinnerEducationalLevel;

    @Bind(R.id.editText_birthday)
    EditText birthdayEditText;

    @Bind(R.id.toggle_male)
    ToggleButton genderMale;

    @Bind(R.id.toggle_female)
    ToggleButton genderFemale;

    /*@Bind(R.id.textView_male)
    TextView maleTextView;

    @Bind(R.id.textView_female)
    TextView femaleTextView;*/

    @Bind(R.id.textView_country_of_residence)
    TextView countryOfResidenceView;

    @OnClick({R.id.toggle_male, R.id.toggle_female})
    public void toggleGender(View view) {
        int viewID = view.getId();
        if (viewID == R.id.toggle_male) {
            selectedGender = "male";
            genderFemale.setChecked(false);
            genderMale.setChecked(true);
            //maleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            //femaleTextView.setTextColor(Color.parseColor("#808080"));
        } else {
            // if not male then female
            selectedGender = "female";
            genderMale.setChecked(false);
            genderFemale.setChecked(true);
            //femaleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            //maleTextView.setTextColor(Color.parseColor("#808080"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        ButterKnife.bind(this);

        userProfile = SharedPrefUtils.getUserProfile(this);

        GeneralUtils.setToolbarTitle(this, HEADER_TITLE);
        getEducationalLevel();
    }

    @OnTouch(R.id.editText_birthday)
    boolean setupBirthday(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Calendar today = Calendar.getInstance();
            today.add(Calendar.YEAR, -TWENTY_YEARS);
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    this,
                    today.get(Calendar.YEAR),
                    today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");
        }
        return true;
    }


    @OnClick(R.id.birthday_picker)
    void setUpBirthDayPicker() {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void getEducationalLevel() {
        spinnerEducationalLevel.setHeight(16);
        spinnerEducationalLevel.setTextSize(16);
        spinnerEducationalLevel.setPadding(16, 0, 0, 0);
        spinnerEducationalLevel.setBackground(getResources().getDrawable(R.drawable.shape_custom_spinner, null));
        spinnerEducationalLevel.setItems(level_of_education);
        spinnerEducationalLevel.setOnItemSelectedListener((view, position, id, item) -> {
            Crashlytics.log(Log.DEBUG, TAG, "Selected " + item.toString() + " Position : " + position);
            switch (position) {
                case 0:
                    levelOfEducation = "edu1";
                    break;
                case 1:
                    levelOfEducation = "edu2";
                    break;
                case 2:
                    levelOfEducation = "edu3";
                    break;
                case 3:
                    levelOfEducation = "edu4";
                    break;
                case 5:
                    levelOfEducation = "edu5";
                    break;
            }
        });
    }

    private String dateFormatted;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month;
        String day;
        if ((monthOfYear + 1) < 10)
            month = "0" + (monthOfYear + 1);
        else
            month = "" + (monthOfYear + 1);

        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;
        else
            day = "" + dayOfMonth;

        dateFormatted = year + "-" + month + "-" + day + "T00:00:00Z";
        Crashlytics.log(Log.DEBUG, TAG, "Formatted Date : " + dateFormatted);
        String dateString = month + "/" + day + "/" + year;
        birthdayEditText.setText(dateString);
    }

    // API to update user demography
    @OnClick(R.id.btn_submit_user_demography)
    void postUserDemography() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(BIRTH, dateFormatted);
            jsonObject.put(GENDER, selectedGender);
            jsonObject.put(EDUCATIONLEVEL, levelOfEducation);
            jsonObject.put(COUNTRYOFRESIDENCE, countryOfResidenceView.getText().toString().toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // API update user profile demography
        Crashlytics.log(Log.DEBUG, TAG, "updateUserProfileDemography params : " + jsonObject.toString());
        postJsonStringUrlEncodedWithToken(Constants.URL.updateUserProfileDemography, jsonObject,
                response1 -> {
                    userProfile.saveUserDemography(birthdayEditText.getText().toString(),
                            selectedGender, levelOfEducation, countryOfResidenceView.getText().toString().toLowerCase(), null);
                    SharedPrefUtils.saveUserProfile(this, userProfile);
                    Crashlytics.log(Log.DEBUG, TAG, "User profile demography updated.");
                    GeneralUtils.routeAndClearBackStack(this, DashboardActivity.class);
                }, null);
    }
}
