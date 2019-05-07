package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_approval_config.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_approval_config") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_approval_config_s', startValue:"1")
        }
        createTable(tableName: "hwfl_approval_config", remarks: "审批配置表") {
        column(name: "delegate_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "employee_code", type: "varchar(50)",  remarks: "配置人code")   
        column(name: "delegate_code", type: "varchar(50)",  remarks: "转交给")   
        column(name: "delegate_start_date", type: "datetime",  remarks: "转交开始时间")   
        column(name: "delegate_end_date", type: "datetime",  remarks: "转交截止时间")   
        column(name: "auto_approval", type: "varchar(10)",  remarks: "是否自动审批")   
        column(name: "approval_start_date", type: "datetime",  remarks: "自动审批开始时间")   
        column(name: "approval_end_date", type: "datetime",  remarks: "自动审批截止时间")   
        column(name: "auto_approval_limit", type: "varchar(200)",  remarks: "自动审批时限")   
        column(name: "tenant_id", type: "bigint(20)",   defaultValue:"0",   remarks: "组织id")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

  }    
}