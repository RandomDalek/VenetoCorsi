package jhonnyhueller.venetocorsi.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.adapters.GridAdapter;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;
import jhonnyhueller.venetocorsi.models.CourseCellInformation;
import jhonnyhueller.venetocorsi.models.SpacesItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeInterface} interface
 * to handle interaction events.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    private HomeInterface mListener;
    private RecyclerView recyclerView;
    private GridAdapter gridAdapter;
    private GridLayoutManager layoutManager;
    private ArrayList<CourseCellInformation>cellInfo;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    public CourseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_course, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler_view_course_cells);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cellInfo=getCellInfo();
        gridAdapter=new GridAdapter(cellInfo);
        layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setAdapter(gridAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<CourseCellInformation> getCellInfo() {
        DatabaseAdapter adapter=new DatabaseAdapter(getActivity());
        adapter.open();
        ArrayList<CourseCellInformation>cellInformation=new ArrayList<>();
        CourseCellInformation info;
        String[] titles=adapter.getCourseTitles();
        for (String title : titles) {
            info = new CourseCellInformation(title, R.drawable.police);
            cellInformation.add(info);
        }
        adapter.close();
        return cellInformation;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (HomeInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement HomeInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
