package com.jazeit.jazeitapp.globalclass;



public class _glbMessages {

    /*
     *
     *
     */
    public final String ENUM_TO_HTTP = "TO_HTTP";
    public final String ENUM_FROM_HTTP = "FROM_HTTP";

    /*
     * REQUESTER returns status
     * Used in : Registration Process
     */
    public final String STATUS_SUCCESS  = "0";
    public final String  STATUS_FAILED = "1";
    public final String  STATUS_INVALID = "-1";

    public final String  FLAG_TRUE = "0";
    public final String  FLAG_FALSE = "1";
    public final String  FLAG_INVALID = "-1";

    /*
     * REQUESTER : When mobile checks reception / appointments
     *
     */
    public final String  DATA_NOT_AVAILABLE = "0";
    public final String  DATA_AVAILABLE = "1";
    public final String  DATA_NEW_AVAILABLE = "2";

    /*
     * MESSAGE to REQUESTER
     *
     */
    public final String  MSG_REGISTER_SMART_DEVICE = "R001";
    public final String  MSG_IS_VALID_AUTH_CODE = "R002";

    public final String  DC_MSG_GET_RECEPTION = "D001";
    public final String  DC_MSG_GET_APPOINTMENTS = "D002";
    public final String  DC_MSG_GET_REPORT_LIST = "D003";

    public final String  DC_STATUS_NO_CHANGE = "D901";

    /*
     *  ERROR Code from REQUESTER
     *
     */
    public final String  ERR_INVALID_REQUEST = "E001";
    public final String  ERR_RUNTIME_EXCEPTION = "E002";
    public final String  ERR_LIBRARY_DAL = "E003";
    public final String  ERR_NO_RECORDS = "E004";
    public final String  ERR_FILE_NOT_FOUND = "E005";

    public final String MSG_NO_CONNECTION = "Unable to connect to Internet. Please Check the Connection and try later.";
    public final String MSG_REQUEST_CALNCELLED = "Unable to connect to Internet. Please Check the Connection and try later.";

    public final String HANDLER_START_ACTIVITY = "START_ACTIVITY";
    public final String HANDLER_POPULATE_TEXTVIEW_HTML = "POPULATE_TEXTVIEW_HTML";
    public final String HANDLER_POPULATE_TEXTVIEW = "POPULATE_TEXTVIEW";
    public final String HANDLER_WRITE_TOAST = "WRITE_TOAST";
    public final String HANDLER_WRITE_TO_FILE = "WRITE_TO_FILE";
    public final String HANDLER_SAVE_PREF = "SAVE_PREF";
}
