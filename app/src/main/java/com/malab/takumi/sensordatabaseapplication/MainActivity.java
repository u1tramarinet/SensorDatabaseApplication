package com.malab.takumi.sensordatabaseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.SensorDatabaseSystem;
import com.malab.takumi.sensordatabaseapplication.sensordatabasesystem.SensorDeviceManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    SensorDatabaseSystem SDS;
    List<SensorDeviceManager> deviceManagerList;

    Button button_go, button_start, button_stop;
    EditText editText_userName;
    TextView textView_sensorState, textView_logcat;

    private boolean bLogging,bSensorRunning = false;
    private int iCountLogcat = 0;

    private final int STATE_NOTLOGIN = 0;
    private final int STATE_LOGGING = 1;
    private final int STATE_INITIALIZED = 2;
    private final int STATE_RUNNING = 3;
    private final int STATE_STANDBY = 4;

    private String testUserName = "testuser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDS = new SensorDatabaseSystem(this);

        // create deviceManager List
        deviceManagerList = new ArrayList<>();
        deviceManagerList.add(new SensorSmartPhone(this, SDS));

        setComponents();

        initSensorDeviceManagers();
    }

    private void setComponents(){
        //UserName
        editText_userName = findViewById(R.id.editText_useName);
        editText_userName.setText(testUserName);
        button_go = findViewById(R.id.button_go);
        button_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        //Sensor
        textView_sensorState = findViewById(R.id.textView_sensorState);
        button_start = findViewById(R.id.button_start);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerSensorDeviceManagers();
            }
        });
        button_stop  = findViewById(R.id.button_stop);
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick unregister");
                unregisterSensorDeviceManagers();
            }
        });

        //Logcat
        textView_logcat = findViewById(R.id.textView_logcat);

        setUIstate(STATE_NOTLOGIN);
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
        unregisterSensorDeviceManagers();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterSensorDeviceManagers();
    }

    void initSensorDeviceManagers(){
        // init deviceManagers
        for(SensorDeviceManager sdm : deviceManagerList){
            sdm.init();
        }
    }

    void login(){
        if(bSensorRunning || bLogging)return;
        String useName = editText_userName.getText().toString();
        bLogging = true;
        setUIstate(STATE_LOGGING);

        long id = (long)(Math.random() * 100);
        loginFinished(id);
    }

    void loginFinished(long id){
        bLogging = false;
        setUIstate(STATE_INITIALIZED);
        addLogcat("Login is finished successfully! id = " + id);
    }

    void registerSensorDeviceManagers(){
        // register device sensors
        if(!setBSensorRunning(true))return;
        for(SensorDeviceManager sdm : deviceManagerList){
            sdm.registerListener();
        }
    }

    void unregisterSensorDeviceManagers(){
        // unregister device sensors
        if(!setBSensorRunning(false))return;
        for(SensorDeviceManager sdm : deviceManagerList){
            sdm.unregisterListener();
        }
    }

    boolean setBSensorRunning(boolean bSenRun){
        if(bSenRun && !bSensorRunning){//センサ起動
            setUIstate(STATE_RUNNING);
        }else if(!bSenRun && bSensorRunning){//センサ停止
            setUIstate(STATE_STANDBY);
        }else{
            return false;
        }
        bSensorRunning = bSenRun;
        return true;
    }

    void addLogcat(String logcat){
        if(iCountLogcat == 5){
            textView_logcat.setText("");
        }
        String text = textView_logcat.getText().toString() + "\n" + logcat;
        textView_logcat.setText(text);
        iCountLogcat++;
    }

    void setUIstate(int state){
        boolean[] enables_login;// edit,button
        boolean[] enables_sensor;// button button
        String text_state;
        switch (state){
            case(STATE_NOTLOGIN):
                enables_login = new boolean[]{true, true};
                enables_sensor = new boolean[]{false, false};
                text_state = getText(R.string.text_state_notlogin).toString();
                break;
            case(STATE_LOGGING):
                enables_login = new boolean[]{false, false};
                enables_sensor = new boolean[]{false, false};
                text_state = getText(R.string.text_state_logging).toString();
                break;
            case(STATE_INITIALIZED):
                enables_login = new boolean[]{true, true};
                enables_sensor = new boolean[]{true, false};
                text_state = getText(R.string.text_state_initialized).toString();
                break;
            case(STATE_RUNNING):
                enables_login = new boolean[]{false, false};
                enables_sensor = new boolean[]{false, true};
                text_state = getText(R.string.text_state_running).toString();
                break;
            case(STATE_STANDBY):
                enables_login = new boolean[]{true, true};
                enables_sensor = new boolean[]{true, false};
                text_state = getText(R.string.text_state_standby).toString();
                break;
            default:
                return;
        }
        editText_userName.setEnabled(enables_login[0]);
        button_go.setEnabled(enables_login[1]);
        button_start.setEnabled(enables_sensor[0]);
        button_stop.setEnabled(enables_sensor[1]);
        textView_sensorState.setText(text_state);
    }
}