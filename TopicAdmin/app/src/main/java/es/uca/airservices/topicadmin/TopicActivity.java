package es.uca.airservices.topicadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TopicActivity extends AppCompatActivity implements OnClickListener{

    private FirebaseAuth firebaseAuth;

    //Variables de botones
    private Button buttonActivate;
    private Button buttonCancel;
    private Button buttonSubscribeAll;
    private Button buttonUnsubscribeAll;

    //Variables de desplegables
    private Spinner pollutantList;
    private Spinner levelList;

    private String[] pollutants;
    private String[] levels;

    private ArrayAdapter<String> pollutantAdapter;
    private ArrayAdapter<String> levelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }


        //botones
        buttonActivate = (Button) findViewById(R.id.buttonActivate);
        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSubscribeAll = (Button) findViewById(R.id.buttonSubscribeAll);
        buttonUnsubscribeAll = (Button) findViewById(R.id.buttonUnsubscribeAll);

        //Desplegables
        pollutantList = (Spinner) findViewById(R.id.pollutantList);
        levelList = (Spinner) findViewById(R.id.alertList);

        /**
         * Elementos que apareceran en los desplegables
         */
        pollutants = new String[]{
                "CO",
                "NO2",
                "O3",
                "PM2.5",
                "PM10",
                "SO2"
        };

        levels = new String[]{
                "Level 1",
                "Level 2",
                "Level 3",
                "Level 4",
                "Level 5"
        };


        /**
         * Se le da los valores a las listas
         */
        pollutantAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pollutants);
        levelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, levels);

        pollutantList.setAdapter(pollutantAdapter);
        levelList.setAdapter(levelAdapter);

        buttonActivate.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);
        buttonSubscribeAll.setOnClickListener(this);
        buttonUnsubscribeAll.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == buttonActivate) {
            //Se suscribira al seleccionado
        }

        if(view == buttonCancel) {
            //Se cancelara la suscripcion del seleccionado
        }

        if(view == buttonSubscribeAll) {
            //Se suscribira a todos los disponibles
        }

        if(view == buttonUnsubscribeAll) {
            //Se cancelaran todas las suscripciones
        }
    }
}
