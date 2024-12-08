import androidx.fragment.app.Fragment;
import java.io.*;

public class SecondFragment extends Fragment {

    public SecondFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_second, container, false);
    }
}