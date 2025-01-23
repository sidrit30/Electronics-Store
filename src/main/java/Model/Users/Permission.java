package Model.Users;

public enum Permission {
    //Could've done a better job naming the permissions but oh well :),
    //Too late to change it now because serialization and all that
    //Explanation for ambiguous permissions below

    //does nothing for manager and admin due to time constraints
    CREATE_BILL,
    //view bills created in the sector/s, or by the user themselves
    VIEW_BILL,

    //view and edit stock per sector
    VIEW_ITEM,
    EDIT_ITEM,

    //view users in sectors
    VIEW_SECTOR,
    EDIT_SECTOR,



    //Old name, now used for Statistics View
    PERFORMANCE_SECTOR,

    //Old name, not used at all
    PERFORMANCE_ALL;
}
