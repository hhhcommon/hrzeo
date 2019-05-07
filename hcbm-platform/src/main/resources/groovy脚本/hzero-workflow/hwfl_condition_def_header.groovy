package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_condition_def_header.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_condition_def_header") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_condition_def_header_s', startValue:"1")
        }
        createTable(tableName: "hwfl_condition_def_header", remarks: "条件定义 头表") {
        column(name: "expression_definition_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "code", type: "varchar(50)",  remarks: "条件唯一标识")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "描述")  {constraints(nullable:"false")}  
        column(name: "category", type: "varchar(255)",  remarks: "流程类型")   
        column(name: "condition_type", type: "varchar(50)",  remarks: "类型")   
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标志")   
        column(name: "start_active_date", type: "datetime",  remarks: "有效期从")   
        column(name: "end_active_date", type: "datetime",  remarks: "有效期至")   
        column(name: "expression_definition", type: "varchar(200)",  remarks: "表达式定义")   
        column(name: "expression", type: "varchar(1000)",  remarks: "表达式")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "scope", type: "tinyint(1)",   defaultValue:"0",   remarks: "作用范围")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,condition_type,code",tableName:"hwfl_condition_def_header",constraintName: "hwfl_condition_def_header_u1")
  }    
}