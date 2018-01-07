package com.example.agarwal.coaching;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by AGARWAL on 7/3/2016.
 */
public class AddStudentEntry extends DrawerActivity {

    TextView date;
    Button submit,back;
    EditText name,contact,email,comments;
    Spinner course,batch;
    ImageView imageView;
    TextView title;
    int day,SELECT_FILE=1100,REQUEST_CAMERA=2200;
    int month;
    int yr;
    String strdate,userChoosenTask;
    Calendar calender;
    Boolean imageset=false;
    ArrayList<String> courselist,batchlist;
    ArrayAdapter<String> courseadpt,batchadpt;
    boolean update=false;
    int sid;
    String path;
    int scourse_id,sbatch_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addstudententry);

        date=(TextView)findViewById(R.id.viewadstuddate);
        title=(TextView)findViewById(R.id.viewaddstudeditadd);

        submit=(Button)findViewById(R.id.btnadstudsubmit);
        back=(Button)findViewById(R.id.btnadstudback);

        name=(EditText)findViewById(R.id.edtadstudname);
        contact=(EditText)findViewById(R.id.edtadstudmobile);
        email=(EditText)findViewById(R.id.edtadstudemail);
        comments=(EditText)findViewById(R.id.edtadstudcomment);

        course=(Spinner)findViewById(R.id.spnadstudcourse);
        batch=(Spinner)findViewById(R.id.spnadstudbatch);

        imageView=(ImageView)findViewById(R.id.imgviewaddstud);

        calender =calender.getInstance();
        day=calender.get(calender.DAY_OF_MONTH);
        month=calender.get(calender.MONTH);
        yr=calender.get(calender.YEAR);

        strdate=day+"/"+(month+1)+"/"+yr;
        date.setText(strdate);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    selectImage();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(AddStudentEntry.this,EntryWindow.class);
                startActivity(intent);*/
                finish();
            }
        });

        courselist=new ArrayList<String>();
        courselist.add("Select Course");
        courseadpt=new ArrayAdapter<String>(AddStudentEntry.this,android.R.layout.simple_list_item_1,courselist);
        course.setAdapter(courseadpt);
        Helper.fillCourses(AddStudentEntry.this,courselist,courseadpt);

        batchlist=new ArrayList<String>();
        batchlist.add("Select Batch");
        batchadpt=new ArrayAdapter<String>(AddStudentEntry.this,android.R.layout.simple_list_item_1,batchlist);
        batch.setAdapter(batchadpt);

        course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Batch");
                    batchadpt=new ArrayAdapter<String>(AddStudentEntry.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);

                    String itemselected = (String) course.getItemAtPosition(position);
                    int cur_id = Integer.parseInt(itemselected.substring(0, 4));
                    Helper.fillBatch(AddStudentEntry.this,batchlist,batchadpt,cur_id);
                }
                else
                {
                    batchlist=new ArrayList<String>();
                    batchlist.add("Select Batch");
                    batchadpt=new ArrayAdapter<String>(AddStudentEntry.this,android.R.layout.simple_list_item_1,batchlist);
                    batch.setAdapter(batchadpt);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString()==null || name.getText().toString().trim().equals(""))
                {
                    Toast.makeText(AddStudentEntry.this,"Enter a Name",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(contact.getText().toString()==null || contact.getText().toString().trim().equals("") || contact.getText().toString().trim().length()!=10)
                {
                    Toast.makeText(AddStudentEntry.this,"Enter a Valid Contact",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(email.getText().toString()==null || email.getText().toString().trim().equals("") || (!email.getText().toString().contains("@")))
                {
                    Toast.makeText(AddStudentEntry.this,"Enter a Valid email",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(batch.getSelectedItemPosition()==0)
                {
                    Toast.makeText(AddStudentEntry.this,"Please select batch and course",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!imageset)
                {
                    Toast.makeText(AddStudentEntry.this,"Please choose a Student Photo",Toast.LENGTH_LONG).show();
                    return;
                }

                Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                //String itemselected = (String) course.getSelectedItem();
                //int cur_id = Integer.parseInt(itemselected.substring(0, 4));
                String itemselected = (String) batch.getSelectedItem();
                int bat_id = Integer.parseInt(itemselected.substring(0, 5));

                if(update)
                {
                    Helper.updateStudentEntry(AddStudentEntry.this,sid, name.getText().toString(), contact.getText().toString(), email.getText().toString(), bat_id, comments.getText().toString(), encodedImage,path);
                }
                else {
                    Helper.addStudentEntry(AddStudentEntry.this, name.getText().toString(), contact.getText().toString(), email.getText().toString(), bat_id, comments.getText().toString(), encodedImage);
                }
               /* AlertDialog.Builder builder=new AlertDialog.Builder(AddStudentEntry.this);
                builder.setTitle("Alert Dialog");
                builder.setMessage("A Mail Will be Send To The Mail Address");
                String msg=createMail();
                //Mail m=new Mail(email.toString(),msg);
                //m.send(email.toString(),msg);
                //builder.setIcon(R.drawable.dialog);
                Mail.msg=msg;

                Mail.address=email.getText().toString();

                Intent intent=new Intent(AddStudentEntry.this,Mail.class);
                startActivity(intent);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddStudentEntry.this,"Student Successfully Added",Toast.LENGTH_LONG).show();
                    }
                });

                builder.show();*/
            }
        });

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null)
        {
            title.setText("Edit Student");
            imageset=true;
            submit.setText("Update");
            update=true;
            scourse_id=bundle.getInt("course_id");
            sbatch_id=bundle.getInt("batch_id");
            String sname=bundle.getString("name");
            String semail=bundle.getString("email");
            String snumber=bundle.getString("number");
            String scomment=bundle.getString("comments");
            String image=bundle.getString("image");
            int id=bundle.getInt("id");
            sid=id;
            path=bundle.getString("path");

           /* for(int i=0;i<course.getCount();i++)
            {
                String itemselected = (String) course.getItemAtPosition(i);
                int cur_id = Integer.parseInt(itemselected.substring(0, 4));

                if(cur_id==course_id)
                {
                    course.setSelection(i);
                }
            }

            for(int i=0;i<course.getCount();i++)
            {
                String itemselected = (String) batch.getItemAtPosition(i);
                int bat_id = Integer.parseInt(itemselected.substring(0, 5));

                if(bat_id==batch_id)
                {
                    course.setSelection(i);
               }
            }*/

            byte[] bytearray=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(bytearray,0,bytearray.length);
            imageView.setImageBitmap(bitmap);

            name.setText(sname);
            email.setText(semail);
            contact.setText(snumber);
            comments.setText(scomment);

        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentEntry.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private String createMail()
    {
        String msg="congratulations you have Successfully registered with the coaching management system." +
                "Please note the below imformation for any future references.\n\n"+"Your name:"
                +name.getText().toString()+"\n Your Mobile Number:"+contact.getText().toString()+"\n " +
                "Your Email:"+email.getText().toString()+"\n\nThank You...\nHave a Nice Day";

        return msg;
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            imageset=true;
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(bm);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        imageView.setImageBitmap(thumbnail);
        imageset=true;
    }
    public void send(String address, String msg) {

        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,address);
        //intent.putExtra(Intent.EXTRA_CC,cc);
        //intent.putExtra(Intent.EXTRA_BCC,bcc);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coaching Management Registration");
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Email"));
    }
}
