package com.realdolmen.rdtravel.persistence;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;

/**
 * Created by JSTAX29 on 7/10/2015.
 */
public class DataSetPersistenceTest extends PersistenceTest {
    @Before
    public void loadDataSet() throws Exception {
        super.initialize();
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(getClass().getResource("/data.xml"));

        IDatabaseConnection connection = new DatabaseConnection(newConnection());
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory()); // Set factorytype in dbconfig to remove warning
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //DatabaseOperation.TRUNCATE_TABLE.execute(connection, dataSet);
        connection.close();
    }
}
