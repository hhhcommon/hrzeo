package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_service_invoke_param.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_service_invoke_param") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_service_invoke_param_s', startValue:"1")
        }
        createTable(tableName: "hwfl_service_invoke_param", remarks: "服务调用参数配置表") {
        column(name: "parameter_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "external_definition_id", type: "bigint(20)",  remarks: "外部定义id")  {constraints(nullable:"false")}  
        column(name: "parameter_type", type: "varchar(50)",  remarks: "参数类型")  {constraints(nullable:"false")}  
        column(name: "parameter_name", type: "varchar(50)",  remarks: "参数名称")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "参数描述")  {constraints(nullable:"false")}  
        column(name: "default_value", type: "varchar(255)",  remarks: "参数默认值")   
        column(name: "default_text", type: "varchar(255)",  remarks: "参数默认文本")   
        column(name: "edit_able", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否可编辑")  {constraints(nullable:"false")}  
        column(name: "parameter_origin", type: "varchar(255)",  remarks: "参数来源地址")   
        column(name: "is_uri_variable", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否为url参数")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"parameter_name,external_definition_id",tableName:"hwfl_service_invoke_param",constraintName: "hwfl_service_invoke_param_u1")
  }    
}