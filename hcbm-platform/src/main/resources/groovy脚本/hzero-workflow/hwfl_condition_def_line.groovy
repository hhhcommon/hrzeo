package script.db

databaseChangeLog(logicalFilePath: 'script/db/hwfl_condition_def_line.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2018-10-24-hwfl_condition_def_line") {
    if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hwfl_condition_def_line_s', startValue:"1")
        }
        createTable(tableName: "hwfl_condition_def_line", remarks: "条件定义 行表") {
        column(name: "expression_definition_line_id", type: "bigint(20)", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
        column(name: "expression_definition_id", type: "bigint(20)",  remarks: "头 id")  {constraints(nullable:"false")}  
        column(name: "code", type: "varchar(20)",  remarks: "代码")  {constraints(nullable:"false")}  
        column(name: "description", type: "varchar(255)",  remarks: "描述")  {constraints(nullable:"false")}  
        column(name: "left_operand", type: "varchar(200)",  remarks: "左操作数")   
        column(name: "right_operand", type: "varchar(200)",  remarks: "右操作数")   
        column(name: "operator", type: "varchar(20)",  remarks: "操作符")   
        column(name: "left_type", type: "varchar(20)",  remarks: "左操作数类型")   
        column(name: "right_type", type: "varchar(20)",  remarks: "右操作数类型")   
        column(name: "left_parameters", type: "varchar(500)",  remarks: "左操作数参数配置")   
        column(name: "left_operand_text", type: "varchar(200)",  remarks: "左操作数显示值")   
        column(name: "right_parameters", type: "varchar(500)",  remarks: "右操作数参数配置")   
        column(name: "right_operand_text", type: "varchar(200)",  remarks: "右操作数显示值")   
        column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "")   
        column(name: "created_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   
        column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"0",   remarks: "")   
        column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")   

    }

   addUniqueConstraint(columnNames:"code,expression_definition_id",tableName:"hwfl_condition_def_line",constraintName: "hwfl_condition_def_line_u1")
  }    
}