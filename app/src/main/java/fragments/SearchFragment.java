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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.plaza19.sharelife.R;

import java.util.ArrayList;

import adapters.ListaUsuariosAdapter;
import modelos.Usuario;

public class SearchFragment extends Fragment{

    private View view;
    SearchView search;
    ListView listView;
    ArrayList<Usuario> User_list;
    ListaUsuariosAdapter adapter;
    private FirebaseFirestore firestore;

   public SearchFragment() {

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_search, container, false);
       search = view.findViewById(R.id.search_view);

       firestore = FirebaseFirestore.getInstance();
       ListView listView = view.findViewById(R.id.lv);
       User_list = new ArrayList<>();

            firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for (int i=0; i<queryDocumentSnapshots.size(); i++) {
                       Usuario aux = new Usuario();
                       aux.setUsername(queryDocumentSnapshots.getDocuments().get(i).get("user_name").toString());
                       User_list.add(aux);
                   }

               }
           });




       adapter = new ListaUsuariosAdapter(getContext(), R.layout.list_user_row, User_list);

       listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               //Toast.makeText(getContext(), adapter.getItem(position), Toast.LENGTH_SHORT).show();
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

