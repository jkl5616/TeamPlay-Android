package skku.teamplay.fragment.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import skku.teamplay.R;
import skku.teamplay.activity.adapter.KanbanQuestlistAdapter;
import skku.teamplay.activity.dialog.QuestPopupDialog;
import skku.teamplay.models.MainQuest;
import skku.teamplay.models.Quest;



/**
 * Created by ddjdd on 2018-05-11.
 */

public class KanbanFragment extends Fragment {
    private int mPageNumber;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference;
    private  KanbanQuestlistAdapter adapter;
    ListView QuestList;
    TextView textKanbanTitle;
    MainQuest newMainQuest;

    String GroupID = "G_0001";

    public static KanbanFragment create(int pageNumber) {
        KanbanFragment fragment = new KanbanFragment();
        Bundle args = new Bundle();
        args.putInt("page", pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentLayout = inflater.inflate(R.layout.fragment_kanban_test, container, false);
        textKanbanTitle = (TextView)fragmentLayout.findViewById(R.id.textKanbanTitle);
        QuestList = (ListView) fragmentLayout.findViewById(R.id.quest_list);
        adapter =  new KanbanQuestlistAdapter();
        mPageNumber = getArguments().getInt("page");

        databaseReference = firebaseDatabase.getReference().child("Groups").child(GroupID).child("MainQuest").child("MainQuest").child("Main"+ Integer.toString(mPageNumber));
//        build();
        addFirebaseListener();

        QuestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Quest newQuest = (Quest)adapter.getItem(position);
                Intent intent = new Intent(getActivity(), QuestPopupDialog.class);
                intent.putExtra("isNew", false);
                intent.putExtra("pos", position);
                intent.putExtra("page", mPageNumber);

                newQuest.putExtraIntent(intent);

                getActivity().startActivityForResult(intent, 1);
            }
            public void onClick(View v) { }
        });
        return fragmentLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int pos;

        if (requestCode == 1) {
            switch (resultCode) {
                case 10:        // 제거
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        String id = data.getStringExtra("id");
                        adapter.deleteItem(pos);
                        databaseReference.child("Quest").child(id).removeValue();
                        newMainQuest.setCount(Integer.toString(Integer.valueOf(newMainQuest.getCount()) - 1));
                        databaseReference.child("Info").child("Info").setValue(newMainQuest);
                    }
                    break;
                case 100:       // 완료
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        String id = data.getStringExtra("id");
                        Quest finishedQuest = (Quest) adapter.getItem(pos);
                        adapter.makeFinish(pos, finishedQuest);
                        databaseReference.child("Quest").child(id).child("finish").setValue(true);
                    }
                    break;

                case 1000:      // 추가
                    final Quest newQuest = new Quest();
                    newQuest.getExtraString(data);
                    newQuest.setID(newMainQuest.getNextID());
                    databaseReference.child("Quest").child(newQuest.getID()).setValue(newQuest);
                    newMainQuest.setCount(Integer.toString(Integer.valueOf(newMainQuest.getCount()) + 1));
                    newMainQuest.setNextID(Integer.toString(Integer.valueOf(newMainQuest.getNextID()) + 1));
                    databaseReference.child("Info").child("Info").setValue(newMainQuest);

                    break;

                case 2000:      // 변경
                    pos = data.getIntExtra("pos", -1);
                    if (pos != -1) {
                        Quest modifiedQuest = new Quest();
                        modifiedQuest.getExtraString(data);
                        adapter.modifyItem(pos, modifiedQuest);
                        databaseReference.child("Quest").child(modifiedQuest.getID()).setValue(modifiedQuest);
                    }
                    break;
            }
        }
    }

    private void build() {
        String GroupID = "G_0001";
        DatabaseReference d2 =  firebaseDatabase.getReference().child("Groups").child(GroupID).child("MainQuest").child("Info").child("Info");
        MainQuest dm = new MainQuest("", "7", "7");
        d2.setValue(dm);
        DatabaseReference d1 =  firebaseDatabase.getReference().child("Groups").child(GroupID).child("MainQuest").child("MainQuest").child("Main"+ Integer.toString(mPageNumber));
        MainQuest nm = new MainQuest("자료수집", "4", "4");
        d1.child("Info").child("Info").setValue(nm);
        Quest nq = new Quest("내것, 미완", "설명", false, "18.01.01", "18.01.01","1111", "0", "지갑", "10");
        d1.child("Quest").child("0").setValue(nq);
        nq.setTitle("남의것, 미완");
        nq.setOwner("2222");
        nq.setID("1");
        d1.child("Quest").child("1").setValue(nq);
        nq.setTitle("새것, 미완");
        nq.setOwner("");
        nq.setID("2");
        d1.child("Quest").child("2").setValue(nq);
        nq.makeFinish();
        nq.setTitle("완료된것");
        nq.setID("3");
        d1.child("Quest").child("3").setValue(nq);
    }

    private void addFirebaseListener() {
        QuestList.setAdapter(adapter);

        databaseReference.child("Info").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                newMainQuest = dataSnapshot.getValue(MainQuest.class);
                textKanbanTitle.setText(newMainQuest.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child("Quest").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Quest newQuest = dataSnapshot.getValue(Quest.class);
                adapter.addItem(newQuest);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}