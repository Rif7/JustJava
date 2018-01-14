package com.example.android.justjava;
/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity;
    static final double oneCupPrice = 2.55;
    static final double whippedCreamPrice = 0.45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantity = 0;
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
        return quantity * oneCubTotalPrice ;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        displayMessage(createOrderSummary(calculatePrice()));
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0) {
            quantity--;
            displayQuantity(quantity);
        }
    }

    private boolean hasWhippedCream() {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.cream_check_box);
        return whippedCreamCheckBox.isChecked();
    }

    private String getAnswer(boolean statement) {
        return statement ? "Yes" : "No";
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
        Log.v("MAinActivity", "Has whipped cream? " + getAnswer(hasWhippedCream()));
        String priceText = "Name: Kapitan Kunal";
        priceText += "\nAdd whipped cream? " + getAnswer(hasWhippedCream());
        priceText += "\nQuantity: " + quantity;
        priceText += "\nTotal: " + NumberFormat.getCurrencyInstance().format(orderPrice);
        priceText += "\nThank You!";
        return priceText;
    }

    private void displayMessage(String message) {
        TextView quantityTextView = (TextView) findViewById(R.id.order_summary_text_view);
        quantityTextView.setText(message);
    }


}