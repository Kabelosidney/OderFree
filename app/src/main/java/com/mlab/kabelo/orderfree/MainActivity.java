package com.mlab.kabelo.orderfree;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity=2;
    boolean hasRiccofy=false;
    boolean hasChocolate=false;
    String customerName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage = "Total = R";
        hasRiccofy= CheckForRiccofy();
        hasChocolate=CheckForChocolate();
        customerName=getCustomerName();
        int price = calculatePrice();
        createOrderSummary(price);
    }



    public void increment(View view) {
        if(quantity>=100) {
            ShowMessage("You can't order more than 100 Cups of Coffee");
            return;
        }
        else{
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    public void ShowMessage(String messageText)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,messageText,duration);
        toast.show();
    }

    public void decrement(View view) {
        if(quantity<=1) {
            ShowMessage("You can't order less than 1 Cup of Coffee");
            return;
        }
        else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText( number);
    }
    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        int price = 0;
        int topping=0;
        if(hasChocolate){
            topping+=2;
        }
        if(hasRiccofy){
            topping+=1;
        }
        price= (quantity*(5+topping));

        return price;
    }

    private boolean CheckForRiccofy()
    {
        CheckBox checkBox = (CheckBox) findViewById(R.id.hasWhippedCream);
        boolean isChecked = checkBox.isChecked();
        return isChecked;
    }

    private boolean CheckForChocolate()
    {
        CheckBox checkBox = (CheckBox) findViewById(R.id.hasChocolate);
        boolean isChecked = checkBox.isChecked();
        return isChecked;
    }

    

    private String getCustomerName()
    {
        EditText txtCustomerName = (EditText)findViewById(R.id.txtCustomerName);
        String name = txtCustomerName.getText().toString();
        return name;
    }

    /*
    * Return a message with all the information of the order
    * @param price is the price of a cup of coffee
    * */
    private void createOrderSummary(int price){
        String message = getString(R.string.name)+" "+customerName+" \n";
        message+=getString(R.string.order_summary_hot_coffee)+" : "+hasRiccofy+"\n";
        message+=getString(R.string.order_summary_chocolate)+" : "+hasChocolate+"\n";
        message+=getString(R.string.order_summary_quantity)+" : "+quantity+"\n";
        message+=getString(R.string.order_summary_price)+": "+price+"\n";
        message+=getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        String[] addresses = new String[1];
        addresses[0]="kabelosidney@gmail.com";
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject)+ " "+customerName);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
