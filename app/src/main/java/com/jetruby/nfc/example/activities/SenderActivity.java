package com.jetruby.nfc.example.activities;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jetruby.nfc.example.R;
import com.jetruby.nfc.example.cryptography.DiffieHellman;
import com.jetruby.nfc.example.nfc.OutcomingNfcManager;


public class SenderActivity extends AppCompatActivity implements OutcomingNfcManager.NfcActivity {

    private TextView tvOutcomingMessage;
    private EditText etOutcomingMessage;
    private Button btnSetOutcomingMessage;

    private NfcAdapter nfcAdapter;
    private OutcomingNfcManager outcomingNfccallback;
    private String data;
    private String encryptedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        if (!isNfcSupported()) {
            Toast.makeText(this, "Nfc is not supported on this device", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC disabled on this device. Turn on to proceed", Toast.LENGTH_SHORT).show();
        }

        initViews();

        //data = this.tvOutcomingMessage.getText().toString();


        // encapsulate sending logic in a separate class
        this.outcomingNfccallback = new OutcomingNfcManager(this);
        this.nfcAdapter.setOnNdefPushCompleteCallback(outcomingNfccallback, this);
        this.nfcAdapter.setNdefPushMessageCallback(outcomingNfccallback, this);

    }

    private void initViews() {
        this.tvOutcomingMessage = findViewById(R.id.tv_out_message);
        this.etOutcomingMessage = findViewById(R.id.et_message);
        this.btnSetOutcomingMessage = findViewById(R.id.btn_set_out_message);
        this.btnSetOutcomingMessage.setOnClickListener((v) -> setOutGoingMessage());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private boolean isNfcSupported() {
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        return this.nfcAdapter != null;
    }

    private void setOutGoingMessage() {
        data = this.etOutcomingMessage.getText().toString();    //Получаем исходящее сообщение из формы

        if (!data.equals("")) {
            //Keys exchange by Diffie-Hellman algorythm

            DiffieHellman diffieHellman = new DiffieHellman();    //public and private keys generated
            diffieHellman.getPublicKey();                         //send public key
                                                                  //receive public key
                                                                  //send partial key
                                                                  //receive partial key
                                                                  //generate full key

            //Chipher sending data

            /*Encryptor encryptor = new Encryptor();
            encryptedData = encryptor.encryptText(data, diffieHellman.getPrivateKey());*/
        }

        this.tvOutcomingMessage.setText(data);
    }

    @Override
    public String getOutcomingMessage() {
        return encryptedData;
    }

    @Override
    public void signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread(() ->
                Toast.makeText(SenderActivity.this, R.string.message_beaming_complete, Toast.LENGTH_SHORT).show());
    }


}
