package com.marvik.apps.smsblocker.database.queries;

import com.marvik.apps.smsblocker.database.schemas.Tables;

/**
 * Created by victor on 11/21/2015.
 */
public class Queries {
    public class BlockedSms {
        public static final String DEFAULT_SORT_ORDER = Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME + " DESC ";
    }

    public class SmsSenders {
        public static final String DEFAULT_SORT_ORDER = Tables.SMSSenders.COL_BLOCK_TIME + " DESC ";
    }
}
