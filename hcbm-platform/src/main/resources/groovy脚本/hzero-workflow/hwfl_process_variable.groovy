package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_process_variable.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_process_variable") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_process_variable_s', startValue:"1")
        }
        createTable(tableName: "hwfl_process_variable", remarks: "流程变量定义表") {
        column(name: "process_variable_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "code", type: "varchar(50)",  remarks: "流程变量code")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(50)",  remarks: "描述")  {constraints(nullable:"false")}  
        column(name: "category", type: "varchar(255)",  remarks: "流程类型")   
        column(name: "parameter_type", type: "varchar(50)",  remarks: "参数类型")  {constraints(nullable:"false")}  
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "scope", type: "tinyint(1)",   defaultValue:"0",   remarks: "作用范围")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,code",tableName:"hwfl_process_variable",constraintName: "hwfl_process_variable_u1")
  }    
}