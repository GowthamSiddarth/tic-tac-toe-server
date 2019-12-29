package com.gowtham.tictactoe.helper.responsewrapper;


import java.util.HashMap;
import java.util.Map;

public class ObjectResponse {

    public static Map<String, Object> jsonify(boolean success, Object message) {
        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("success", success);
        responseObject.put("message", message);

        return responseObject;
    }
}
