package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_authority_control.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_authority_control") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_authority_control_s', startValue:"1")
        }
        createTable(tableName: "hwfl_authority_control", remarks: "权限控制表") {
        column(name: "authority_control_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)}
        column(name: "assign_type", type: "varchar(20)",  remarks: "分配类型")  {constraints(nullable:"false")}  
        column(name: "assign_id", type: "varchar(50)",  remarks: "分配id")  {constraints(nullable:"false")}  
        column(name: "target_type", type: "varchar(20)",  remarks: "被分配目标类型")  {constraints(nullable:"false")}  
        column(name: "target_id", type: "varchar(50)",  remarks: "被分配目标id")  {constraints(nullable:"false")}  
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"target_type,assign_id,tenant_id,assign_type,target_id",tableName:"hwfl_authority_control",constraintName: "hwfl_authority_control_u1")
  }    
}