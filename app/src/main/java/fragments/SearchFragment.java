package fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.plaza19.sharelife.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment{

    private View view;
    SearchView search;
    ListView listView;
    ArrayList<String> stringArrayList;
    ArrayAdapter<String> adapter;
    private FirebaseFirestore firestore;

   public SearchFragment() {

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_search, container, false);
       search = view.findViewById(R.id.search_view);

       firestore = FirebaseFirestore.getInstance();
       ListView listView = view.findViewById(R.id.lv);
       stringArrayList = new ArrayList<>();

            firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for (int i=0; i<queryDocumentSnapshots.size(); i++) {
                       stringArrayList.add(queryDocumentSnapshots.getDocuments().get(i).get("user_name").toString());
                   }

               }
           });




       adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, stringArrayList);

       listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
           }
       });

       search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               adapter.getFilter().filter(newText);
               return false;
           }
       });


        return view;
    }



}

