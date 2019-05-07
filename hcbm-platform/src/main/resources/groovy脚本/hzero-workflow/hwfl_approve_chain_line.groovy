package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_approve_chain_line.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_approve_chain_line") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_approve_chain_line_s', startValue:"1")
        }
        createTable(tableName: "hwfl_approve_chain_line", remarks: "审批链定义 行表") {
        column(name: "approve_chain_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "approve_chain_id", type: "bigint(20)",  remarks: "头 id")   
        column(name: "name", type: "varchar(255)",  remarks: "名称")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "描述")   
        column(name: "code", type: "varchar(50)",  remarks: "code")   
        column(name: "assignee", type: "varchar(500)",  remarks: "审批人")   
        column(name: "assign_group", type: "varchar(200)",  remarks: "审批组")   
        column(name: "form_key", type: "varchar(255)",  remarks: "审批页面")   
        column(name: "sequence", type: "decimal(18,2)",   defaultValue:"10.00",   remarks: "排序号，可以为小数")   
        column(name: "skip_expression", type: "varchar(255)",  remarks: "跳过条件,留空表示不跳过 (false)")   
        column(name: "break_on_skip", type: "tinyint(1)",   defaultValue:"0",   remarks: "该规则被跳过时,停止继续走其他规则")   
        column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }
   createIndex(tableName: "hwfl_approve_chain_line", indexName: "hwfl_approve_chain_line_n1") {
            column(name: "approve_chain_id")
        }          

   addUniqueConstraint(columnNames:"name",tableName:"hwfl_approve_chain_line",constraintName: "hwfl_approve_chain_line_u1")
  }    
}