package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_form_definition.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_form_definition") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_form_definition_s', startValue:"1")
        }
        createTable(tableName: "hwfl_form_definition", remarks: "外部定义表单") {
        column(name: "form_definition_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "category", type: "varchar(255)",  remarks: "流程类型")   
        column(name: "code", type: "varchar(50)",  remarks: "唯一标识")  {constraints(nullable:"false")}  
        column(name: "url", type: "varchar(255)",  remarks: "表单url")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "描述")  {constraints(nullable:"false")}  
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标志")   
        column(name: "invoke_flag", type: "tinyint(1)",  remarks: "是否回调")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,code",tableName:"hwfl_form_definition",constraintName: "hwfl_form_definition_u1")
  }    
}