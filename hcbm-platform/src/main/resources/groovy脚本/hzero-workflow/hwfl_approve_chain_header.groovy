package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_approve_chain_header.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_approve_chain_header") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_approve_chain_header_s', startValue:"1")
        }
        createTable(tableName: "hwfl_approve_chain_header", remarks: "审批链定义 头表") {
        column(name: "approve_chain_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "process_key", type: "varchar(255)",  remarks: "")  {constraints(nullable:"false")}  
        column(name: "usertask_id", type: "varchar(255)",  remarks: "")  {constraints(nullable:"false")}  
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"usertask_id,process_key",tableName:"hwfl_approve_chain_header",constraintName: "hwfl_approve_chain_header_u1")
  }    
}