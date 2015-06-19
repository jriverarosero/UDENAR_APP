package com.galeras.udenar.udenarapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jorge on 15/06/2015.
 */
public class Conection {




    /* Cadena de conexion para el serividor */

    public static String SERVER_URL = "http://192.168.6.72:90/academica/";
    //public static String SERVER_URL = "http://192.168.0.10:90/academica/";
    //public static String SERVER_URL = "http://192.168.43.15:90/academica/";

    /* Cadena de conexion para el logueo */

    public static String LOGIN_URL = SERVER_URL + "login/";

    /* Cadena de conexion para el RSS de la udenar */

    public static String RSS_URL = "http://ccomunicaciones.udenar.edu.co/?feed=rss2";

    /* Cadena de conexion para las materias */

    public static String SUBJECT_URL = SERVER_URL + "getSubject/";

    /* Cadena de Conexion para obtener desprendibles */

    public static String TICKETS_URL = SERVER_URL + "getTickets/";

    /* Cadena de Conexion para obtener notas */

    public static String GRADES_URL = SERVER_URL + "getgrades/";

    /* Cadena de Conexion para obtener notas */

    public static String LOGOUT_URL = SERVER_URL + "logout/";

    /* Cadena de Conexion para obtener notas */

    public static String CODE_URL = SERVER_URL + "getcode/";

    /* Cadena de Conexion para obtener fotografia */

    public static String IMG_URL = "http://akademica.udenar.edu.co/foto/estudiante/";



}
