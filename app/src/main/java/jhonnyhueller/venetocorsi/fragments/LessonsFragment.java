package jhonnyhueller.venetocorsi.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import jhonnyhueller.venetocorsi.R;
import jhonnyhueller.venetocorsi.adapters.LessonsAdapter;
import jhonnyhueller.venetocorsi.database.DatabaseAdapter;
import jhonnyhueller.venetocorsi.interfaces.CourseInterface;
import jhonnyhueller.venetocorsi.interfaces.HomeInterface;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link HomeInterface}
 * interface.
 */
public class LessonsFragment extends ListFragment implements AbsListView.OnItemClickListener{
    private final String TAG=getClass().getSimpleName();
    private DatabaseAdapter adapter;
    private CourseInterface mListener;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID_COURSE = "id_course";

    private long id_course;

    private AbsListView mListView;

    private ListAdapter mAdapter;

    public static LessonsFragment newInstance(long id_course) {
        LessonsFragment fragment = new LessonsFragment();
        Bundle args = new Bundle();
        args.putLong(ID_COURSE, id_course);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LessonsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            id_course = getArguments().getLong(ID_COURSE);
        }
        Cursor cursor=adapter.fetchLessonsOfCourse(id_course);
        mAdapter=new LessonsAdapter(getActivity(),cursor,0);
        setListAdapter(mAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lessons_list, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CourseInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CourseInterface");
        }
        adapter=new DatabaseAdapter(getActivity());
        adapter.open();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        adapter.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.lessonClick(id);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.lessonClick(id);
        }
    }
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

}
