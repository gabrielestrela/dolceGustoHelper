package br.com.nespressohelper.daitan.nespressohelper;

/**
 * Created by estrela on 12/27/17.
 */

public interface BackEndCommHandler {

    enum Method{GET, POST}

    void handleResponse(String response, Method m);
    void handleResponse(int code, Method m);


}
