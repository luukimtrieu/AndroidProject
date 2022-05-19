package com.example.androidproject.Controller;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidproject.MainActivity;
import com.example.androidproject.Model.Database.AppDatabase;
import com.example.androidproject.Model.Database.DayStatus;
import com.example.androidproject.Model.SharedViewModel;
import com.example.androidproject.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDayStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDayStatusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AppDatabase db;
    private SharedViewModel viewModel;

    String emotion_type = "";
    boolean sunny = false;
    boolean cloudy = false;
    boolean snowy = false;
    boolean rainy = false;
    boolean windy = false;
    boolean friends = false;
    boolean GFBF = false;
    boolean family = false;
    boolean acquaintance = false;
    boolean none = false;
    String note = "";
    String photo_url = "";

    Button btnSunny;
    Button btnCloudy;
    Button btnWindy;
    Button btnSnowy;
    Button btnRainy;
    Button btnFriends;
    Button btnGFBF;
    Button btnAcquaintance;
    Button btnFamily;
    Button btnNone;

    RadioGroup radioGroup;
    RadioButton checkedRadioButton;
    EditText editTextNote;
    ImageView imageViewPhoto;
    TextView tvYearDayMonth;
    String selectedDate;

    private Button btnDone;

    private ActivityResultLauncher<String> resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            photo_url = result.toString();
            imageViewPhoto.setImageURI(result);
        }
    });

    public AddDayStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDayStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDayStatusFragment newInstance(String param1, String param2) {
        AddDayStatusFragment fragment = new AddDayStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add_day_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Navigation.findNavController(view).navigate(R.id.action_addDayStatusFragment_to_calendarFragment);
                return false;
            }
        });

        if(getArguments() != null) {
            if(getArguments().getInt("exist") == 1) {
                viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                DayStatus dayStatus = db.dayStatusDao().getOneByDate(getDate());
                viewModel.setDayStatus(dayStatus);

                tvYearDayMonth = view.findViewById(R.id.tvYearDayMonth);
                tvYearDayMonth.setText(getDate());

                btnDone = view.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View iview) {
                        radioGroup = view.findViewById(R.id.radioGroup);
                        btnSunny = view.findViewById(R.id.buttonSunny);
                        btnCloudy = view.findViewById(R.id.buttonCloudy);
                        btnWindy = view.findViewById(R.id.buttonWindy);
                        btnSnowy = view.findViewById(R.id.buttonSnowy);
                        btnRainy = view.findViewById(R.id.buttonRainy);
                        btnFriends = view.findViewById(R.id.buttonFriends);
                        btnGFBF = view.findViewById(R.id.buttonGFBF);
                        btnAcquaintance = view.findViewById(R.id.buttonAcquaintance);
                        btnFamily = view.findViewById(R.id.buttonFamily);
                        btnNone = view.findViewById(R.id.buttonNone);
                        editTextNote = view.findViewById(R.id.editTextNote);
                        imageViewPhoto = view.findViewById(R.id.imageViewTodayPhoto);

                        int checkedButtonID = radioGroup.getCheckedRadioButtonId();
                        checkedRadioButton = view.findViewById(checkedButtonID);

                        if (checkedButtonID != -1) {
                            initData();
                            updateData();
                            Toast.makeText(getContext(), "Update successfully", Toast.LENGTH_LONG).show();
                            Navigation.findNavController(iview).navigate(R.id.action_addDayStatusFragment_to_calendarFragment);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Please select your emotion", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else if(getArguments().getInt("exist") == 0) {
                db = Room.databaseBuilder(getContext(), AppDatabase.class, "database.db").allowMainThreadQueries().build();
                tvYearDayMonth = view.findViewById(R.id.tvYearDayMonth);
                tvYearDayMonth.setText(getDate());

                btnDone = view.findViewById(R.id.btnDone);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View iview) {
                        radioGroup = view.findViewById(R.id.radioGroup);
                        btnSunny = view.findViewById(R.id.buttonSunny);
                        btnCloudy = view.findViewById(R.id.buttonCloudy);
                        btnWindy = view.findViewById(R.id.buttonWindy);
                        btnSnowy = view.findViewById(R.id.buttonSnowy);
                        btnRainy = view.findViewById(R.id.buttonRainy);
                        btnFriends = view.findViewById(R.id.buttonFriends);
                        btnGFBF = view.findViewById(R.id.buttonGFBF);
                        btnAcquaintance = view.findViewById(R.id.buttonAcquaintance);
                        btnFamily = view.findViewById(R.id.buttonFamily);
                        btnNone = view.findViewById(R.id.buttonNone);
                        editTextNote = view.findViewById(R.id.editTextNote);
                        imageViewPhoto = view.findViewById(R.id.imageViewTodayPhoto);

                        int checkedButtonID = radioGroup.getCheckedRadioButtonId();
                        checkedRadioButton = view.findViewById(checkedButtonID);

                        if (checkedButtonID != -1) {
                            initData();
                            insertData();
                            Navigation.findNavController(iview).navigate(R.id.action_addDayStatusFragment_to_calendarFragment);
                        }
                        else
                            Toast.makeText(getContext(), "Please select your emotion", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public String getMonthYearFromDate(String date)
    {
        return date.substring(0, date.length() - 3);
    }

    public String getDate()
    {
        if(getArguments() != null)
            if(Integer.parseInt(getArguments().getString("day")) < 10)
                return getArguments().getString("year month") + "-0" + getArguments().getString("day");
        return getArguments().getString("year month") + "-" + getArguments().getString("day");
    }

    private void updateData()
    {
        DayStatus dayStatus = new DayStatus();
        String date = getDate();

        dayStatus.emotion_type = emotion_type;
        dayStatus.rainy = rainy;
        dayStatus.sunny = sunny;
        dayStatus.windy = windy;
        dayStatus.cloudy = cloudy;
        dayStatus.snowy = snowy;
        dayStatus.friends = friends;
        dayStatus.acquaintance = acquaintance;
        dayStatus.GFBF = GFBF;
        dayStatus.none = none;
        dayStatus.family = family;
        dayStatus.note = note;
        dayStatus.date = date;
        dayStatus.photo_URL = photo_url;

        int a = db.dayStatusDao().update(dayStatus.emotion_type, dayStatus.note, dayStatus.photo_URL, dayStatus.sunny,
                dayStatus.rainy, dayStatus.cloudy, dayStatus.snowy, dayStatus.windy, dayStatus.friends, dayStatus.family
        , dayStatus.acquaintance, dayStatus.GFBF, dayStatus.none, dayStatus.date);
    }

    private void insertData() {
        DayStatus dayStatus = new DayStatus();
        String date = getDate();

        dayStatus.emotion_type = emotion_type;
        dayStatus.rainy = rainy;
        dayStatus.sunny = sunny;
        dayStatus.windy = windy;
        dayStatus.cloudy = cloudy;
        dayStatus.snowy = snowy;
        dayStatus.friends = friends;
        dayStatus.acquaintance = acquaintance;
        dayStatus.GFBF = GFBF;
        dayStatus.none = none;
        dayStatus.family = family;
        dayStatus.note = note;
        dayStatus.date = date;
        dayStatus.photo_URL = photo_url;

        db.dayStatusDao().insert(dayStatus);
    }

    public void initData() {
        emotion_type = checkedRadioButton.getText().toString();
        photo_url = imageViewPhoto.getContentDescription().toString();

        if (btnSunny.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.sunny, null).getConstantState())
            sunny = true;

        if (btnCloudy.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.cloudy, null).getConstantState())
            cloudy = true;

        if (btnWindy.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.wind, null).getConstantState())
            windy = true;

        if (btnRainy.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.rainy, null).getConstantState())
            rainy = true;

        if (btnSnowy.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.snowy, null).getConstantState())
            snowy = true;

        if (btnFriends.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.friend, null).getConstantState())
            friends = true;

        if (btnGFBF.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.love, null).getConstantState())
            GFBF = true;

        if (btnAcquaintance.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.acquaintance, null).getConstantState())
            acquaintance = true;

        if (btnNone.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.none, null).getConstantState())
            none = true;

        if (btnFamily.getBackground().getConstantState() ==
                ResourcesCompat.getDrawable(getResources(), R.drawable.family, null).getConstantState())
            family = true;
        note = editTextNote.getText().toString();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addDayStatusFragment_to_calendarFragment);
        return super.onOptionsItemSelected(item);
    }
}

