package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.trabalho.models.Trip;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        // Precisa transferir a responsabilidade para a presenter e adicionar databinding
        EditText departureDate = (EditText) findViewById(R.id.inputDepartureDate);
        EditText arrivalDate = (EditText) findViewById(R.id.inputArrivalDate);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(departureDate, smf);
        MaskTextWatcher mtw2 = new MaskTextWatcher(arrivalDate, smf);
        departureDate.addTextChangedListener(mtw);
        arrivalDate.addTextChangedListener(mtw2);
    }

    public void submit(View view) throws ParseException {
        EditText inputDepartureDate = (EditText) findViewById(R.id.inputDepartureDate);
        EditText inputArrivalDate = (EditText) findViewById(R.id.inputArrivalDate);
        EditText inputCity = (EditText) findViewById(R.id.inputCity);
        EditText inputCountry = (EditText) findViewById(R.id.inputCountry);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date departureDate = format.parse(inputDepartureDate.getText().toString());
        Date arrivalDate = format.parse(inputArrivalDate.getText().toString());

        Trip trip = new Trip(departureDate, arrivalDate, inputCountry.getText().toString(), inputCity.getText().toString());
        Intent intent = new Intent(getApplicationContext(), TripDetailsActivity.class);
        intent.putExtra("objTrip", trip);
        startActivity(intent);
    }

}