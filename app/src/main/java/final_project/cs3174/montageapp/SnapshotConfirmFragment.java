package final_project.cs3174.montageapp;

import android.content.Context;
import android.graphics.Bitmap;
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
public class SnapshotConfirmFragment extends Fragment implements View.OnClickListener
{
    ImageView imageArea;
    TextView imageInfo;
    EditText enterMood;
    Button confirm;
    Button goBack;
    String snapshotInfo = "";
    Bitmap image = null;

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
        if (image != null)
        {
            imageArea.setImageBitmap(image);
        }
        imageInfo = view.findViewById(R.id.imageInfo);
        imageInfo.setText(snapshotInfo);
        enterMood = view.findViewById(R.id.enterMood);
        confirm = view.findViewById(R.id.confirmButton);
        confirm.setOnClickListener(this);
        goBack = view.findViewById(R.id.backButton);
        goBack.setOnClickListener(this);
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
     * Sets the current snapshot object for the fragment and updates the imageInfo.
     * @param ss
     */
    public void setSnapshotInfo(Snapshot ss, Bitmap bitmap)
    {
        String date = ss.getPhotoName().substring(4, 6) + "/"
                + ss.getPhotoName().substring(6, 8) + "/"
                + ss.getPhotoName().substring(2, 4);
        image = bitmap;
        snapshotInfo = "Date: " + date + "\n\n" +
                "Location: " + ss.getLocation() + "\n\n"
                + "Weather: " + ss.getWeather() + "\n";
    }

    public void onClick(View v)
    {
        if (v.getId() == goBack.getId())
        {
            mListener.onSnapshotConfirmClick(0, null);
        }
        else if (v.getId() == confirm.getId())
        {
            // Tell MainActivity that confirm was clicked and send back the mood String from the text entry box
            mListener.onSnapshotConfirmClick(1, enterMood.getText().toString().replace("'", ""));
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
        void onSnapshotConfirmClick(int i, String m);
    }
}
