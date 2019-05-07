package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_application.groovy') {
    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_application") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_application_s', startValue:"1")
        }
        createTable(tableName: "hitf_application", remarks: "") {
            column(name: "application_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")   
            column(name: "application_code", type: "varchar(" + 30 * weight + ")",  remarks: "应用代码")  {constraints(nullable:"false")}  
            column(name: "application_name", type: "varchar(" + 250 * weight + ")",  remarks: "应用名称")  {constraints(nullable:"false")}  
            column(name: "oauth_client_id", type: "bigint(20)",  remarks: "客户端ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"application_code,tenant_id",tableName:"hitf_application",constraintName: "hitf_application_u1")
    }
}