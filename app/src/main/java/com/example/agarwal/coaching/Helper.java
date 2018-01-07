package com.example.agarwal.coaching;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AGARWAL on 7/10/2016.
 */
public class Helper {

    public static void adduser(final Context context, final String name, final String email, final String password) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_registration";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Logging in ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        /*Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();*/
                        //Toast.makeText(context,"User successfully added",Toast.LENGTH_LONG);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        String address = email;
                        Intent intent = new Intent(context, Mail.class);
                        Mail.address = address;
                        Mail.msg = "congratulations your account has been successfully created.Please note the below imformation for" +
                                "any future references.\n\n" + "Your Username:" + name + "\n Your Password:" + password + "\n " +
                                "Your Email:" + email + "\n\nThank You...\nHave a Nice Day";
                        context.startActivity(intent);
                        //builder.setIcon(R.drawable.dialog);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "User Successfully Added", Toast.LENGTH_LONG).show();
                            }
                        });

                        builder.show();
                        //flag[0] =true;
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    private static void showDialog(ProgressDialog progressDialog) {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private static void hideDialog(ProgressDialog progressDialog) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static void validateUser(final Context context, final String email, final String password) {
        final Session session = new Session(context);
        String tag_string_req = "req_login";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Logging in ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    Log.e("res", response);
                    JSONObject jObj = new JSONObject(response);
                    Boolean userId = jObj.getBoolean("error");

                    if (!userId) {
                        //session.setLogin(true);
                        session.setLogin(true);
                        session.setUser(jObj.getString("user_name"), email);
                        Intent intent = new Intent(context, MainWindow.class);
                        context.startActivity(intent);
                        Log.e("responseuser", jObj.getString("user_name"));
                        ((Activity) context).finish();
                        //Map<String,String> params=new HashMap<String, String>();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Post params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void addCouirse(final Context context, final String name, final int months, final int days, final int fee) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_addcourse";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Adding ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("cid");
                        Toast.makeText(context, "Course Added with Course_id=" + id, Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addcourse");
                params.put("name", name);
                params.put("months", months + "");
                params.put("days", days + "");
                params.put("fee", fee + "");

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void fillCourses(final Context context, final ArrayList<String> coursename, final ArrayAdapter adapter) {
        String tag_string_req = "req_fillcourse";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("FIlling ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=fillcourse", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    Log.d("1234", "res=" + response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONArray jarray = new JSONArray(response);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String id = object.getString("course_id");
                        String name = object.getString("course_name");

                        coursename.add(id + "  " + name);

                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "fillcourse");
                return params;*/

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void addBatch(final Context context, final int cource_id, final java.sql.Date date, final java.sql.Time time) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_addbatch";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Adding ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("bid");
                        Toast.makeText(context, "Course Added with Batch_id=" + id, Toast.LENGTH_LONG).show();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addbatch");
                Log.e("id", cource_id + "");
                params.put("cource_id", cource_id + "");
                Log.e("date", date + "");
                params.put("date", date + "");
                Log.e("time", time + "");
                params.put("time", time + "");

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void addStudentEntry(final Context context, final String name, final String mobile, final String email, final int batch_id, final String comment, final String photo) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_addStudentENtry";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Adding Student...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("sid");
                        Toast.makeText(context, "Student Added with Student_id=" + id, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        Mail.msg = "congratulations you have Successfully registered with the coaching management system." +
                                "Please note the below imformation for any future references.\n\n" + "\n Your Student_id:" + id + "\nYour name:"
                                + name + "\n Your Mobile Number:" + mobile + "\n " +
                                "Your Email:" + email + "\n\nThank You...\nHave a Nice Day";

                        Mail.address = email;

                        Intent intent = new Intent(context, Mail.class);
                        context.startActivity(intent);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addstudententry");
                //Log.e("id",cource_id+"");
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("batch_id", batch_id + "");
                params.put("comments", comment);
                params.put("photo", photo);
                Log.e("p", photo);
                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void addStudentQuery(final Context context, final String name, final String mobile, final String email, final int course_id, final String comment, final String photo) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_addStudentENtry";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Adding Query...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("qid");
                        Toast.makeText(context, "Query Added with Query_id=" + id, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        Mail.msg = "Thank You For Showing Interest in Our with the coaching management system." +
                                "Please note the below imformation for any future references.\n\n" + "\n Query Id:" + id + "\nYour name:"
                                + name + "\n Your Mobile Number:" + mobile + "\n " +
                                "Your Email:" + email + "\n\nThank You...\nHave a Nice Day";

                        Mail.address = email;

                        Intent intent = new Intent(context, Mail.class);
                        context.startActivity(intent);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addstudentquery");
                //Log.e("id",cource_id+"");
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("course_id", course_id + "");
                params.put("comments", comment);
                params.put("photo", photo);
                // Log.e("p",photo);
                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void fillBatch(final Context context, final ArrayList<String> batchlist, final ArrayAdapter batchadpt, final int course_id) {
        String tag_string_req = "req_fillbatch";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("FIlling batches ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=fillbatch&course_id=" + course_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    Log.d("1234", "res=" + response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONArray jarray = new JSONArray(response);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String id = object.getString("batch_id");
                        String date = object.getString("start_date");
                        String timing = object.getString("timing");

                        batchlist.add(id + "   " + date + "   " + timing);

                    }
                    batchadpt.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "fillcourse");
                return params;*/

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void getStudentRecord(final Context context, final boolean flag, final ArrayList<String> name, final ArrayList<String> image, final ArrayList<String> email, final ArrayList<String> phone, final ArrayList<String> id, final ArrayList<String> comments, int batch_id, int course_id, final ArrayAdapter<String> adapter) {
        String tag_string_req = "req_fillbatch";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Getting Records ...");
        showDialog(progressDialog);

        String url;

        if (flag) {
            url = URLHelper.URL + "?tag=getstudentrecord&batch_id=" + batch_id;
        } else {
            url = URLHelper.URL + "?tag=getstudentquery&course_id=" + course_id;
        }

        Log.e("c", course_id + "");

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    Log.e("1234", response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONArray jarray = new JSONArray(response);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        name.add(object.getString("name"));
                        email.add(object.getString("email"));
                        phone.add(object.getString("mobile"));
                        image.add(object.getString("photo"));
                        id.add(object.getString("id"));
                        comments.add(object.getString("comments"));
                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void fillReceipt(final Context context, final TextView number) {
        String tag_string_req = "req_fillReceipt";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Getting Receipt Number...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=receipt_no", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    // Log.d("1234", "res="+response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("receipt_no");
                        number.setText(id + "");
                    } else {
                        number.setText("1");
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "fillcourse");
                return params;*/

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void fillStudent(final Context context, final ArrayList<String> studentlist, final ArrayAdapter studentadpt, final int batch_id) {
        String tag_string_req = "req_fillstudent";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("FIlling students ...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=fillstudent&batch_id=" + batch_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    //Log.d("1234", "res="+response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONArray jarray = new JSONArray(response);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String id = object.getString("student_id");
                        String name = object.getString("name");

                        studentlist.add(id + "   " + name);
                    }
                    studentadpt.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                /*Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "fillcourse");
                return params;*/

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void fillStudInfo(final Context context, final int student_id, final TextView contact, final TextView email) {
        String tag_string_req = "req_fillstudentInfo";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("FIlling students Info...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=fillstudentinfo&student_id=" + student_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    //Log.d("1234", "res="+response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONArray jarray = new JSONArray(response);

                    for (int i = 0; i < jarray.length(); i++) {
                        JSONObject object = jarray.getJSONObject(i);

                        String mobile = object.getString("mobile");
                        String emails = object.getString("email");

                        contact.setText(mobile);
                        email.setText(emails);

                        Log.e("Info", contact + "  " + email);
                    }

                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void fetchFeeRecord(final Context context, final int student_id, final int batch_id, final int course_id, final TextView viewfee, final TextView viewdue, final TextView deposited) {
        String tag_string_req = "req_fetchfeerecord";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Fetching Fee Record...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=fetchfeerecord&student_id=" + student_id + "&batch_id=" + batch_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        String sfee = jObj.getString("fee");
                        int fee = Integer.parseInt(sfee);
                        String sdue = jObj.getString("due");
                        int due = Integer.parseInt(sdue);

                        viewdue.setText(due + "");
                        viewfee.setText(fee + "");
                        deposited.setText(fee - due + "");
                    } else {
                        Log.e("fee", "Surpasses this");
                        getFee(context, course_id, viewdue, viewfee, deposited);
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void getFee(final Context context, final int course_id, final TextView viewfee, final TextView viewdue, final TextView deposited) {
        String tag_string_req = "req_getfee";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Getting Fee...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL + "?tag=getfee&course_id=" + course_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        String sfee = jObj.getString("fee");
                        int fee = Integer.parseInt(sfee);
                        //int due=jObj.getInt("due");

                        Log.e("fee", fee + "");
                        viewdue.setText(fee + "");
                        viewfee.setText(fee + "");
                        deposited.setText("0");
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void addFeeRecord(final Context context, final int student_id, final int batch_id, final int paid, final int fee, final int due, final String email) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_addFeeRecord";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Adding Fee Record...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    Log.e("Response", response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int number = jObj.getInt("receipt_no");
                        Toast.makeText(context, "Your Fee Receipt Number=" + number, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        Mail.msg = "you have successfully paid at coaching management system." +
                                "Your Updated Fee Record with the paid fee_receipt is below.\n\n" + "\n Receipt_no:" + number + "\nYour Fee:"
                                + fee + "\n You paid:" + paid + "\n " +
                                "Due:" + due + "\n\nThank You...\nHave a Nice Day";

                        Mail.address = email;

                        Intent intent = new Intent(context, Mail.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "addfeerecord");
                //Log.e("id",cource_id+"");
                params.put("student_id", student_id + "");
                params.put("batch_id", batch_id + "");
                params.put("fee", fee + "");
                params.put("due", due + "");

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }


    public static void updateStudentEntry(final Context context, final int sid, final String name, final String mobile, final String email, final int batch_id, final String comment, final String photo, final String path) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_updateStudentENtry";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Updating Student...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    Log.e("response",response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("sid");
                        Toast.makeText(context, "Student Entry Updated with Student_id=" + id, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        Mail.msg = "congratulations you have Successfully Updated with the coaching management system." +
                                "Please note the below imformation for any future references.\n\n" + "\n Your Student_id:" + id + "\nYour name:"
                                + name + "\n Your Mobile Number:" + mobile + "\n " +
                                "Your Email:" + email + "\n\nThank You...\nHave a Nice Day";

                        Mail.address = email;

                        Intent intent = new Intent(context, Mail.class);
                        context.startActivity(intent);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "updatestudententry");
                //Log.e("id",cource_id+"");
                params.put("sid", "" + sid);
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("batch_id", batch_id + "");
                params.put("comments", comment);
                params.put("photo", photo);
                params.put("path",path);

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void updateStudentQuery(final Context context, final int qid, final String name, final String mobile, final String email, final int course_id, final String comment, final String photo, final String path) {
        //final boolean[] flag = {false};
        String tag_string_req = "req_updateStudentQuery";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Updating Query...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {
                    Log.e("response",response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        int id = jObj.getInt("sid");
                        Toast.makeText(context, "Query Entry Updated with Query_id=" + id, Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert Dialog");
                        builder.setMessage("A Mail Will be Send To The Mail Address");
                        Mail.msg = "congratulations you have Successfully Updated with the coaching management system." +
                                "Please note the below imformation for any future references.\n\n" + "\n Your Query_id:" + id + "\nYour name:"
                                + name + "\n Your Mobile Number:" + mobile + "\n " +
                                "Your Email:" + email + "\n\nThank You...\nHave a Nice Day";

                        Mail.address = email;

                        Intent intent = new Intent(context, Mail.class);
                        context.startActivity(intent);
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "updatestudentquery");
                //Log.e("id",cource_id+"");
                params.put("qid", "" + qid);
                params.put("name", name);
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("course_id", course_id + "");
                params.put("comments", comment);
                params.put("photo", photo);
                params.put("path",path);

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        //return flag[0];
    }

    public static void deleteStudent(final Context context, final int id) {
        String tag_string_req = "req_delStudent";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Deleting Student Entry...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    // Log.d("1234", "res="+response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                       Toast.makeText(context,"Student with student_id="+id+" has been Successfully Deleted",Toast.LENGTH_LONG).show();
                        Intent intent = ((Activity)context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context,"Problem in Deleting Record!!!Contact Administrator",Toast.LENGTH_LONG).show();
                        Intent intent = ((Activity)context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "deletestudententry");

                params.put("sid", "" + id);

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public static void deleteQuery(final Context context, final int id) {
        String tag_string_req = "req_delStudent";
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("Deleting Student Entry...");
        showDialog(progressDialog);

        StringRequest strReq = new StringRequest(Request.Method.POST, URLHelper.URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog(progressDialog);

                try {

                    // Log.d("1234", "res="+response);
                    //JSONObject jsono = new JSONObject(response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(context,"Student Query with query_id="+id+" has been Successfully Deleted",Toast.LENGTH_LONG).show();
                        Intent intent = ((Activity)context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context,"Problem in Deleting Record!!!Contact Administrator",Toast.LENGTH_LONG).show();
                        Intent intent = ((Activity)context).getIntent();
                        ((Activity) context).finish();
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    Log.e("Exception", e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog(progressDialog);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "deletestudentquery");

                params.put("qid", "" + id);

                return params;
            }
        };
        // Adding request to  queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}