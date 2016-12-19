package masco.mis.software.mascoapproval;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HomeActivity extends  FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tapplication.FullScreen(this);
        setContentView(R.layout.activity_home);


    }

}
