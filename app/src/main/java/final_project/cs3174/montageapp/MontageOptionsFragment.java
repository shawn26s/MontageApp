package final_project.cs3174.montageapp;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

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
    RadioButton order0;
    RadioButton order1;
    RadioButton order2;
    int timeSetting; // time for each photo in the montage in milliseconds
    int order; // 0 = chronological, 1 = reverse chronological, 2 = random
    Button selectMusic;
    Button start;
    Uri musicUri;

    public static final int REQUEST_MUSIC_CODE = 42;

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
        time1 = v.findViewById(R.id.seconds1);
        time1.setOnClickListener(this);
        time2 = v.findViewById(R.id.seconds2);
        time2.setOnClickListener(this);
        time3 = v.findViewById(R.id.seconds5);
        time3.setOnClickListener(this);
        time4 = v.findViewById(R.id.secondsFit);
        time4.setOnClickListener(this);
        order0 = v.findViewById(R.id.oldToNew);
        order0.setOnClickListener(this);
        order1 = v.findViewById(R.id.newToOld);
        order1.setOnClickListener(this);
        order2 = v.findViewById(R.id.randOrder);
        order2.setOnClickListener(this);
        order0.setChecked(true);
        selectMusic = v.findViewById(R.id.selectMusic);
        selectMusic.setOnClickListener(this);
        start = v.findViewById(R.id.startMontage);
        start.setOnClickListener(this);
        musicUri = null;
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
            timeSetting = -1; // will require extra processing later, based on the number of photos saved
        }
        else if (v.getId() == order0.getId())
        {
            order = 0;
        }
        else if (v.getId() == order1.getId())
        {
            order = 1;
        }
        else if (v.getId() == order2.getId())
        {
            order = 2;
        }
        else if (v.getId() == selectMusic.getId())
        {
            Intent musicIntent = new Intent(Intent.ACTION_GET_CONTENT,
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(musicIntent, REQUEST_MUSIC_CODE);
        }
        else if (v.getId() == start.getId())
        {
            mListener.onStartClicked(timeSetting, order, musicUri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MUSIC_CODE && resultCode == RESULT_OK)
        {
            musicUri = data.getData();
            Toast.makeText(mListener.setMainActivity().getApplicationContext(), "Music added!", Toast.LENGTH_SHORT).show();
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
        void onStartClicked(int time, int order, Uri musicUri);
        MainActivity setMainActivity();
    }
}
