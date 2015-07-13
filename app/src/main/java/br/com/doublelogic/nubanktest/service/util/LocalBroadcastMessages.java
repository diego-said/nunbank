package br.com.doublelogic.nubanktest.service.util;

/**
 * Created by diegoalvessaidsimao on 11/07/15.
 */
public class LocalBroadcastMessages {

    public class COMMON {
        public static final String INTERN_PROBLEM = "internProblem";
        public static final String INTERNET_PROBLEM = "internetProblem";
    }

    public class REST {
        public static final String CONNECTION_PROBLEM = "connectionProblem";
    }

    public class SERVICE {
        public static final String PRE_EXECUTE_LIST_BILLS = "preExecuteListBills";
        public static final String POST_EXECUTE_LIST_BILLS = "postExecuteListBills";
    }

}
