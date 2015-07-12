package br.com.doublelogic.nubanktest.rest;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import br.com.dotz.R;
import br.com.dotz.data.UserProfileController;
import br.com.dotz.service.util.LocalBroadcastMessages;
import br.com.dotz.service.util.ServiceMessages;

/**
 * Created by diego on 12/18/14.
 */
public abstract class AbstractRestAPI {

    private static final int HTTP_OK = 200;
    private static final String ACCESS_TOKEN_KEY = "Authorization-Token";

    protected final Context context;

    protected final String backendAPI;

    protected final Gson gson;

    protected AbstractRestAPI(Context context) {
        this.context = context;
        backendAPI = context.getString(R.string.backend_api);
        // back-end pattern 2015-04-28T15:58:54.8115593-03:00
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    }

    protected abstract String getTag();

    protected String requestThroughHttpGet(String url, boolean sendUserToken) {
        // Prepare a request object
        HttpGet httpGet = new HttpGet(url);

        if(sendUserToken) {
            // send user token
            httpGet.addHeader(ACCESS_TOKEN_KEY, UserProfileController.getInstance().getToken());
        }

        // Execute the request
        return requestHttp(httpGet);
    }

    protected String requestThroughHttpDelete(String url, boolean sendUserToken) {
        // Prepare a request object
        HttpDelete httpDelete = new HttpDelete(url);

        if(sendUserToken) {
            // send user token
            httpDelete.addHeader(ACCESS_TOKEN_KEY, UserProfileController.getInstance().getToken());
        }

        // Execute the request
        return requestHttp(httpDelete);
    }

    protected String requestThroughHttpPost(String url, boolean sendUserToken) {
        return requestThroughHttpPost(url, sendUserToken, null);
    }

    protected String requestThroughHttpPost(String url, boolean sendUserToken, List<NameValuePair> params) {
        // Prepare a request object
        HttpPost httpPost = new HttpPost(url);

        if(sendUserToken) {
            // send user token
            httpPost.addHeader(ACCESS_TOKEN_KEY, UserProfileController.getInstance().getToken());
        }

        // parameters
        if(params != null) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                Log.e(getTag(), e.getMessage(), e);
            }
        }

        // Execute the request
        return requestHttp(httpPost);
    }

    protected String requestThroughHttpPostJSON(String url, boolean sendUserToken, List<NameValuePair> params) {
        // Prepare a request object
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

        if(sendUserToken) {
            // send user token
            httpPost.addHeader(ACCESS_TOKEN_KEY, UserProfileController.getInstance().getToken());
        }

        // parameters
        if(params != null) {
            try {
                JSONObject jsonParams = new JSONObject();
                for(NameValuePair param : params) {
                    jsonParams.put(param.getName(), param.getValue());
                }

                StringEntity se = new StringEntity(jsonParams.toString());
                se.setContentEncoding("UTF-8");
                se.setContentType("application/json");

                httpPost.setEntity(se);
            } catch (UnsupportedEncodingException e) {
                Log.e(getTag(), e.getMessage(), e);
            } catch (JSONException e) {
                Log.e(getTag(), e.getMessage(), e);
            }
        }

        // Execute the request
        return requestHttp(httpPost);
    }

    private String requestHttp(HttpUriRequest request) {
        HttpParams httpParameters = new BasicHttpParams();
        // Set the timeout in milliseconds until a connection is established.
        // The default value is zero, that means the timeout is not used.
        int timeoutConnection = context.getResources().getInteger(R.integer.connection_timeout);
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = context.getResources().getInteger(R.integer.socket_timeout);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        // Execute the request
        HttpResponse response;
        InputStream in = null;
        try {
            response = httpclient.execute(request);

            if (HTTP_OK == response.getStatusLine().getStatusCode()) {

                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    // A Simple JSON Response Read
                    in = entity.getContent();
                    String result = convertStreamToString(in);

                    return result;
                }
            } else {
                Log.w(getTag(), "Http error: " + response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e) {
            Log.e(getTag(), e.getMessage(), e);
            ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
        } catch (IOException e) {
            Log.e(getTag(), e.getMessage(), e);
            ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
        } catch (Exception e) {
            Log.e(getTag(), e.getMessage(), e);
            ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(getTag(), e.getMessage(), e);
                }
            }
        }
        return null;
    }

    protected String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Log.e(getTag(), e.getMessage(), e);
        } finally {
            try {
                if(is != null)
                    is.close();
            } catch (IOException e) {
                Log.e(getTag(), e.getMessage(), e);
            }
        }
        return sb.toString();
    }

    protected JsonObject parseHttpRequestResult(String result) {
        JsonObject object = null;
        try {
            if(result != null) {
                object = gson.fromJson(result, JsonObject.class);

                if(object != null) {
                    //TODO verificar o tratamento de erro do backend
//                    JsonPrimitive code = object.getAsJsonPrimitive("code");
//                    JsonPrimitive message = object.getAsJsonPrimitive("message");
//
//                    if(ApiMessages.OK.getCode() == code.getAsInt()) {
//                        return object;
//                    } else {
//                        Log.w(getTag(), "API error[" + code.getAsInt() + "]: " + message.getAsString());
//                        ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
//                    }
                    return object;
                } else {
                    Log.w(getTag(), "JSON parser error[" + result + "]");
                    ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
                }
            }
        } catch (Exception e) {
            Log.e(getTag(), e.getMessage(), e);
            ServiceMessages.sendBroadcastMessage(LocalBroadcastMessages.CONNECTION_PROBLEM, context);
        }
        return object;
    }

}