package com.bpk.app.emrapp;

/**
 *
 * @author SURACHAI.TO
 */
public class DataControl
{

    private static String currentUser;

    /**
     * @return the currentUser
     */
    public static String getCurrentUser()
    {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(String aCurrentUser)
    {
        currentUser = aCurrentUser;
    }

}
