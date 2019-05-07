package script.db

databaseChangeLog(logicalFilePath: 'script/db/himp_import.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2019-02-19-himp_import") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'himp_import_s', startValue:"1")
        }
        createTable(tableName: "himp_import", remarks: "") {
            column(name: "import_id", type: "bigint(20)", autoIncrement: true ,   remarks: "主键 表ID")  {constraints(primaryKey: true)}
            column(name: "batch", type: "varchar(" + 60 * weight + ")",  remarks: "批次")  {constraints(nullable:"false")}
            column(name: "status", type: "varchar(" + 30 * weight + ")",  remarks: "当前状态")  {constraints(nullable:"false")}
            column(name: "data_count", type: "int(20)",  remarks: "数据数量")  {constraints(nullable:"false")}
            column(name: "object_version_number", type: "bigint(20)",  remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}
            column(name: "creation_date", type: "datetime",  remarks: "")  {constraints(nullable:"false")}
            column(name: "created_by", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}
            column(name: "last_updated_by", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}
            column(name: "last_update_date", type: "datetime",  remarks: "")  {constraints(nullable:"false")}

        }

        addUniqueConstraint(columnNames:"batch",tableName:"himp_import",constraintName: "himp_import_u1")
    }
}