package saha.sampras.androidhashtag;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class MainActivity extends ActionBarActivity {

    //public static variable for demo purpose
    //we'll use this ArrayList in other activity
    public static ArrayList<String> messagesList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private ListView messagesListView;
    private EditText messageEditText;
    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variables
        messagesListView = (ListView) findViewById(R.id.messagesListView);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        addButton = (Button) findViewById(R.id.addButton);

        //Extend an ArrayAdapter so that we can make
        // our TextView HashTag compatible
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, messagesList) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView hashView;

                if(convertView == null) {
                    hashView = new TextView(MainActivity.this);
                }
                else {
                    hashView = (TextView) convertView;
                }

                //Get the message from list and set as text
                String message = messagesList.get(position);
                hashView.setText(message);

                //Pattern to find if there's a hash tag in the message
                //i.e. any word starting with a # and containing letter or numbers or _
                Pattern tagMatcher = Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");

                //Scheme for Linkify, when a word matched tagMatcher pattern,
                //that word is appended to this URL and used as content URI
                String newActivityURL = "content://saha.sampras.androidhashtag.tagdetailsactivity/";

                //Attach Linkify to TextView
                Linkify.addLinks(hashView, tagMatcher, newActivityURL);

                return hashView;
            }
        };

        //assign custom adapter to messages listview
        messagesListView.setAdapter(adapter);

        //Add text from EditText to our messages list on button click
        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String message = messageEditText.getText().toString();
                if(message.trim()!="")
                {
                    messagesList.add(0,message);
                    adapter.notifyDataSetChanged();
                    messageEditText.setText("");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
