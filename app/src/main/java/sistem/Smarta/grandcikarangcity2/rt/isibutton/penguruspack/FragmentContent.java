package sistem.Smarta.grandcikarangcity2.rt.isibutton.penguruspack;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import sistem.Smarta.grandcikarangcity2.R;


public class FragmentContent extends Fragment {
  private static final String key_Title ="Content";


    public FragmentContent() {
        // Required empty public constructor
    }


    public static FragmentContent newInstance(String param1) {
        FragmentContent fragment = new FragmentContent();
        Bundle args = new Bundle();
        args.putString(key_Title, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString(key_Title);
        ((TextView)view.findViewById(R.id.title)).setText(title);

    }
}