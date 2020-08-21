package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.service.autofill.OnClickAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * This is class to create BottomSheetDialog in order to show to user the pop up window with the address
 */
public class BottomSheetDialog extends BottomSheetDialogFragment{
    TextView textView;
    private Double lngtude ,lantude;
    private Button button;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /**
         * Here i set specific style in order to set some specific colors etc.
         */
        inflater.getContext().setTheme(R.style.AdressSheetDialog);
        View v = inflater.inflate(R.layout.address_sheet_layout,container,false);
        textView = v.findViewById(R.id.textView4);
        button = v.findViewById(R.id.button);
        /**
         * This is the buttonListener and use it to say what button will do when user clicks "Συνέχεια"
         */
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoursquareListView.class);
                /**
                 * Here when user press "Συνέχεια i take some values like longitude and latitude also take and address
                 */
                intent.putExtra("lng", lngtude);
                intent.putExtra("lan",lantude);
                intent.putExtra("address",textView.getText());
                startActivity(intent);
            }
        });
        return v;
    }

    public Double getLngtude() {
        return lngtude;
    }

    public void setLngtude(Double lngtude) {
        this.lngtude = lngtude;
    }

    public Double getLantude() {
        return lantude;
    }

    public void setLantude(Double lantude) {
        this.lantude = lantude;
    }
}
