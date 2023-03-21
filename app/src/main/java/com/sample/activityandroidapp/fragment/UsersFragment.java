package com.sample.activityandroidapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sample.activityandroidapp.adapters.UsersAdapter;
import com.sample.activityandroidapp.databinding.DialogEditDeleteUserBinding;
import com.sample.activityandroidapp.databinding.DialogEditUserOnlyBinding;
import com.sample.activityandroidapp.databinding.FragmentUserBinding;
import com.sample.activityandroidapp.intefaces.AdapterListener;
import com.sample.activityandroidapp.intefaces.ServerListener;
import com.sample.activityandroidapp.models.Users;
import com.sample.activityandroidapp.preference.MyPref;
import com.sample.activityandroidapp.services.ServerRequests;

import java.util.List;

public class UsersFragment extends Fragment {

    FragmentUserBinding binding;

    DialogEditDeleteUserBinding editDeleteUserBinding;
    DialogEditUserOnlyBinding editBinding;
    Context mContext;
    List<Users> usersList;
    ServerRequests request;

    UsersAdapter adapter;

    AlertDialog alertDialog, alertEditDialog;
    Users selectedUser;

    public UsersFragment(Context mContext, List<Users> usersList) {
        this.mContext = mContext;
        this.usersList = usersList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(LayoutInflater.from(mContext), container, false);
        loadData();
        return binding.getRoot();
    }

    private void loadData() {
        request = new ServerRequests(mContext);

        request.getAllUsers(new ServerListener() {

            @Override
            public void onSuccess(String response) {
                usersList = new Gson().fromJson(response, new TypeToken<List<Users>>() {
                }.getType());

                adapter = new UsersAdapter(mContext, usersList);
                binding.lv.setAdapter(adapter);
                binding.lv.setOnItemClickListener((parent, view, position, id) -> {
                    selectedUser = usersList.get(position);
                    Users currentUser = new MyPref(mContext).getUsers();
                    if (currentUser.getUserID() == selectedUser.getUserID()) {
                        Toast.makeText(mContext, "Selected user is the current user", Toast.LENGTH_SHORT).show();
                    } else if (currentUser.getUserType() != 1) {
                        Toast.makeText(mContext, "Current User is not authorized to Edit/Delete User", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                        editDeleteUserBinding = DialogEditDeleteUserBinding.inflate(getLayoutInflater(), null, false);
                        mBuilder.setView(editDeleteUserBinding.getRoot());
                        setDialogListener();
                        alertDialog = mBuilder.create();
                        alertDialog.show();
                    }

                });
            }

            @Override
            public void onError() {

            }
        });
    }

    private void setDialogListener() {
        editDeleteUserBinding.btnDeleteUser.setOnClickListener(v -> {
            AlertDialog.Builder tBuilder = new AlertDialog.Builder(mContext);
            DialogInterface.OnClickListener dListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        request.deleteUser(Integer.toString(selectedUser.getUserID()), new ServerListener() {

                            @Override
                            public void onSuccess(String response) {
                                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                binding.lv.setAdapter(null);
                                loadData();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mContext, "Failed to delete user", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            };
            tBuilder.setMessage("Are You Sure You Want Delete This User?")
                    .setNegativeButton("Yes", dListener)
                    .setPositiveButton("No", dListener)
                    .setCancelable(false)
                    .show();

        });
        editDeleteUserBinding.btnEditUser.setOnClickListener(v -> {
            AlertDialog.Builder eBuilder = new AlertDialog.Builder(mContext);
            editBinding = DialogEditUserOnlyBinding.inflate(getLayoutInflater(), null, false);
            eBuilder.setView(editBinding.getRoot());
            setEditUserDialogListener();
            alertEditDialog = eBuilder.create();
            alertEditDialog.show();
        });

    }

    private void setEditUserDialogListener() {

        editBinding.editUsername.setText(selectedUser.getUserName());
        editBinding.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editBinding.editUsername.getText().toString();
                String password = editBinding.editPassword.getText().toString();
                String confirmPass = editBinding.editConfirmPassword.getText().toString();

                if (username.equals("") || password.equals("") || confirmPass.equals("")) {
                    Toast.makeText(mContext, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.equals(confirmPass)) {
                        Users nUser = new Users.UserBuilder()
                                .setUserID(selectedUser.getUserID())
                                .setUserName(username)
                                .setPassword(password)
                                .build();
                        request.editUser(nUser, new ServerListener() {

                            @Override
                            public void onSuccess(String response) {
                                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                                alertEditDialog.dismiss();
                                alertDialog.dismiss();
                                binding.lv.setAdapter(null);
                                loadData();
                            }

                            @Override
                            public void onError() {
                                Toast.makeText(mContext, "Failed To Update User", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(mContext, "Password doesn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
