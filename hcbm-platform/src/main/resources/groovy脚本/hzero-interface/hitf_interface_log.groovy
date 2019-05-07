package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface_log.groovy') {
    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface_log") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_interface_log_s', startValue:"1")
        }
        createTable(tableName: "hitf_interface_log", remarks: "") {
            column(name: "interface_log_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint(20)",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "invoke_key", type: "varchar(" + 255 * weight + ")",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "application_code", type: "varchar(" + 30 * weight + ")",  remarks: "")   
            column(name: "application_name", type: "varchar(" + 250 * weight + ")",  remarks: "")   
            column(name: "server_code", type: "varchar(" + 30 * weight + ")",  remarks: "")   
            column(name: "server_name", type: "varchar(" + 250 * weight + ")",  remarks: "")   
            column(name: "client_id", type: "varchar(" + 255 * weight + ")",  remarks: "")   
            column(name: "interface_type", type: "varchar(" + 30 * weight + ")",  remarks: "")   
            column(name: "interface_url", type: "varchar(" + 255 * weight + ")",  remarks: "")   
            column(name: "interface_request_method", type: "varchar(" + 30 * weight + ")",  remarks: "")   
            column(name: "interface_request_time", type: "datetime",  remarks: "")   
            column(name: "interface_response_time", type: "bigint(20)",  remarks: "")   
            column(name: "interface_response_code", type: "varchar(" + 10 * weight + ")",  remarks: "")   
            column(name: "interface_response_status", type: "varchar(" + 255 * weight + ")",  remarks: "")   
            column(name: "request_method", type: "varchar(" + 30 * weight + ")",  remarks: "")   
            column(name: "request_time", type: "datetime",  remarks: "")   
            column(name: "response_time", type: "bigint(20)",  remarks: "")   
            column(name: "response_code", type: "varchar(" + 10 * weight + ")",  remarks: "")   
            column(name: "response_status", type: "varchar(" + 255 * weight + ")",  remarks: "")   
            column(name: "ip", type: "varchar(" + 40 * weight + ")",  remarks: "")   
            column(name: "referer", type: "varchar(" + 250 * weight + ")",  remarks: "")   
            column(name: "user_agent", type: "varchar(" + 250 * weight + ")",  remarks: "")   
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}