package Model.Users;

public enum Permission {
    //Could've done a better job naming the permissions but oh well :),
    //Too late to change it now because serialization and all that
    //Explanation for ambiguous permissions below

    CREATE_BILL,
    //view bills created by the user themselves
    VIEW_BILL,

    //view and edit stock per sector
    VIEW_ITEM,
    EDIT_ITEM,

    //view users in sectors
    VIEW_SECTOR,
    EDIT_SECTOR,



    //Old name, now used for Statistics View
    PERFORMANCE_SECTOR,

    //Old name, now used for more details in Statistic View
    PERFORMANCE_ALL;
}
