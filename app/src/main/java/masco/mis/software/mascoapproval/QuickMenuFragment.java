package masco.mis.software.mascoapproval;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import masco.mis.software.mascoapproval.approval.ApprovalType;
import masco.mis.software.mascoapproval.chat.ChatActivity;

public class QuickMenuFragment extends Fragment {

    Button home_message;
    Button home_approval;
    Button home_voicecall;
    Button home_chart;
    Button home_operation;

    public QuickMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quick_menu, container, false);

        home_message = (Button) v.findViewById(R.id.btn_home_message);
        home_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        home_approval = (Button) v.findViewById(R.id.btn_home_approval);

        home_approval.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ApprovalType.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
