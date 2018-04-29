package final_project.cs3174.montageapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MontageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MontageFragment extends Fragment
{
    private OnFragmentInteractionListener mListener;
    TextView dateLocation;
    ImageView snapshotImage;
    TextView moodWeather;
    MainActivity mainActivity;
    ArrayList<Snapshot> snapshots;
    int timeSetting;
    int orderSetting;
    MontageAsync montageAsync;

    public MontageFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_montage, container, false);
        dateLocation = v.findViewById(R.id.textDetails1);
        moodWeather = v.findViewById(R.id.textDetails2);
        snapshotImage = v.findViewById(R.id.snapshotImage);
        mainActivity = mListener.setMainActivity();
        snapshots = mainActivity.sdbman.getAllRecords();
        setOrdering();
        montageAsync = new MontageAsync();
        montageAsync.execute();
        //snapshotImage.setImageBitmap(mainActivity.loadImageFromStorage(snapshots.get(0).getPhotoName()));
        return v;
    }

    public void setTimeAndOrder(int time, int order, int size)
    {
        this.timeSetting = time;
        if (timeSetting == -1 && size != 0)
        { // sets the time such that all photos take 1 minute to view.
            timeSetting = 60000 / size;
        }

        this.orderSetting = order;
    }

    private void setOrdering()
    {
        if (orderSetting == 1)
        {
            ArrayList<Snapshot> reversed = new ArrayList<>();
            for (int i = snapshots.size() - 1; i >= 0; i--) // reverse the order of the snapshots
            {
                reversed.add(snapshots.get(i));
            }
            snapshots = reversed;
        }
        else if (orderSetting == 2)
        {
            ArrayList<Snapshot> randomized = new ArrayList<>();
            Random rand = new Random();
            while (!snapshots.isEmpty())
            {
                randomized.add(snapshots.remove(abs(rand.nextInt() % snapshots.size())));
            }
            snapshots = randomized;
        }
    }

    public void updateDisplay(int i)
    {
        Snapshot snap = snapshots.get(i);
        String date = snap.getPhotoName().substring(4, 6) + "/"
                + snap.getPhotoName().substring(6, 8) + "/"
                + snap.getPhotoName().substring(2, 4);
        dateLocation.setText(date + "\n" + snap.getLocation());
        snapshotImage.setImageBitmap(mainActivity.loadImageFromStorage(snap.getPhotoName()));
        moodWeather.setText(snap.getMood() + "\n" + snap.getWeather());
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
        MainActivity setMainActivity();
        void removeMontageFrag();
    }

    private class MontageAsync extends AsyncTask<Integer, Integer, Void>
    {
        @Override // handles image changes
        protected Void doInBackground(Integer...integers)
        {
            for (int i = 0; i < snapshots.size(); i++)
            {
                publishProgress(i);
                try
                {
                    Thread.sleep(timeSetting);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            publishProgress(-1);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            if (values[0] != -1)
            {
                updateDisplay(values[0]);
            }
            else
            {
                Toast.makeText(mainActivity.getApplicationContext(),
                        "That's it!", Toast.LENGTH_SHORT).show();
                mainActivity.removeMontageFrag();
            }
        }
    }
}
