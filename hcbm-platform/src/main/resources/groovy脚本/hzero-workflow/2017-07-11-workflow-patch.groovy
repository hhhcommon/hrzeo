package script.db

databaseChangeLog(logicalFilePath:"patch.groovy"){

    changeSet(author: "qixiangyu", id: "20170808-act_hi_identitylink") {
        addColumn(tableName: "act_hi_identitylink") {
            column(name: "read_flag_", type: "varchar(1)", remarks: "读取状态", defaultValue: "N")
        }
    }
}