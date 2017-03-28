package com.amazonaws.serverless.sample.jersey.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    /**
     * Mapper is.
     */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert object to string.
     *
     * @param obj
     *            a java object
     * @return string
     */
    public static String toJson(final Object obj) {
	try {
	    return mapper.writeValueAsString(obj);
	} catch (JsonProcessingException e) {
	    return null;
	}
    }

    /**
     * Convert a json string to an object.
     *
     * @param inp
     *            string to convert
     * @param claz
     *            class to return
     * @return object of claz
     */
    @SuppressWarnings("unchecked")
    public static Object toObject(final String inp, final @SuppressWarnings("rawtypes") Class claz) {
	try {
	    return mapper.readValue(inp, claz);
	} catch (Exception e) {
	    return null;
	}
    }

    /**
     * Dont care about exception its just for logging.
     *
     * @param obj
     *            to convert
     * @return string in json
     */
    public static String logJson(final Object obj) {
	try {
	    return mapper.writeValueAsString(obj);
	} catch (JsonProcessingException e) {
	    return "";
	}
    }
}

