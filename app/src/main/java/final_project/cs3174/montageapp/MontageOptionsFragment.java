package final_project.cs3174.montageapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MontageOptionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MontageOptionsFragment extends Fragment implements View.OnClickListener
{
    private OnFragmentInteractionListener mListener;

    RadioButton time0;
    RadioButton time1;
    RadioButton time2;
    RadioButton time3;
    RadioButton time4;
    int timeSetting; // time for each photo in the montage in milliseconds
    Button start;

    public MontageOptionsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_montage_options, container, false);
        time0 = v.findViewById(R.id.seconds0_5);
        time0.setOnClickListener(this);
        time0.setChecked(true);
        timeSetting = 500;
        time1 = v.findViewById(R.id.seconds1);
        time1.setOnClickListener(this);
        time2 = v.findViewById(R.id.seconds2);
        time2.setOnClickListener(this);
        time3 = v.findViewById(R.id.seconds5);
        time3.setOnClickListener(this);
        time4 = v.findViewById(R.id.secondsFit);
        time4.setOnClickListener(this);
        start = v.findViewById(R.id.startMontage);
        return v;
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

    @Override
    public void onClick(View v)
    {
        if (v.getId() == time0.getId())
        {
            timeSetting = 500;
        }
        else if (v.getId() == time1.getId())
        {
            timeSetting = 1000;
        }
        else if (v.getId() == time2.getId())
        {
            timeSetting = 2000;
        }
        else if (v.getId() == time3.getId())
        {
            timeSetting = 5000;
        }
        else if (v.getId() == time4.getId())
        {
            timeSetting = -1; // will require extra processing later, based on the number of photos saved and
        }
        else if (v.getId() == start.getId())
        {

        }
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
        void onStartClicked(int time);
    }
}
