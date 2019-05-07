package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_service_invoke_def.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_service_invoke_def") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_service_invoke_def_s', startValue:"1")
        }
        createTable(tableName: "hwfl_service_invoke_def", remarks: "服务调用定义表") {
        column(name: "external_definition_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "category", type: "varchar(50)",  remarks: "流程分类")   
        column(name: "external_type", type: "varchar(50)",  remarks: "类型")  {constraints(nullable:"false")}  
        column(name: "code", type: "varchar(50)",  remarks: "唯一标识")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "描述")  {constraints(nullable:"false")}  
        column(name: "url", type: "varchar(255)",  remarks: "远程服务请求接口")   
        column(name: "context_enable", type: "tinyint(1)",   defaultValue:"0",   remarks: "是否传递上下文")   
        column(name: "invoke_remote_service", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否调用远程服务")   
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "启用标志")   
        column(name: "return_type", type: "varchar(20)",  remarks: "返回类型")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "scope", type: "tinyint(1)",   defaultValue:"0",   remarks: "作用范围")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,code,external_type",tableName:"hwfl_service_invoke_def",constraintName: "hwfl_service_invoke_def_u1")
  }    
}