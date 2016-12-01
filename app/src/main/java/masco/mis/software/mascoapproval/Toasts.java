package masco.mis.software.mascoapproval;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class Toasts {
	
	protected static void biscuit(Context context, String _message, int color) {
		Toast toast = Toast.makeText(context, _message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL | Gravity.FILL_HORIZONTAL, 0, 0);
		//toast.setGravity(Gravity.BOTTOM, 0, 0);
		TextView textView = new TextView(context);
		textView.setBackgroundColor(color);
		textView.setTextColor(Color.BLACK);
		textView.setTextSize(16);
		Typeface typeface = Typeface.create("Normal", Typeface.BOLD);
		textView.setTypeface(typeface);
		textView.setPadding(15, 5, 15, 5);
		textView.setText(_message);
		textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		toast.setView(textView);
		toast.show();
	}

}
