package final_project.cs3174.montageapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class MoodFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    MainActivity mainActivity;
    ArrayList<Snapshot> snapshots;
    GraphView graphView;

    public MoodFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mood, container, false);

        mainActivity = mListener.getMoodMainActivity();
        snapshots = mainActivity.sdbman.getAllRecords();
        graphView = v.findViewById(R.id.graph);

        createGraph();
        return v;
    }

    private void createGraph() {
        List<DataPoint> dataPoints = new ArrayList<>();
        int i = 0;
        int val;

        for (Snapshot snapshot : snapshots) {
            val = getMoodValue(snapshot.getMood());
            if (val != -1) dataPoints.add(new DataPoint(i, val));
            i++;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[dataPoints.size()]));
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
    }

    private int getMoodValue(String mood) {
        switch (mood) {
            case "Very Happy":
                return 5;
            case "Happy":
                return 4;
            case "Neutral":
                return 3;
            case "Confused":
                return 2;
            case "Sad":
                return 1;
            default:
                return -1;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
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
    public interface OnFragmentInteractionListener {
        MainActivity getMoodMainActivity();
    }
}
