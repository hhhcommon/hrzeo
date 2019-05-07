package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_process_listener.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_process_listener") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_process_listener_s', startValue:"1")
        }
        createTable(tableName: "hwfl_process_listener", remarks: "流程监听器") {
        column(name: "listener_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "code", type: "varchar(50)",  remarks: "监听器代码")  {constraints(nullable:"false")}  
        column(name: "name", type: "varchar(255)",  remarks: "监听器名称")  {constraints(nullable:"false")}  
        column(name: "listener_type", type: "varchar(20)",  remarks: "监听器类型")  {constraints(nullable:"false")}  
        column(name: "event", type: "varchar(20)",  remarks: "监听器事件")  {constraints(nullable:"false")}  
        column(name: "transaction_flag", type: "tinyint(1)",   defaultValue:"0",   remarks: "事务监听器标志")   
        column(name: "on_transaction", type: "varchar(20)",  remarks: "事务管理")   
        column(name: "service_code", type: "varchar(50)",  remarks: "监听服务编码")   
        column(name: "service_params", type: "varchar(500)",  remarks: "监听服务参数")   
        column(name: "expression", type: "varchar(255)",  remarks: "监听服务表达式")   
        column(name: "category", type: "varchar(255)",  remarks: "流程类型")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,listener_type,code",tableName:"hwfl_process_listener",constraintName: "hwfl_process_listener_u1")
  }    
}