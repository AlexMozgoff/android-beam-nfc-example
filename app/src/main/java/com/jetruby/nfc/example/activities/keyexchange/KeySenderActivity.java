package com.jetruby.nfc.example.activities.keyexchange;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import com.jetruby.nfc.example.R;
import com.jetruby.nfc.example.cryptography.DiffieHellman;
import com.jetruby.nfc.example.nfc.OutcomingNfcManager;

public class KeySenderActivity extends AppCompatActivity implements OutcomingNfcManager.NfcActivity {

    private Button sendPublicKey;
    private Button sendPartialKey;
    private DiffieHellman diffieHellman;
    private String dataToSend;
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.key_exchange_activity);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        sendPublicKey = findViewById(R.id.send_public_key_button);
        sendPublicKey.setOnClickListener((v) -> SendPublicKey());

        sendPartialKey = findViewById(R.id.send_partial_key_button);
        sendPartialKey.setOnClickListener((v) -> SendPartialKey());

        diffieHellman = new DiffieHellman();
    }

    public void SendPublicKey() {
        dataToSend = diffieHellman.getPublicKey().toString();
        OutcomingNfcManager.counter = 0;                                    //0 - for public key
    }
    public void SendPartialKey() {
        dataToSend = diffieHellman.generatePartialKey().toString();
        OutcomingNfcManager.counter = 1;                                    //1 - for partial key
    }

    @Override
    public String getOutcomingMessage() {
        return dataToSend;
    }

    @Override
    public void signalResult() {
        runOnUiThread(() ->
                Toast.makeText(
                        KeySenderActivity.this,
                        R.string.message_beaming_complete,
                        Toast.LENGTH_SHORT).
                        show());
    }
}
