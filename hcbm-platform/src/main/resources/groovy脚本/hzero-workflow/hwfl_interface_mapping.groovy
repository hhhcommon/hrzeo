package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_interface_mapping.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_interface_mapping") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_interface_mapping_s', startValue:"1")
        }
        createTable(tableName: "hwfl_interface_mapping", remarks: "接口映射表") {
        column(name: "interface_mapping_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "code", type: "varchar(50)",  remarks: "接口代码")  {constraints(nullable:"false")}  
        column(name: "service_id", type: "varchar(50)",  remarks: "服务ID")  {constraints(nullable:"false")}  
        column(name: "url", type: "varchar(255)",  remarks: "接口地址")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "接口说明")  {constraints(nullable:"false")}  
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "scope", type: "tinyint(1)",   defaultValue:"0",   remarks: "作用范围")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"tenant_id,code",tableName:"hwfl_interface_mapping",constraintName: "hwfl_interface_mapping_u1")
  }    
}