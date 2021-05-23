package com.plaza19.sharelife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import Utils.TokenManager;
import fragments.ChatFragment;
import fragments.PerfilFragment;
import fragments.PrincipalFragment;
import fragments.SearchFragment;

public class PrincipalActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tokenManager = new TokenManager();
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new PrincipalFragment());
        createToken();
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            openFragment(new PrincipalFragment());
                            return true;
                        case R.id.navigation_chats:
                            openFragment(new ChatFragment());
                            return true;
                        case R.id.navigation_buscar:
                            openFragment(new SearchFragment());
                            return true;
                        case R.id.navigation_perfil:
                            openFragment(new PerfilFragment());
                            return true;
                    }
                    return false;
                }
            };

    private void createToken() {
        tokenManager.create(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }
}