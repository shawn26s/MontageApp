package final_project.cs3174.montageapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener
{
    private OnFragmentInteractionListener mListener;
    CheckBox locationWeather;
    Button deleteRecords;
    MainActivity mainActivity;

    public SettingsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        locationWeather = v.findViewById(R.id.checkbox);
        locationWeather.setOnClickListener(this);
        deleteRecords = v.findViewById(R.id.deleteRecords);
        deleteRecords.setOnClickListener(this);
        mainActivity = mListener.setMainActivity();
        if (mainActivity.recordLocation)
        {
            locationWeather.setChecked(true);
        }
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
        if (v.getId() == deleteRecords.getId())
        {
            mListener.confirmDelete();
        }
        else if (v.getId() == locationWeather.getId())
        {
            SharedPreferences.Editor editor = mainActivity.sharedPref.edit();
            if (locationWeather.isChecked())
            {
                editor.putBoolean(mainActivity.RECORD_LOCATION, true);
                mListener.setRecordLocation(true);
            }
            else
            {
                editor.putBoolean(mainActivity.RECORD_LOCATION, false);
                mListener.setRecordLocation(false);
            }
            editor.apply();
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
        MainActivity setMainActivity();
        void confirmDelete();
        void setRecordLocation(boolean rec);
    }
}
