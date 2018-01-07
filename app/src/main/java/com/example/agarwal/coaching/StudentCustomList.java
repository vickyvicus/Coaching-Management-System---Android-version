package com.example.agarwal.coaching;

/**
 * Created by AGARWAL on 7/5/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class StudentCustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> name;
    private final ArrayList<String> image;
    private final ArrayList<String> number;
    private final ArrayList<String> emails;
    private final ArrayList<String> id;
    private final ArrayList<String> comments;
    private final int course_id;
    private final int batch_id;



    public StudentCustomList(Activity context,ArrayList<String> name, ArrayList<String> image,ArrayList<String> number,ArrayList<String> emails,ArrayList<String> id,ArrayList<String> comments,int course_id,int batch_id) {
        super(context, R.layout.student_list_single, name);
        this.context = context;
        this.name = name;
        this.image =image;
        this.emails=emails;
        this.number=number;
        this.id=id;
        this.comments=comments;
        this.course_id=course_id;
        this.batch_id=batch_id;

    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.student_list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtstudlistname);
        final ImageView imgviewmain = (ImageView) rowView.findViewById(R.id.imgstudlistmain);


        txtTitle.setText(name.get(position));
        imgviewmain.setImageResource(R.mipmap.ic_launcher);

        DownloadImageTask diT = new DownloadImageTask(imgviewmain);
        String url=URLHelper.URL.substring(0,(URLHelper.URL.length()-9));
        diT.execute(url+image.get(position));

        ImageView imgviewcall=(ImageView) rowView.findViewById(R.id.imgstudlistcall);
        //imgviewcall.setTag(number[position]);

        ImageView imgviewemail=(ImageView) rowView.findViewById(R.id.imgstudlistemail);
        //imgviewemail.setTag(emails[position]);

        ImageView imgviewmsg=(ImageView) rowView.findViewById(R.id.imgstudlistmsg);
        //imgviewmsg.setTag(number[position]);

        ImageView imgviewmenu=(ImageView)rowView.findViewById(R.id.imgstudlistmenu);
        //imgviewmenu.setTag(id);

        imgviewemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mail.address= emails.get(position);
                Mail.msg="";
                Intent intent=new Intent(parent.getContext(),Mail.class);
                parent.getContext().startActivity(intent);
            }
        });

        imgviewcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number.get(position)));

                if (ActivityCompat.checkSelfPermission(parent.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                parent.getContext().startActivity(intent);
            }
        });

        imgviewmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", number.get(position));
                smsIntent.putExtra("sms_body","Body of Message");
                parent.getContext().startActivity(smsIntent);
            }
        });

        imgviewmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(parent.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.student_list_view_menu,
                        popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.studlistviewedit:

                                //Or Some other code you want to put here.. This is just an example.
                                //Toast.makeText(parent.getContext(), " Install Clicked at position " + " : " + position, Toast.LENGTH_LONG).show();

                                if(id.get(position).trim().length()==7)
                                {

                                    Bundle bundle=new Bundle();
                                    bundle.putInt("id",Integer.parseInt(id.get(position)));
                                    bundle.putInt("course_id",course_id);
                                    bundle.putInt("batch_id",batch_id);
                                    bundle.putString("name",name.get(position));
                                    bundle.putString("comments",comments.get(position));
                                    bundle.putString("email",emails.get(position));
                                    bundle.putString("number",number.get(position));
                                    bundle.putString("path",image.get(position));

                                    Bitmap bitmap=((BitmapDrawable)imgviewmain.getDrawable()).getBitmap();
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                                    bundle.putString("image",encodedImage);

                                    Intent intent=new Intent(parent.getContext(),AddStudentEntry.class);
                                    intent.putExtras(bundle);

                                    parent.getContext().startActivity(intent);
                                }

                                else
                                {
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("id",Integer.parseInt(id.get(position)));
                                    bundle.putInt("course_id",course_id);
                                    bundle.putString("name",name.get(position));
                                    bundle.putString("comments",comments.get(position));
                                    bundle.putString("email",emails.get(position));
                                    bundle.putString("number",number.get(position));
                                    bundle.putString("path",image.get(position));

                                    Bitmap bitmap=((BitmapDrawable)imgviewmain.getDrawable()).getBitmap();
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                                    String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                                    bundle.putString("image",encodedImage);

                                    Intent intent=new Intent(parent.getContext(),AddStudentQuery.class);
                                    intent.putExtras(bundle);

                                    parent.getContext().startActivity(intent);
                                }
                                break;
                            case R.id.studlistviewdelete:

                                AlertDialog.Builder builder=new AlertDialog.Builder(parent.getContext());  //alertdailog is outer class n builder is inner class inbuilt
                                builder.setTitle("Alert Dialog");
                                builder.setMessage("Are you sure you want to delete this record");
                                //builder.setIcon(R.drawable.dialog);

                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(parent.getContext(),"Student has been Successfully deleted",Toast.LENGTH_LONG).show();

                                        if(id.get(position).trim().length()==7)
                                        {
                                            Helper.deleteStudent(parent.getContext(),Integer.parseInt(id.get(position)));
                                        }
                                        else {
                                            Helper.deleteQuery(parent.getContext(),Integer.parseInt(id.get(position)));
                                        }

                                    }
                                });
                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(parent.getContext(),"you pressed cancel button",Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(parent.getContext(),"you pressed none button",Toast.LENGTH_LONG).show();
                                    }
                                });
                                builder.show();          //for showing

                                break;

                            default:
                                break;
                        }

                        return true;
                    }
                });
            }
        });

        return rowView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage)
        {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}
