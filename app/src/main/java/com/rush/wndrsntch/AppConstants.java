package com.rush.wndrsntch;

import android.util.SparseArray;

public class AppConstants
{
    public static final String PREFS_FILE = "wndrsntch";

    public enum UserState
    {
        INIT( 0 ),
        SETUP_COMPLETE( 1 );

        private int val;
        private final static SparseArray< UserState > map = new SparseArray<>( values().length );

        UserState( int val )
        {
            this.val = val;
        }

        static
        {
            for( UserState userState : values() )
            {
                map.put( userState.val, userState );
            }
        }

        public static UserState getUserStateByVal( int val )
        {
            return map.get( val );
        }

        public int getVal()
        {
            return val;
        }

    }
}
