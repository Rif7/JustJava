package com.example.android.justjava;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    static final double oneCupPrice = 2.55;
    static final double whippedCreamPrice = 0.45;
    static final double chocolatePrice = 0.30;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quantity", quantity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null)
        {
            quantity = savedInstanceState.getInt("quantity");
        }
        displayQuantity(quantity);
    }

    /**
     * This method returns calculated Price
     */
    public double calculatePrice() {
        double oneCubTotalPrice = oneCupPrice;
        if (hasWhippedCream()) {
            oneCubTotalPrice += whippedCreamPrice;
        }
        if (hasChocolate()) {
            oneCubTotalPrice += chocolatePrice;
        }
        return quantity * oneCubTotalPrice ;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        composeEmail("JustJava Order for " + getInputName());
    }

    public void composeEmail(String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(calculatePrice()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
            displayQuantity(quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot order more coffees", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            displayQuantity(quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Cannot order less coffees", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private String getInputName() {
        EditText inputNameView = findViewById(R.id.name_input);
        return inputNameView.getText().toString();
    }

    private boolean hasWhippedCream() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cream_check_box);
        return whippedCreamCheckBox.isChecked();
    }

    private boolean hasChocolate() {
        CheckBox chocolateCreamCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        return chocolateCreamCheckBox.isChecked();
    }

    private String getAnswer(boolean statement) {
        return statement ? getString(R.string.yes) : getString(R.string.no);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Prepare summary
     * @param orderPrice price of order
     * @return text summary
     */
    private String createOrderSummary(double orderPrice) {
        String priceText = getString(R.string.Name) + ": " + getInputName();
        priceText += "\n" + getString(R.string.add_whipped_cream) +"? " + getAnswer(hasWhippedCream());
        priceText += "\n" + getString(R.string.add_chocolate_cream) + "? " + getAnswer(hasChocolate());
        priceText += "\n" + getString(R.string.quantity) + " : " + quantity;
        priceText += "\n" + getString(R.string.total) + " : " + NumberFormat.getCurrencyInstance().format(orderPrice);
        priceText += "\n" + getString(R.string.thank_you) + " !";
        return priceText;
    }

}