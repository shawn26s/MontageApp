package final_project.cs3174.montageapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SnapshotConfirmFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SnapshotConfirmFragment extends Fragment
{
    ImageView imageArea;
    TextView imageInfo;
    EditText enterMood;
    Button confirm;
    Button goBack;

    private OnFragmentInteractionListener mListener;

    public SnapshotConfirmFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_snapshot_confirm, container, false);
        imageArea = view.findViewById(R.id.imageArea);
        imageInfo = view.findViewById(R.id.imageInfo);
        enterMood = view.findViewById(R.id.enterMood);
        confirm = view.findViewById(R.id.confirmButton);
        goBack = view.findViewById(R.id.backButton);
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            mListener = (OnFragmentInteractionListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener
    {

    }
}
