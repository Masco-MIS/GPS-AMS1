package masco.mis.software.mascoapproval.approval;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import masco.mis.software.mascoapproval.R;
import masco.mis.software.mascoapproval.Tapplication;

public class ApprovalType extends Activity {
    Button btnPr, btnSr, btnLa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_approval_type);
        btnPr = (Button) findViewById(R.id.btn_approvaltype_pr);
        btnSr = (Button) findViewById(R.id.btn_approvaltype_sr);
        btnLa = (Button) findViewById(R.id.btn_approvaltype_la);
        final Intent intent = new Intent(this, OperationActivity.class);
        btnPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);

            }
        });
        btnSr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        btnLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

    }
}
