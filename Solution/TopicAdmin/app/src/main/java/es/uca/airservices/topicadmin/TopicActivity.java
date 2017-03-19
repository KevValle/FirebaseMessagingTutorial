package es.uca.airservices.topicadmin;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class TopicActivity extends AppCompatActivity implements OnClickListener{

    private FirebaseAuth firebaseAuth;

    //Variables de botones
    private Button buttonActivate;
    private Button buttonCancel;
    private Button buttonSubscribeAll;
    private Button buttonUnsubscribeAll;
    private Button buttonLogout;

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
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

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
                "Level_1",
                "Level_2",
                "Level_3",
                "Level_4",
                "Level_5"
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
        buttonLogout.setOnClickListener(this);

    }

    private void subscribeAll() {
        //Se suscribe a todas las combinaciones posibles
        //Si ya esta suscrito no lo hara de nuevo (controlado
        //por la API)

        for(int i=0; i < pollutants.length; i++) {
            //Recorrido de niveles dentro de contaminantes
            for(int j=0; j < levels.length; j++) {
                FirebaseMessaging.getInstance().subscribeToTopic(pollutants[i] + "_" + levels[j]);
            }
        }

    }

    private void unSubscribeAll() {
        //Se cancela la suscripcion de todas las combinaciones posibles
        //Si ya no esta suscrito no lo hara de nuevo (controlado
        //por la API)

        for(int i=0; i < pollutants.length; i++) {
            //Recorrido de niveles dentro de contaminantes
            for(int j=0; j < levels.length; j++) {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(pollutants[i] + "_" + levels[j]);
            }
        }

    }

    private void subscribe(String pollutant, String level) {
        //Se suscribe al contaminante y el nivel elegido
        FirebaseMessaging.getInstance().subscribeToTopic(pollutant + "_" + level);
    }

    private void unsubscribe(String pollutant, String level) {
        //Se cancela la suscripcion al contaminante y el nivel elegido
        FirebaseMessaging.getInstance().unsubscribeFromTopic(pollutant + "_" + level);
    }

    @Override
    public void onClick(View view) {
        String pollutant;
        String level;

        if(view == buttonActivate) {
            //Se suscribira al seleccionado
            pollutant = pollutantList.getSelectedItem().toString();
            level = levelList.getSelectedItem().toString();

            subscribe(pollutant, level);

            Toast.makeText(TopicActivity.this, "Se ha suscrito al tema seleccionado", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonCancel) {
            //Se cancelara la suscripcion del seleccionado
            pollutant = pollutantList.getSelectedItem().toString();
            level = levelList.getSelectedItem().toString();

            unsubscribe(pollutant, level);

            Toast.makeText(TopicActivity.this, "Se ha cancelado la suscripcion del tema seleccionado", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonSubscribeAll) {
            //Se suscribira a todos los disponibles
            subscribeAll();

            Toast.makeText(TopicActivity.this, "Se ha suscrito a todos los temas", Toast.LENGTH_SHORT).show();
        }

        if(view == buttonUnsubscribeAll) {
            //Se cancelaran todas las suscripciones
            unSubscribeAll();
            Toast.makeText(TopicActivity.this, "Se ha cancelado la suscripcion de todos los temas", Toast.LENGTH_SHORT).show();

        }

        if(view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
