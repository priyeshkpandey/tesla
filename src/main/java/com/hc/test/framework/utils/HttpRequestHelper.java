package com.hc.test.framework.utils;

import org.apache.commons.configuration2.Configuration;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;

import javax.ws.rs.core.MultivaluedMap;
import java.text.MessageFormat;
import java.util.HashMap;

/**
 * Created by sabyasachi on 27/04/16.
 */
public class HttpRequestHelper {
    DatabaseUtil databaseUtil;
    private Configuration prop;
    private ReadConfiguration readConfig;
    private HashMap<String, HashMap<String, String>> queryResults;
    public HashMap<String,String> akoshaAuthHeaders;

    public MultivaluedMap setAndGetAkoshaAuth(String mobileno) throws Exception {

        databaseUtil=new DatabaseUtil("helpchat");
        readConfig = new ReadConfiguration("config");
        prop = readConfig.getConfigFile();
        databaseUtil.createConnection();



        MessageFormat userIdQuery = new MessageFormat(
                prop.getString("userid.from.mobile"));
        Object[] userParams = { mobileno };
        queryResults = databaseUtil.getQueryResultAsMapForSingleTable(userIdQuery
                .format(userParams));
        Object[] userId = {queryResults.get("user").get("id1")};

        MessageFormat userAuthQuery=new MessageFormat(prop.getString("user.access.code"));

        queryResults=databaseUtil.getQueryResultAsMapForSingleTable(userAuthQuery.format(userId));

        String akosha_auth_key=queryResults.get("user_access_code").get("akosha_access_code1");

        MultivaluedMap params=new MultivaluedStringMap();
        params.add("X-AKOSHA-AUTH",akosha_auth_key);

        return params;


    }



}
