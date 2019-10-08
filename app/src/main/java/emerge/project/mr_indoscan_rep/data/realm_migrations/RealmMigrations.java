package emerge.project.mr_indoscan_rep.data.realm_migrations;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by Himanshu Emerge on 3/2/2018.
 */

public class RealmMigrations implements RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema realmSchema = realm.getSchema();
        for (long version = oldVersion; version < newVersion; version++) {
            if (version == 0) { // to 1

            }
        }
    }



}
