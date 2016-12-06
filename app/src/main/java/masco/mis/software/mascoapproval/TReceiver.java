package masco.mis.software.mascoapproval;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import masco.mis.software.mascoapproval.service.LocationTService;

public class TReceiver extends BroadcastReceiver {
    public TReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        try {
            Intent startServiceIntent = new Intent(context, LocationTService.class);
            context.startService(startServiceIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Some error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //   throw new UnsupportedOperationException("Not yet implemented");
    }
}
