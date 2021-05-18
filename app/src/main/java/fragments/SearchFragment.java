package fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.plaza19.sharelife.R;

import java.util.ArrayList;
import java.util.List;

import Utils.UserManager;
import adapters.ListaUsuariosAdapter;
import modelos.Usuario;

public class SearchFragment extends Fragment{

    private View view;
    SearchView search;
    ListView listView;
    ArrayList<Usuario> user_list;
    ListaUsuariosAdapter adapter;
    UserManager userManager;

    private FirebaseFirestore firestore;

   public SearchFragment() {

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_search, container, false);
       search = view.findViewById(R.id.search_view);

       firestore = FirebaseFirestore.getInstance();
       ListView listView = view.findViewById(R.id.lv);
       user_list = new ArrayList<>();
       userManager = new UserManager();

            firestore.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
               @Override
               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for (int i=0; i<queryDocumentSnapshots.size(); i++) {
                       Usuario aux = new Usuario();
                       aux.setUsername(queryDocumentSnapshots.getDocuments().get(i).get("user_name").toString());
                       user_list.add(aux);
                   }

               }
           });


       adapter = new ListaUsuariosAdapter(getContext(), R.layout.list_user_row, user_list);

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

               userManager.getUsers_list_query(newText).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           List<DocumentSnapshot> documents = task.getResult().getDocuments();
                           adapter.clear();
                           for (int i=0; i<documents.size(); i++) {
                               Usuario aux = new Usuario();
                               aux.setUsername(documents.get(i).get("user_name").toString());
                               adapter.add(aux);
                           }
                       }else {
                           Toast.makeText(getContext(), "No se han podido los usuarios", Toast.LENGTH_SHORT);
                       }
                   }
               });

               return false;
           }
       });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getContext(), user_list.get(position).getUsername(), Toast.LENGTH_SHORT).show();
               openFragment(new PerfilFragment(user_list.get(position).getUsername()));
           }
       });




        return view;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}

