package final_project.cs3174.montageapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmDeleteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ConfirmDeleteFragment extends Fragment implements View.OnClickListener
{
    private OnFragmentInteractionListener mListener;
    Button yes;
    Button no;

    public ConfirmDeleteFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_confirm_delete, container, false);
        yes = v.findViewById(R.id.yesDelete);
        yes.setOnClickListener(this);
        no = v.findViewById(R.id.noGoBack);
        no.setOnClickListener(this);
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
        if (v.getId() == no.getId())
        {
            mListener.onClickConfirm(false);
        }
        else if (v.getId() == yes.getId())
        {
            mListener.onClickConfirm(true);
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
        void onClickConfirm(boolean b);
    }
}
